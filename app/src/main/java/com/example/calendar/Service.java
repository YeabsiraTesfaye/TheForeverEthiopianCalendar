package com.example.calendar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.calendar.db.DBHandler;
import com.example.calendar.db.MemoModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Service extends android.app.Service {
    private DBHandler dbHandler;
    private HashMap<Integer,String> monthsET = new HashMap<>();
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;

    public class LocalBinder extends Binder {
        public Service getService() {
            return Service .this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        monthsET.put(1, "መስከረም");
        monthsET.put(2, "ጥቅምት");
        monthsET.put(3, "ህዳር");
        monthsET.put(4, "ታህሳስ");
        monthsET.put(5, "ጥር");
        monthsET.put(6, "የካቲት");
        monthsET.put(7, "መጋቢት");
        monthsET.put(8, "ሚያዚያ");
        monthsET.put(9, "ግንቦት");
        monthsET.put(10, "ሰኔ");
        monthsET.put(11, "ምሌ");
        monthsET.put(12, "ነሀሴ");
        monthsET.put(13, "ጳጉሜ");
        // do your jobs here

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            startForeground();
                            //your method here
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 40000); //execute in every minutes



        return START_STICKY;
    }

    private void startForeground() {
        getReminder();
    }

    void showNotification(String title, String message,int id){
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder mBuild = new NotificationCompat.Builder(this, id+"");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        mBuild.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setVibrate(pattern);
        mBuild.setStyle(new NotificationCompat.InboxStyle());
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        mBuild.setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            NotificationChannel channel =
                    new NotificationChannel(id+"", "Event",
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            mBuild.setChannelId(id+"");
        }
        notificationManager.notify(id ,
                mBuild.build()) ;
    }
    void getReminder(){
        dbHandler = new DBHandler(getApplicationContext());
        ArrayList<MemoModal> memoModalArrayList = dbHandler.readMemo();
        for(MemoModal m : memoModalArrayList) {
            Calendar c = Calendar.getInstance();
            int[] dateGR = toGR(m.getDay()+"",m.getMonth()+"",m.getYear()+"");
            if(dateGR[2] == c.get(Calendar.YEAR) && dateGR[1]==c.get(Calendar.MONTH)+1 && dateGR[0] == c.get(Calendar.DAY_OF_MONTH) && m.getHour() == c.get(Calendar.HOUR) && m.getMinute() == c.get(Calendar.MINUTE)){
                showNotification(monthsET.get(m.getMonth())+" "+m.getDay()+" "+m.getYear(),m.getDescription(),m.getId());
            }
        }
    }

    public int[] toGR(String dayET, String monthET, String yearET) {

        int day = Integer.parseInt(dayET);
        int month = Integer.parseInt(monthET);


        System.out.println(month);
        int year = Integer.parseInt(yearET);

        int dayGR = 0;
        int monthGR = 0;
        int yearGR = 0;
        if (month >= 1 && month < 4) {
            yearGR = year + 7;
        } else if (month == 4) {
            if (day < 23) {
                yearGR = year + 7;
            } else {
                yearGR = year + 8;
            }
        } else if (month > 4) {
            yearGR = year + 8;
        }
        if (year % 4 != 0) {

            if (month == 1) {
                if (day >= 1 && day <= 20) {
                    monthGR = 9;
                    dayGR = day + 10;
                } else if (day > 20) {
                    monthGR = 10;
                    dayGR = day - 20;
                }
            } else if (month == 2) {
                if (day >= 1 && day <= 21) {
                    monthGR = 10;
                    dayGR = day + 10;
                } else {
                    if (day > 21) {
                        monthGR = 11;
                        dayGR = day - 21;
                    }
                }
            } else if (month == 3) {
                if (day >= 1 && day <= 21) {
                    monthGR = 11;
                    dayGR = day + 9;
                } else {
                    if (day > 21) {
                        monthGR = 12;
                        dayGR = day - 21;
                    }
                }
            } else if (month == 4) {
                if (day >= 1 && day <= 22) {
                    monthGR = 12;
                    dayGR = day + 9;
                } else {
                    if (day > 22) {
                        monthGR = 1;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 5) {
                if (day >= 1 && day <= 23) {
                    monthGR = 1;
                    dayGR = day + 8;
                } else {
                    if (day > 23) {
                        monthGR = 2;
                        dayGR = day - 23;
                    }
                }
            } else if (month == 6) {
                if (yearGR % 4 == 0) {
                    if (day >= 1 && day <= 21) {
                        monthGR = 2;
                        dayGR = day + 8;
                    } else {
                        if (day > 21) {
                            monthGR = 3;
                            dayGR = day - 21;
                        }
                    }
                } else {
                    if (day >= 1 && day <= 21) {
                        monthGR = 2;
                        dayGR = day + 7;
                    } else {
                        if (day > 21) {
                            monthGR = 3;
                            dayGR = day - 21;
                        }
                    }
                }

            } else if (month == 7) {
                if (day >= 1 && day <= 22) {
                    monthGR = 3;
                    dayGR = day + 9;
                } else {
                    if (day > 22) {
                        monthGR = 4;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 8) {
                if (day >= 1 && day <= 22) {
                    monthGR = 4;
                    dayGR = day + 8;
                } else {
                    if (day > 22) {
                        monthGR = 5;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 9) {
                if (day >= 1 && day <= 23) {
                    monthGR = 5;
                    dayGR = day + 8;
                } else {
                    if (day > 23) {
                        monthGR = 6;
                        dayGR = day - 23;
                    }
                }
            } else if (month == 10) {
                if (day >= 1 && day <= 23) {
                    monthGR = 6;
                    dayGR = day + 7;
                } else {
                    if (day > 23) {
                        monthGR = 7;
                        dayGR = day - 23;
                    }
                }
            } else if (month == 11) {
                if (day >= 1 && day <= 24) {
                    monthGR = 7;
                    dayGR = day + 7;
                } else {
                    if (day > 24) {
                        monthGR = 8;
                        dayGR = day - 24;
                    }
                }
            } else if (month == 12) {
                if (day >= 1 && day <= 25) {
                    monthGR = 8;
                    dayGR = day + 6;
                } else {
                    if (day > 25) {
                        monthGR = 9;
                        dayGR = day - 25;
                    }
                }
            } else if (month == 13) {
                if (Integer.parseInt(yearET) % 4 == 3) {
                    if (day >= 1 && day <= 6) {
                        monthGR = 9;
                    }
                } else {
                    if (day >= 1 && day <= 5) {
                        monthGR = 9;
                    }
                }
                dayGR = day + 5;
            }

        } else {
            if (month == 1) {
                if (day >= 1 && day <= 19) {
                    monthGR = 9;
                    dayGR = day + 11;
                } else if (day > 19) {
                    monthGR = 10;
                    dayGR = day - 19;
                }
            } else if (month == 2) {
                if (day >= 1 && day <= 20) {
                    monthGR = 11;
                    dayGR = day + 11;
                } else {
                    if (day > 20) {
                        monthGR = 11;
                        dayGR = day - 20;
                    }
                }
            } else if (month == 3) {
                if (day >= 1 && day <= 20) {
                    monthGR = 11;
                    dayGR = day + 10;
                } else {
                    if (day > 20) {
                        monthGR = 12;
                        dayGR = day - 20;
                    }
                }
            } else if (month == 4) {
                if (day >= 1 && day <= 21) {
                    monthGR = 12;
                    dayGR = day + 10;
                } else {
                    if (day > 21) {
                        monthGR = 1;
                        dayGR = day - 21;
                    }
                }
            } else if (month == 5) {
                if (day >= 1 && day <= 22) {
                    monthGR = 1;
                    dayGR = day + 9;
                } else {
                    if (day > 22) {
                        monthGR = 2;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 6) {
                if (day >= 1 && day <= 21) {
                    monthGR = 2;
                    dayGR = day + 8;
                } else {
                    if (day > 21) {
                        monthGR = 3;
                        dayGR = day - 21;
                    }
                }
            } else if (month == 7) {
                if (day >= 1 && day <= 21) {
                    monthGR = 3;
                    dayGR = day + 9;
                } else {
                    if (day > 21) {
                        monthGR = 4;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 8) {
                if (day >= 1 && day <= 21) {
                    monthGR = 4;
                    dayGR = day + 8;
                } else {
                    if (day > 21) {
                        monthGR = 5;
                        dayGR = day - 22;
                    }
                }
            } else if (month == 9) {
                if (day >= 1 && day <= 22) {
                    monthGR = 5;
                    dayGR = day + 8;
                } else {
                    if (day > 22) {
                        monthGR = 6;
                        dayGR = day - 23;
                    }
                }
            } else if (month == 10) {
                if (day >= 1 && day <= 23) {
                    monthGR = 6;
                    dayGR = day + 7;
                } else {
                    if (day > 23) {
                        monthGR = 7;
                        dayGR = day - 23;
                    }
                }
            } else if (month == 11) {
                if (day >= 1 && day <= 24) {
                    monthGR = 7;
                    dayGR = day + 7;
                } else {
                    if (day > 24) {
                        monthGR = 8;
                        dayGR = day - 24;
                    }
                }
            } else if (month == 12) {
                if (day >= 1 && day <= 25) {
                    monthGR = 8;
                    dayGR = day + 6;
                } else {
                    if (day > 25) {
                        monthGR = 9;
                        dayGR = day - 25;
                    }
                }
            } else if (month == 13) {
                if (Integer.parseInt(yearET) % 4 == 3) {
                    if (day >= 1 && day <= 6) {
                        monthGR = 9;
                    }
                } else {
                    if (day >= 1 && day <= 5) {
                        monthGR = 9;
                    }
                }
                dayGR = day + 5;
            }
        }

        int[] date = {dayGR, monthGR, yearGR};
        System.out.println(dayGR+" "+monthGR+" "+yearGR);
        return date;

    }
}