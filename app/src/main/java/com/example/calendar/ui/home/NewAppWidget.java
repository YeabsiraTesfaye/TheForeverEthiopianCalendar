package com.example.calendar.ui.home;

import android.app.AlarmManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.calendar.R;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        toET(context, appWidgetManager,appWidgetId);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    public static void toET(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

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
        monthsET.put(11, "ምሌ");
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
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }




}