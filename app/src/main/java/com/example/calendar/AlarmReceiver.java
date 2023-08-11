package com.example.calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFY_ME_ID = 1337;

    @Override
    public void onReceive(Context ctxt, Intent intent){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctxt);
        boolean useNotification = prefs.getBoolean("use_notification", true);
        if (useNotification) {
            NotificationManager mgr = (NotificationManager) ctxt.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent i = PendingIntent.getActivity(ctxt, 0, new Intent(ctxt, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
            Notification note = new Notification.Builder(ctxt).setContentTitle("AniTime").setContentText("Episode has been released!"). setSmallIcon(android.R.drawable.stat_notify_chat).setContentIntent(i).setAutoCancel(true).build();
            note.flags |= Notification.FLAG_AUTO_CANCEL;
            mgr.notify(NOTIFY_ME_ID, note);
        } else {
            Intent i = new Intent(ctxt, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctxt.startActivity(i);
        }
    }
}