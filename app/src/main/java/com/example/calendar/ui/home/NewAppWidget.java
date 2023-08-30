package com.example.calendar.ui.home;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.calendar.MainActivity;
import com.example.calendar.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE =
            "com.example.exampleWidget.AUTO_UPDATE";
    private static final String MyOnClick1 = "myOnClickTag1";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MyOnClick1.equals(intent.getAction())) {
            // your onClick action is here
            onUpdate(context);
        }
        if(intent!=null && intent.getAction()!=null &&
                intent.getAction().equals(ACTION_AUTO_UPDATE)){
            onUpdate(context);
        }
    }

    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager =
                AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
//        WidgetNotification.scheduleWidgetUpdate(context);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //CODE TO UPDATE YOUR WIDGET VIEW
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            toET(context, appWidgetManager, appWidgetId);
                            System.out.println(timer);
                            //your method here
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every minutes


//        toET(context, appWidgetManager, appWidgetId);
//        System.out.println("here");

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        WidgetNotification.clearWidgetUpdate(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        WidgetNotification.clearWidgetUpdate(context);
    }

    public void toET(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        HashMap<Integer, String> monthsET = new HashMap<>();

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
        monthsET.put(11, "ሐምሌ");
        monthsET.put(12, "ነሀሴ");
        monthsET.put(13, "ጳጉሜ");

        int day = -1;
        int month = -1;
        int year = -1;

        Calendar c = Calendar.getInstance();

        int todaysDay = c.get(Calendar.DAY_OF_MONTH);
        int todaysMonth = c.get(Calendar.MONTH)+1;
        int todaysYear = c.get(Calendar.YEAR);

        int thisYear = -1;
        if (todaysMonth == 9 && todaysDay >= 11) {
            thisYear = todaysYear - 7;
        } else if (todaysMonth == 9 && todaysDay < 11) {
            thisYear = todaysYear - 8;
        } else if (todaysMonth < 9) {
            thisYear = todaysYear - 8;
        } else if (todaysMonth > 9) {
            thisYear = todaysYear - 7;
        }


        if (todaysYear % 4 != 0) {
            if (todaysMonth == 1) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 4;
                    day = todaysDay + 22;
                } else if (todaysDay > 8) {
                    month = 5;
                    day = (todaysDay + 22) % 30;
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 2) {
                if (todaysDay >= 1 && todaysDay <= 6) {
                    month = 5;
                    day = todaysDay + 23;
                } else if (todaysDay > 6) {
                    month = 6;
                    day = (todaysDay + 23) % 30;
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 3) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 6;
                    day = todaysDay + 21;
                } else if (todaysDay > 8) {
                    month = 7;
                    day = (todaysDay + 21) % 30;
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 4) {
                if (todaysDay >= 1 && todaysDay <= 7) {
                    month = 7;
                    day = todaysDay + 22;
                } else if (todaysDay > 7) {
                    month = 8;
                    day = (todaysDay + 22) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 5) {
                if (todaysDay >= 1 && todaysDay <= 7) {
                    month = 8;
                    day = todaysDay + 22;
                } else if (todaysDay > 8) {
                    month = 9;
                    day = (todaysDay + 22) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 6) {
                if (todaysDay >= 1 && todaysDay <= 6) {
                    month = 9;
                    day = todaysDay + 23;
                } else if (todaysDay > 6) {
                    month = 10;
                    day = (todaysDay + 23) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 7) {
                if (todaysDay >= 1 && todaysDay <= 6) {
                    month = 10;
                    day = todaysDay + 23;
                } else if (todaysDay > 6) {
                    month = 11;
                    day = (todaysDay + 23) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 8) {
                if (todaysDay >= 1 && todaysDay <= 6) {
                    month = 11;
                    day = todaysDay + 24;
                } else if (todaysDay > 6) {
                    month = 12;
                    day = (todaysDay + 24) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 9) {
                if (todaysDay >= 1 && todaysDay <= 5) {
                    month = 12;
                    day = todaysDay + 25;
                    year = todaysYear - 8;
                }
                if (thisYear % 4 == 3) {
                    if (todaysDay > 5 && todaysDay <= 11) {
                        month = 13;
                        day = (todaysDay + 25) % 30;
                        if (day == 0) {
                            day = 5;
                        }
                    }
                    year = todaysYear - 8;
                } else {
                    month = 13;
                    day = (todaysDay + 25) % 30;
                    if (day == 0) {
                        day = 5;
                    }
                    year = todaysYear - 8;
                }
                if (todaysDay > 10) {
                    month = 1;
                    day = (todaysDay + 25) % 30 - 5;
                    if (day == 0) {
                        day = 30;
                    }
                    year = todaysYear - 7;
                }
            }
            if (todaysMonth == 10) {
                if (todaysDay >= 1 && todaysDay <= 9) {
                    month = 1;
                    day = todaysDay + 20;
                } else if (todaysDay > 9) {
                    month = 2;
                    day = (todaysDay + 20) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
            if (todaysMonth == 11) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 2;
                    day = todaysDay + 21;
                } else if (todaysDay > 8) {
                    month = 3;
                    day = (todaysDay + 21) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
            if (todaysMonth == 12) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 3;
                    day = todaysDay + 21;
                } else if (todaysDay > 8) {
                    month = 4;
                    day = (todaysDay + 21) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
        } else {
            if (todaysMonth == 1) {
                if (todaysDay >= 1 && todaysDay <= 9) {
                    month = 4;
                    day = todaysDay + 22;
                } else if (todaysDay > 9) {
                    month = 5;
                    day = (todaysDay + 22) % 30;
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 2) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 5;
                    day = todaysDay + 23;
                } else if (todaysDay > 8) {
                    month = 6;
                    day = (todaysDay + 23) % 30;
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 3) {
                if (todaysDay >= 1 && todaysDay <= 9) {
                    month = 6;
                    day = todaysDay + 22;
                } else if (todaysDay > 9) {
                    month = 7;
                    day = (todaysDay + 22) % 30;
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 4) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 7;
                    day = todaysDay + 23;
                } else if (todaysDay > 8) {
                    month = 8;
                    day = (todaysDay + 23) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 5) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 8;
                    day = todaysDay + 23;
                } else if (todaysDay > 8) {
                    month = 9;
                    day = (todaysDay + 23) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 6) {
                if (todaysDay >= 1 && todaysDay <= 7) {
                    month = 9;
                    day = todaysDay + 24;
                } else if (todaysDay > 7) {
                    month = 10;
                    day = (todaysDay + 24) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 7) {
                if (todaysDay >= 1 && todaysDay <= 7) {
                    month = 10;
                    day = todaysDay + 24;
                } else if (todaysDay > 7) {
                    month = 11;
                    day = (todaysDay + 24) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }

            if (todaysMonth == 8) {
                if (todaysDay >= 1 && todaysDay <= 6) {
                    month = 11;
                    day = todaysDay + 25;
                } else if (todaysDay > 6) {
                    month = 12;
                    day = (todaysDay + 25) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 8;
            }
            if (todaysMonth == 9) {
                if (todaysDay >= 1 && todaysDay <= 5) {
                    month = 12;
                    day = todaysDay + 26;
                    year = todaysYear - 8;
                }
                if (thisYear % 4 == 3) {
                    if (todaysDay > 5 && todaysDay <= 11) {
                        month = 13;
                        day = (todaysDay + 26) % 30;
                        if (day == 0) {
                            day = 5;
                        }
                        year = todaysYear - 8;
                    }
                    if (todaysDay > 11) {
                        month = 1;
                        day = (todaysDay + 26) % 30;
                        if (day == 0) {
                            day = 30;
                        }
                        year = todaysYear - 7;
                    }

                } else {
                    if (todaysDay > 5 && todaysDay <= 10) {
                        month = 13;
                        day = (todaysDay + 26) % 30;
                        if (day == 0) {
                            day = 5;
                        }
                        year = todaysYear - 8;
                    } else if (todaysDay > 10) {
                        month = 1;
                        day = (todaysDay + 26) % 30;
                        if (day == 0) {
                            day = 30;
                        }
                        year = todaysYear - 7;
                    }
                }
                if (todaysDay > 10) {
                    month = 1;
                    day = (todaysDay + 25) % 30 - 5;
                    if (day == 0) {
                        day = 30;
                    }
                    year = todaysYear - 7;
                }
            }
            if (todaysMonth == 10) {
                if (todaysDay >= 1 && todaysDay <= 9) {
                    month = 1;
                    day = todaysDay + 20;
                } else if (todaysDay > 9) {
                    month = 2;
                    day = (todaysDay + 20) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
            if (todaysMonth == 11) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 2;
                    day = todaysDay + 21;
                } else if (todaysDay > 8) {
                    month = 3;
                    day = (todaysDay + 21) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
            if (todaysMonth == 12) {
                if (todaysDay >= 1 && todaysDay <= 8) {
                    month = 3;
                    day = todaysDay + 21;
                } else if (todaysDay > 8) {
                    month = 4;
                    day = (todaysDay + 21) % 30;
                    if (day == 0) {
                        day = 30;
                    }
                }
                year = todaysYear - 7;
            }
        }
        if (thisYear % 4 == 0) {
            day -= 1;
        }
        String dayName = "";
        int Elet = (day + (Math.floorDiv((year + 5500), 4) + 5500 + year) % 7 + (month * 2) - 1) % 7;
        if (Elet == 1) {
            dayName = "እሁድ";
        } else if (Elet == 2) {
            dayName = "ሰኞ";
        } else if (Elet == 3) {
            dayName = "ማክሰኞ";
        } else if (Elet == 4) {
            dayName = "ረቡዕ";
        } else if (Elet == 5) {
            dayName = "ሀሙስ";
        } else if (Elet == 6) {
            dayName = "ዓርብ";
        } else if (Elet == 0) {
            dayName = "ቅዳሜ";
        }
//        resultET.setText(dayName + " " + monthsET.get(month) + " " + day + " " + year);

        CharSequence widgetText = dayName + " " + monthsET.get(month) + " " + day + " " + year;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_text,
                PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.refreshDAte, getPendingSelfIntent(context, MyOnClick1));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

}