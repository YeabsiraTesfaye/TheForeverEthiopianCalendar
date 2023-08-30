package com.example.calendar.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UdateHandler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent!=null && intent.getAction() != null && context != null) {
                if ( (intent.getAction().equalsIgnoreCase(Intent.ACTION_MY_PACKAGE_REPLACED)) ||                        (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction()) &&                                (intent.getData().getSchemeSpecificPart().equals(context.getPackageName())))) {
                    WidgetNotification.scheduleWidgetUpdate(context);

                }
            }
        }catch (Exception e){e.printStackTrace();
        }
    }
}