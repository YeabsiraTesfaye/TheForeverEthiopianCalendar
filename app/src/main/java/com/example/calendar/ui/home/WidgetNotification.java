package com.example.calendar.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class WidgetNotification {

    public static final int WIDGET_REQUEST_CODE = 191001;

    private static int[] getActiveWidgetIds(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        return appWidgetManager.getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
    }
 
   public static void scheduleWidgetUpdate(Context context) {
        if(getActiveWidgetIds(context)!=null && getActiveWidgetIds(context).length>0) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = getWidgetAlarmIntent(context);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis()); 

            am.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), (60000), pi);
        }
    }

    private static PendingIntent getWidgetAlarmIntent(Context context) {
        Intent intent = new Intent(context, NewAppWidget.class)
                .setAction(NewAppWidget.ACTION_AUTO_UPDATE)                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,getActiveWidgetIds(context));
        return PendingIntent.getBroadcast(context, WIDGET_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    public static void clearWidgetUpdate(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getWidgetAlarmIntent(context));
    }    
}