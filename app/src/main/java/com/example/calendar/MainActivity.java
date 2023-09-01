package com.example.calendar;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.calendar.ui.home.NewAppWidget;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.calendar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, 1);
        }

        ComponentName componentName = new ComponentName(
                getApplicationContext(),
                Service.class);

        getApplicationContext().getPackageManager().setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        String packageName = "org.traccar.client";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent i = new Intent();

            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                i.setData(Uri.parse("package:" + packageName));
                startActivity(i);
            }
        }

        startService(new Intent(this, Service.class));




        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        SharedPreferences pref = this.getSharedPreferences("PACKAGE.NAME",MODE_PRIVATE);
        Boolean firstTime = pref.getBoolean("firstTime",true);

        int ids[] = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, NewAppWidget.class));

        if(ids.length == 0 && firstTime){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    AppWidgetManager mAppWidgetManager = getSystemService(AppWidgetManager.class);
                    ComponentName myProvider = new ComponentName(MainActivity.this, NewAppWidget.class);
                    Bundle b = new Bundle();
                    b.putString("ggg", "ggg");
                    if (mAppWidgetManager.isRequestPinAppWidgetSupported()) {
                        Intent pinnedWidgetCallbackIntent = new Intent(MainActivity.this, NewAppWidget.class);
                        PendingIntent successCallback = PendingIntent.getBroadcast(MainActivity.this, 0,
                                pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE);
                        mAppWidgetManager.requestPinAppWidget(myProvider, b, successCallback);

                    }


                }

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Thread.sleep(4000);
                        runOnUiThread(() -> pref.edit().putBoolean("firstTime",false).apply());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return null;
                }


            }.execute();



    }


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


}