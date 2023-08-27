package com.example.calendar.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootHandler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction() != null && context != null) {
  if (intent.getAction().equalsIgnoreCase( Intent.ACTION_BOOT_COMPLETED)) {                     
           WidgetNotification.scheduleWidgetUpdate(context);
  }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}