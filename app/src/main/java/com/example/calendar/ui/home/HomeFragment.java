package com.example.calendar.ui.home;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendar.AlarmReceiver;
import com.example.calendar.R;
import com.example.calendar.databinding.FragmentHomeBinding;
import com.example.calendar.db.DBHandler;
import com.tomergoldst.tooltips.ToolTipsManager;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class HomeFragment extends Fragment implements ToolTipsManager.TipListener {
    static int[] idArrays = new int[]{R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5,R.id.d6, R.id.d7, R.id.d8, R.id.d9, R.id.d10,R.id.d11, R.id.d12, R.id.d13, R.id.d14, R.id.d15,R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d20,R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25,R.id.d26, R.id.d27, R.id.d28, R.id.d29, R.id.d30,R.id.d31, R.id.d32, R.id.d33, R.id.d34, R.id.d35,R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d40, R.id.d41, R.id.d42} ;

    int ThisYear=-1;
    static String Wengelawi="";
    static  int Abekte,Metke,MeteneRabit,Mebacha,Wenber,Medeb,BealeMetke,MebajaHamer,Elet, AtsfeWer,NeneweMonth,Nenewe,
            Tinsae,TinsaeMonth,Enkutatash, Meskel, GenaTsom,Gena,Timket,Debretabor,Fisleta,AbiyTsom,AbiyTsomMonth,DebreZeyt,
            DebreZeytMonth,Hosaena,HosaenaMonth,Seklet,RekbeKahnat,Erget,theMonth,theDay,theYear = -1;
    //**********************
    static HashMap<Integer, String> months = new HashMap<>();
    static LinearLayout ll;
    private FragmentHomeBinding binding;
    static View root;
    static View calendarLayout;
    ScrollView scrollView;
    EditText yearInput;
    TextView todayTV;
    ImageButton increase, decrease;
    ToolTipsManager toolTipsManager;
    ClipboardManager myClipboard;

    private DBHandler dbHandler;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        dbHandler = new DBHandler(getContext());
        toolTipsManager=new ToolTipsManager(this);

        myClipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);

        createNotificationChannel();
        months.put(1, "መስከረም");
        months.put(2, "ጥቅምት");
        months.put(3, "ህዳር");
        months.put(4, "ታህሳስ");
        months.put(5, "ጥር");
        months.put(6, "የካቲት");
        months.put(7, "መጋቢት");
        months.put(8, "ሚያዚያ");
        months.put(9, "ግንቦት");
        months.put(10, "ሰኔ");
        months.put(11, "ሀምሌ");
        months.put(12, "ነሀሴ");
        months.put(13, "ጳጉሜን");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        yearInput = root.findViewById(R.id.yearInput);
        ll = root.findViewById(R.id.calendars);
        scrollView = root.findViewById(R.id.scrollVIew);
        increase = root.findViewById(R.id.increase);
        decrease = root.findViewById(R.id.decrease);
        DisplayTodayeDate();
        populateCalendar(inflater,container);
        duration();
        yearInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = yearInput.getText().toString().trim();
                if(!input.equals("")){
                    ll.removeAllViews();
                    calculateTheYear(Integer.parseInt(input));
                    populateCalendar(inflater,container);
                    if(theYear == Integer.parseInt(yearInput.getText().toString())){
                        duration();
                    }else{
                        scrollView.smoothScrollTo(0,0);
                    }
                }else{
                    ll.removeAllViews();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = yearInput.getText().toString();
                if(!year.equals("")){
                    int increasedValue = Integer.parseInt(year)+1;
                    yearInput.setText(increasedValue+"");
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = yearInput.getText().toString();
                if(!year.equals("")){
                    int decreasedValue = Integer.parseInt(year)-1;
                    yearInput.setText(decreasedValue+"");
                }
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void DisplayTodayeDate(){
        int day = -1;
        int month=-1;
        int year=-1;


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        String formattedDate = df.format(c);
        String[] date = formattedDate.split("-");

        
        int todaysDay = Integer.parseInt(date[0]);
        int todaysMonth = Integer.parseInt(date[1]);
        int todaysYear = Integer.parseInt(date[2]);



//        Calendar today = Calendar.getInstance();
        int thisYear=-1;
        if(todaysMonth == 9 && todaysDay >= 11){
            thisYear = todaysYear-7;
        }else if(todaysMonth == 9 && todaysDay < 11){
            thisYear = todaysYear-8;
        }else if(todaysMonth < 9){
            thisYear = todaysYear-8;
        }else if(todaysMonth > 9){
            thisYear = todaysYear-7;
        }

        

        calculateTheYear(thisYear);
        if(todaysYear%4 != 0){
            if(todaysMonth == 1){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 4;
                    day = todaysDay + 22;
                }else if(todaysDay > 8){
                    month = 5;
                    day = (todaysDay + 22)%30;
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 2){
                if(todaysDay >= 1 && todaysDay <= 6){
                    month = 5;
                    day = todaysDay + 23;
                }else if(todaysDay > 6){
                    month = 6;
                    day = (todaysDay + 23)%30;
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 3){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 6;
                    day = todaysDay + 21;
                }else if(todaysDay > 8){
                    month = 7;
                    day = (todaysDay + 21)%30;
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 4){
                if(todaysDay >= 1 && todaysDay <= 7){
                    month = 7;
                    day = todaysDay + 22;
                }else if(todaysDay > 7){
                    month = 8;
                    day = (todaysDay + 22)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 5){
                if(todaysDay >= 1 && todaysDay <= 7){
                    month = 8;
                    day = todaysDay + 22;
                }else if(todaysDay > 8){
                    month = 9;
                    day = (todaysDay + 22)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 6){
                if(todaysDay >= 1 && todaysDay <= 6){
                    month = 9;
                    day = todaysDay + 23;
                }else if(todaysDay > 6){
                    month = 10;
                    day = (todaysDay + 23)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 7){
                if(todaysDay >= 1 && todaysDay <= 6){
                    month = 10;
                    day = todaysDay + 23;
                }else if(todaysDay > 6){
                    month = 11;
                    day = (todaysDay + 23)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 8){
                if(todaysDay >= 1 && todaysDay <= 6){
                    month = 11;
                    day = todaysDay + 24;
                }else if(todaysDay > 6){
                    month = 12;
                    day = (todaysDay + 24)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 9){
                if(todaysDay >= 1 && todaysDay <= 5){
                    month = 12;
                    day = todaysDay + 25;
                    year = todaysYear-8;
                }
                if(Wengelawi.equals("ሉቃስ")){
                    if(todaysDay >5 && todaysDay <= 11){
                        month = 13;
                        day = (todaysDay + 25)%30;
                        if(day == 0){
                            day = 5;
                        }
                    }
                    year = todaysYear-8;
                }
                else{
                    month = 13;
                    day = (todaysDay + 25)%30;
                    if(day == 0){
                        day = 5;
                    }
                    year = todaysYear-8;
                }
                if(todaysDay > 10){
                    month = 1;
                    day = (todaysDay+25)%30 - 5;
                    if(day == 0){
                        day = 30;
                    }
                    year = todaysYear-7;
                }
            }
            if(todaysMonth == 10){
                if(todaysDay >= 1 && todaysDay <= 9){
                    month = 1;
                    day = todaysDay + 20;
                }else if(todaysDay >9){
                    month = 2;
                    day = (todaysDay + 20)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
            if(todaysMonth == 11){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 2;
                    day = todaysDay + 21;
                }else if(todaysDay >8){
                    month = 3;
                    day = (todaysDay + 21)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
            if(todaysMonth == 12){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 3;
                    day = todaysDay + 21;
                }else if(todaysDay >8){
                    month = 4;
                    day = (todaysDay + 21)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
        }
        else {
            if(todaysMonth == 1){
                if(todaysDay >= 1 && todaysDay <= 9){
                    month = 4;
                    day = todaysDay + 22;
                }else if(todaysDay > 9){
                    month = 5;
                    day = (todaysDay + 22)%30;
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 2){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 5;
                    day = todaysDay + 23;
                }else if(todaysDay > 8){
                    month = 6;
                    day = (todaysDay + 23)%30;
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 3){
                if(todaysDay >= 1 && todaysDay <= 9){
                    month = 6;
                    day = todaysDay + 22;
                }else if(todaysDay > 9){
                    month = 7;
                    day = (todaysDay + 22)%30;
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 4){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 7;
                    day = todaysDay + 23;
                }else if(todaysDay >8){
                    month = 8;
                    day = (todaysDay + 23)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 5){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 8;
                    day = todaysDay + 23;
                }else if(todaysDay >8){
                    month = 9;
                    day = (todaysDay + 23)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 6){
                if(todaysDay >= 1 && todaysDay <= 7){
                    month = 9;
                    day = todaysDay + 24;
                }else if(todaysDay >7){
                    month = 10;
                    day = (todaysDay + 24)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 7){
                if(todaysDay >= 1 && todaysDay <= 7){
                    month = 10;
                    day = todaysDay + 24;
                }else if(todaysDay > 7){
                    month = 11;
                    day = (todaysDay + 24)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }

            if(todaysMonth == 8){
                if(todaysDay >= 1 && todaysDay <= 6){
                    month = 11;
                    day = todaysDay + 25;
                }else if(todaysDay > 6){
                    month = 12;
                    day = (todaysDay + 25)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-8;
            }
            if(todaysMonth == 9){
                if(todaysDay >= 1 && todaysDay <= 5){
                    month = 12;
                    day = todaysDay + 26;
                    year = todaysYear-8;
                }
                if(Wengelawi.equals("ሉቃስ")){
                    if(todaysDay >5 && todaysDay <= 11){
                        month = 13;
                        day = (todaysDay + 26)%30;
                        if(day == 0){
                            day = 5;
                        }
                        year = todaysYear-8;
                    }
                    if(todaysDay >11){
                        month = 1;
                        day = (todaysDay + 26)%30;
                        if(day == 0){
                            day = 30;
                        }
                        year = todaysYear-7;
                    }

                }
                else{
                    if(todaysDay>5 && todaysDay <= 10){
                        month = 13;
                        day = (todaysDay + 26)%30;
                        if(day == 0){
                            day = 5;
                        }
                        year = todaysYear-8;
                    }else if(todaysDay>10){
                        month = 1;
                        day = (todaysDay + 26)%30;
                        if(day == 0){
                            day = 30;
                        }
                        year = todaysYear-7;
                    }
                }
                if(todaysDay > 10){
                    month = 1;
                    day = (todaysDay+25)%30 - 5;
                    if(day == 0){
                        day = 30;
                    }
                    year = todaysYear-7;
                }
            }
            if(todaysMonth == 10){
                if(todaysDay >= 1 && todaysDay <= 9){
                    month = 1;
                    day = todaysDay + 20;
                }else if(todaysDay >9){
                    month = 2;
                    day = (todaysDay + 20)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
            if(todaysMonth == 11){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 2;
                    day = todaysDay + 21;
                }else if(todaysDay >8){
                    month = 3;
                    day = (todaysDay + 21)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
            if(todaysMonth == 12){
                if(todaysDay >= 1 && todaysDay <= 8){
                    month = 3;
                    day = todaysDay + 21;
                }else if(todaysDay >8){
                    month = 4;
                    day = (todaysDay + 21)%30;
                    if(day == 0){
                        day = 30;
                    }
                }
                year = todaysYear-7;
            }
        }
        if(Wengelawi.equals("ዩሃንስ")){
            day-=1;
        }
        String dayName="";
        int Elet = (day + (Math.floorDiv((year+5500),4)+5500+year)%7 + (month*2) - 1) % 7;
        if(Elet == 1){
            dayName = "እሁድ";
        }else if(Elet == 2){
            dayName = "ሰኞ";
        }else if(Elet == 3){
            dayName = "ማክሰኞ";
        }else if(Elet == 4){
            dayName = "ረቡዕ";
        }else if(Elet == 5){
            dayName = "ሀሙስ";
        }else if(Elet == 6){
            dayName = "ዓርብ";
        }else if(Elet == 0){
            dayName = "ቅዳሜ";
        }

        todayTV = root.findViewById(R.id.today);
        todayTV.setText(dayName+' '+ months.get(month) + ' '+day+' '+year);
        theMonth = month;
        theDay = day;
        theYear = year;
        yearInput.setText(year+"");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putInt("year", year);
        myEdit.putInt("month", month);
        myEdit.putInt("day", day);
        myEdit.putString("wongelawi", Wengelawi);
        myEdit.apply();


    }
    public static void calculateTheYear(int Year) {
        int WengelawiValue = (Year + 5500) % 4;

        if (WengelawiValue == 0) {
            Wengelawi = "ዩሃንስ";
        } else if (WengelawiValue == 1) {
            Wengelawi = "ማቴዎስ";
        } else if (WengelawiValue == 2) {
            Wengelawi = "ማርቆስ";
        } else if (WengelawiValue == 3) {
            Wengelawi = "ሉቃስ";
        }
        TextView wongelawi = root.findViewById(R.id.wongelawi);
        wongelawi.setText("ዘመነ "+Wengelawi);
        MeteneRabit = (int) (((Year + 5500) / 4));
        Mebacha = (5500 + Year + MeteneRabit) % 7;
        Medeb = (5500 + Year) % 19;
        if(Medeb == 0){
            Medeb = 19;
        }
        Wenber = Medeb - 1;

        Metke = (Wenber * 19) % 30;
        Abekte = 30 - Metke;
        if (Metke > 14) {
            BealeMetke = 1;
        } else {
            BealeMetke = 2;
        }
        AtsfeWer = BealeMetke * 2;
        Elet = (Metke + Mebacha + AtsfeWer - 1) % 7;

        if (Elet == 1) {
            MebajaHamer = (Metke + 7);
        } else if (Elet == 2) {
            MebajaHamer = (Metke + 6);
        } else if (Elet == 3) {
            MebajaHamer = (Metke + 5);
        } else if (Elet == 4) {
            MebajaHamer = (Metke + 4);
        } else if (Elet == 5) {
            MebajaHamer = (Metke + 3);
        } else if (Elet == 6) {
            MebajaHamer = (Metke + 2);
        } else if (Elet == 0) {
            MebajaHamer = (Metke + 8);
        }
        if (BealeMetke == 1) {
            NeneweMonth = 5;
        } else if (BealeMetke == 2) {
            NeneweMonth = 6;
        }
        if (BealeMetke == 1) {
            NeneweMonth = 5;
        } else if (BealeMetke == 2) {
            NeneweMonth = 6;
        }


        Nenewe = MebajaHamer;
        if(Nenewe > 30){
            Nenewe=Nenewe%30;
            NeneweMonth+=1;
        }

        AbiyTsom = MebajaHamer + 14;
        DebreZeyt = MebajaHamer + 11;
        Hosaena = (MebajaHamer + 2)%30;
        Seklet = MebajaHamer + 7;
        RekbeKahnat = MebajaHamer + 3;
        Erget = MebajaHamer + 18;
        AbiyTsomMonth = NeneweMonth;
        if(AbiyTsom > 30){
            AbiyTsom = AbiyTsom%30;
            AbiyTsomMonth = AbiyTsomMonth + 1;
        }
        TinsaeMonth = NeneweMonth + 2;
        Tinsae = MebajaHamer + 9;
        if(Tinsae > 30){
            Tinsae = Tinsae%30;
            TinsaeMonth = TinsaeMonth + 1;
        }
        if(AbiyTsom <= 3){
            DebreZeytMonth = AbiyTsomMonth;
        }else{
            DebreZeytMonth = AbiyTsomMonth+1;
        }
        if(DebreZeyt > 30){
            DebreZeyt = DebreZeyt%30;
        }

        if(DebreZeyt <=9){
            HosaenaMonth = DebreZeytMonth;
        }else{
            HosaenaMonth = DebreZeytMonth+1;
        }

        System.out.println(NeneweMonth+" "+Nenewe+" ");

    }
    public void populateCalendar(LayoutInflater inflater, ViewGroup container){
        int[] counter = {Mebacha};
        months.forEach((key, value) -> {
            calendarLayout =  inflater.inflate(R.layout.calendar_layout, container, false);
                if (key != 13) {
                    if (key == 1) {
                        Enkutatash = Mebacha;
                        Meskel = Mebacha + 16;
                    }
                    if (key == 3) {
                        GenaTsom = Mebacha+14;
                    }
                    if (key == 4) {
                        Gena = Mebacha + 28;
                    }
                    if (key == 5) {
                        Timket = Mebacha + 10;
                    }
                    if (key == 12) {
                        Debretabor = Mebacha + 12;
                        Fisleta = Mebacha;
                    }
                    for(int ind=0; ind<=idArrays.length-1; ind++){
                        if(counter[0] < 30+Mebacha){
                            TextView day = calendarLayout.findViewById(idArrays[counter[0]++]);
                            day.setText(ind+1+"");
                            day.setOnClickListener(new DoubleClickListener() {
                                @Override
                                void onDoubleClick(View v) {
                                    TextView tv = (TextView)v;
                                    btn_showMessage(tv,value+" "+tv.getText().toString()+" "+ yearInput.getText().toString(), key, value);
                                    Toast.makeText(getContext(),value+" "+tv.getText().toString()+" "+ yearInput.getText().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } else if(key == 13) {
                    if (Wengelawi=="ሉቃስ") {
                        for(int ind=0; ind<=idArrays.length-37; ind++){
                            if(counter[0] < 30+Mebacha){
                                TextView day = calendarLayout.findViewById(idArrays[counter[0]++]);
                                day.setText(ind+1+"");
                                day.setOnClickListener(new DoubleClickListener() {
                                    @Override
                                    void onDoubleClick(View v) {
                                        btn_showMessage(day,value+" "+day.getText().toString()+" "+ yearInput.getText().toString(), key, value);
                                    }
                                });
                            }
                        }
                    } else {
                        for(int ind=0; ind<=idArrays.length-38; ind++){
                            if(counter[0] < 30+Mebacha){
                                TextView day = calendarLayout.findViewById(idArrays[counter[0]++]);
                                day.setText(ind+1+"");
                                day.setOnClickListener(new DoubleClickListener() {
                                    @Override
                                    void onDoubleClick(View v) {
                                        btn_showMessage(day,value+" "+day.getText().toString()+" "+ yearInput.getText().toString(), key, value);
                                    }
                                });
                            }
                        }
                    }
                }
                TextView mtv = calendarLayout.findViewById(R.id.month);
                mtv.setText(value);
                mtv.setTag(value);

                if(key == NeneweMonth){
                    TextView NeneweTV = calendarLayout.findViewById(idArrays[(Nenewe+Mebacha)-1]);
                    NeneweTV.setBackgroundColor(Color.parseColor("#292928"));
                    NeneweTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(NeneweTV, "ጾመ ነነዌ");
                }

                if(key == theMonth && theYear == Integer.parseInt(yearInput.getText().toString())){
                    TextView todayTV = calendarLayout.findViewById(idArrays[theDay+Mebacha-1]);
                    todayTV.setTextSize(20);
                    todayTV.setTextColor(Color.BLUE);
                    todayTV.setTypeface(todayTV.getTypeface(), Typeface.BOLD_ITALIC);
                    toaster(todayTV,"ዛሬ");
                }
                if(Tinsae < 3){
                    if(key == TinsaeMonth-1){
                        int Seklet1 = Tinsae+Mebacha+27;
                        TextView SekletTV = calendarLayout.findViewById(idArrays[Seklet1]);
                        SekletTV.setBackgroundColor(Color.parseColor("#292928"));
                        SekletTV.setTextColor(Color.parseColor("#ffffff"));
                        toaster(SekletTV, "ስቅለት");
                    }
                }
                if(key == 1){
                    TextView EnkutatashTV = calendarLayout.findViewById(idArrays[Enkutatash]);
                    EnkutatashTV.setBackgroundColor(Color.parseColor("#b22222"));
                    EnkutatashTV.setTextColor(Color.parseColor("#ffffff"));

                    TextView MeskelTV = calendarLayout.findViewById(idArrays[Meskel]);
                    MeskelTV.setBackgroundColor(Color.parseColor("#b22222"));
                    MeskelTV.setTextColor(Color.parseColor("#ffffff"));

                    toaster(EnkutatashTV, "እንቁጣጣሽ");
                    toaster(MeskelTV, "መስቀል");
                } else if(key == 3){
                    TextView GenaTsomTV = calendarLayout.findViewById(idArrays[GenaTsom]);
                    GenaTsomTV.setBackgroundColor(Color.parseColor("#292928"));
                    GenaTsomTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(GenaTsomTV, "የገና ፆም");
                }
                else if(key == 4){
                    TextView GenaTV = calendarLayout.findViewById(idArrays[Gena]);
                    GenaTV.setBackgroundColor(Color.parseColor("#b22222"));
                    GenaTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(GenaTV, "ገና");
                } else if(key == 5){
                    TextView TimketTV = calendarLayout.findViewById(idArrays[Timket]);
                    TimketTV.setBackgroundColor(Color.parseColor("#b22222"));
                    TimketTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(TimketTV, "ጥምቀት");
                } else if(key == 12){
                    TextView DebretaborTV = calendarLayout.findViewById(idArrays[Debretabor]);
                    DebretaborTV.setBackgroundColor(Color.parseColor("#b22222"));
                    DebretaborTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(DebretaborTV, "ደብረታቦር");

                    TextView FisletaTV = calendarLayout.findViewById(idArrays[Fisleta]);
                    FisletaTV.setBackgroundColor(Color.parseColor("#292928"));
                    FisletaTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(FisletaTV, "ጾመ ፍልሰታ");
                } else if(key == TinsaeMonth){
                    int Tinsae1 = Tinsae + Mebacha - 1;
                    try {
                        TextView TinsaeTV = calendarLayout.findViewById(idArrays[Tinsae1]);
                        TinsaeTV.setBackgroundColor(Color.parseColor("#b22222"));
                        TinsaeTV.setTextColor(Color.parseColor("#ffffff"));
                        toaster(TinsaeTV, "ተንሳኤ");
                    }catch (Exception e){}

                    if(Tinsae > 2){
                        Seklet = Tinsae1-2;
                        TextView SekletTV = calendarLayout.findViewById(idArrays[Seklet]);
                        SekletTV.setBackgroundColor(Color.parseColor("#b22222"));
                        SekletTV.setTextColor(Color.parseColor("#ffffff"));
                        toaster(SekletTV, "ስቅለት");
                    }

                } else if(key == AbiyTsomMonth){
                    AbiyTsom = (AbiyTsom + Mebacha - 1)%42;
                    TextView AbiyTsomTV = calendarLayout.findViewById(idArrays[AbiyTsom]);
                    AbiyTsomTV.setBackgroundColor(Color.parseColor("#b22222"));
                    AbiyTsomTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(AbiyTsomTV, "አብይ ፆም");
                }

                if(key == DebreZeytMonth){
                    TextView DebrezeytTV = calendarLayout.findViewById(idArrays[(DebreZeyt+Mebacha-1)]);
                    DebrezeytTV.setBackgroundColor(Color.parseColor("#b22222"));
                    DebrezeytTV.setTextColor(Color.parseColor("#ffffff"));
                    toaster(DebrezeytTV, "ደብረዘይት");
                }

            if(key == HosaenaMonth){
                TextView HosaenaTV = calendarLayout.findViewById(idArrays[(Hosaena+Mebacha-1)]);
                HosaenaTV.setBackgroundColor(Color.parseColor("#b22222"));
                HosaenaTV.setTextColor(Color.parseColor("#ffffff"));
                toaster(HosaenaTV, "ሆሳህና");
            }

                counter[0] = counter[0]%7;

                Mebacha = counter[0];
                ll.addView(calendarLayout);
            });
        }
    void toaster(TextView tv, String message){
        TooltipCompat.setTooltipText(tv,message);
    }
    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {}
    private void focusOnView(){
        final int[] amount = new int[1];
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                TextView tv= ll.findViewWithTag(months.get(theMonth));
                TableLayout tl = (TableLayout) tv.getParent().getParent();
                if(theYear == Integer.parseInt(yearInput.getText().toString())){
                    if(theMonth == 2){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.55));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }
                    if(theMonth == 3){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.67));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 4){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.75));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 5){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.8));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 6){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.835));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 7){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.858));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 8){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.875));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 9){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.89));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth == 10){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.9));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }else if(theMonth >= 11){
                        amount[0] = (int) (tl.getBottom()*(theMonth*0.91));
                        scrollView.smoothScrollTo(0, amount[0]);
                    }
                }

                todayTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        yearInput.setText(theYear+"");
                        scrollView.smoothScrollTo(0, amount[0]);
                    }
                });
            }
        });
    }
    void duration(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                focusOnView();
            }
        };
        handler.postDelayed(runnable, 10);
    }
    public void btn_showMessage(TextView tv, String date, int key, String value){
        int dialogYear = Integer.parseInt(yearInput.getText().toString());
        int dialogday = Integer.parseInt(tv.getText().toString());
        if(dialogYear == theYear){
            if(theMonth < key){
                displayDialog(value+" "+tv.getText().toString()+" "+ yearInput.getText().toString());

            }else if(theMonth == key && dialogday>=theDay){
                displayDialog(value+" "+tv.getText().toString()+" "+ yearInput.getText().toString());

            }
        }else if(dialogYear > theYear){
            displayDialog(value+" "+tv.getText().toString()+" "+ yearInput.getText().toString());

        }

    }
    void displayDialog(String date){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        final TimePicker timePicker = mView.findViewById(R.id.timePicker);
        TextView info = mView.findViewById(R.id.info);
        EditText desc = mView.findViewById(R.id.desc);
        Button btn_cancel = mView.findViewById(R.id.btn_cancel);
        Button btn_okay = mView.findViewById(R.id.btn_okay);
        info.setText(date);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Integer.parseInt(date.split(" ")[1]);
                String monthValue = date.split(" ")[0];
                int month = 0;
                for(Map.Entry<Integer, String> entry: months.entrySet()) {
                    if(entry.getValue().trim().equals(monthValue)) {
                        month = entry.getKey();
                        break;
                    }
                }
                int year = Integer.parseInt(date.split(" ")[2]);
                int hour = Integer.parseInt(timePicker.getHour()+"");
                int minute = Integer.parseInt(timePicker.getMinute()+"");

                String description = desc.getText().toString();
                String ampm = "";
                if (hour == 0) {
                    hour = 12+hour;
                    ampm = "AM";
                } else if (hour == 12) {
                    ampm = "PM";
                } else if (hour > 12) {
                    hour = hour-12;
                    ampm = "PM";
                } else {
                    ampm = "AM";
                }
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHandler.addNewMemo(day, month, year, hour, minute, description, ampm);

                OnToggleClicked(hour,minute,year,month,day,ampm);
                Toast.makeText(getContext(), "Data has been added.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void OnToggleClicked(int hour, int minute,int year, int month, int day,String ampm) {
//        long time;
        Calendar calendar = Calendar.getInstance();

        // calendar is called to get current time in hour and minute
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (ampm.equals("AM")) {
            calendar.set(Calendar.AM_PM, 0);
        }else if(ampm.equals("PM")){
            calendar.set(Calendar.AM_PM, 1);
        }
//        calendar.set(year,month,day);

        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, sec);

        long time = calendar.getTimeInMillis();

        Intent i = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, i, PendingIntent.FLAG_IMMUTABLE);

        am.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

    abstract class DoubleClickListener implements View.OnClickListener {
        private long lastClickTime = 0;
        private long DOUBLE_CLICK_TIME_DELTA = 300;
        @Override
        public void onClick(View v) {
            long clickTime = System.currentTimeMillis();
            TextView tv = (TextView)v;
            tv.performLongClick();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v);
            }
            lastClickTime = clickTime;
        }

        abstract void onDoubleClick(View v);

    }

    private void createNotificationChannel(){
        if (ContextCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{POST_NOTIFICATIONS}, 1);
        }
    }
}