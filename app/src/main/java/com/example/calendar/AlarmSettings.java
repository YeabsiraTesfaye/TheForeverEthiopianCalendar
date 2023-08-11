package com.example.calendar;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class AlarmSettings extends PreferenceActivity {
    private SharedPreferences prefs = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.options);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(onChange);
    }

    @Override
    public void onPause() {
        prefs.unregisterOnSharedPreferenceChangeListener(onChange);
        super.onPause();
    }

    SharedPreferences.OnSharedPreferenceChangeListener onChange = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if ("alarm".equals(key)){
                boolean enabled = prefs.getBoolean(key,false);
                int flag = (enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
                ComponentName component = new ComponentName(AlarmSettings.this, BootReceiver.class);
                getPackageManager().setComponentEnabledSetting(component, flag, PackageManager.DONT_KILL_APP);

                if (enabled) {
                    BootReceiver.setAlarm(AlarmSettings.this);
                } else {
                    BootReceiver.cancelAlarm(AlarmSettings.this);
                }
            } else if ("alarm_time".equals(key)){
                BootReceiver.cancelAlarm(AlarmSettings.this);
                BootReceiver.setAlarm(AlarmSettings.this);
            }
        }
    };}