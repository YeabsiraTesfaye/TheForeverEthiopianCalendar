package com.example.calendar.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendar.R;
import com.example.calendar.databinding.FragmentNotificationsBinding;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    HashMap<String, Integer> monthsGR = new HashMap<>();
    HashMap<Integer, String> monthsET = new HashMap<>();
    AutoCompleteTextView resultET;
    AutoCompleteTextView resultGR;
    AutoCompleteTextView dayGR;
    AutoCompleteTextView monthGR;
    TextInputLayout yearGR;
    TextInputLayout yearET;
    AutoCompleteTextView dayET;
    AutoCompleteTextView monthET;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        monthsGR.put("January", 1);
        monthsGR.put("February", 2);
        monthsGR.put("March", 3);
        monthsGR.put("April", 4);
        monthsGR.put("May", 5);
        monthsGR.put("June", 6);
        monthsGR.put("July", 7);
        monthsGR.put("August", 8);
        monthsGR.put("September", 9);
        monthsGR.put("October", 10);
        monthsGR.put("November", 11);
        monthsGR.put("December", 12);

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

        dayGR = root.findViewById(R.id.daysGR);
        yearGR = root.findViewById(R.id.yearGR);
        monthGR = root.findViewById(R.id.monthsGR);
        resultET = root.findViewById(R.id.resultET);
        resultGR = root.findViewById(R.id.resultGR);

        String[] monthsResGR = getResources().getStringArray(R.array.monthsGR);
        ArrayAdapter monthArrayAdapterGR = new ArrayAdapter(getContext(), R.layout.dropdown_menu, monthsResGR);
        monthGR.setAdapter(monthArrayAdapterGR);


        monthET = root.findViewById(R.id.monthsET);
        yearET = root.findViewById(R.id.yearET);

        String[] monthsResET = getResources().getStringArray(R.array.months);
        ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, monthsResET);
        monthET.setAdapter(monthArrayAdapterET);
        dayET = root.findViewById(R.id.daysET);

        monthET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearGR);
                hideKeyboardFrom(getContext(), yearET);
            }
        });
        dayET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearGR);
                hideKeyboardFrom(getContext(), yearET);
            }
        });
        monthET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String day = dayET.getEditableText().toString();
                if (!yearET.getEditText().getText().toString().equals("")) {
                    if (i != 12) {
                        String[] daysResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                        dayET.setAdapter(monthArrayAdapterET);
                    } else {
                        int year = Integer.parseInt(yearET.getEditText().getText().toString());
                        if (year % 4 == 3) {
                            String[] daysResET = getResources().getStringArray(R.array.puagume6);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                            if(!day.equals("")){
                                if (Integer.parseInt(day) > 6) {
                                    dayET.setText(dayET.getAdapter().getItem(5).toString(), false);
                                }
                            }

                        } else {
                            String[] daysResET = getResources().getStringArray(R.array.puagume5);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                            if(!day.equals("")){
                                if (Integer.parseInt(day) > 5) {
                                    dayET.setText(dayET.getAdapter().getItem(4).toString(), false);
                                }
                            }


                        }
                    }
                }
                if (!yearET.getEditText().getText().toString().equals("") && !monthET.getEditableText().toString().equals("") && !dayET.getEditableText().toString().equals("")) {
                    toGR(dayET.getText().toString(), monthET.getEditableText().toString(), yearET.getEditText().getText().toString());
                }

            }
        });
        yearET.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!yearET.getEditText().getText().toString().equals("")) {
                    int month = 0;
                    for (Map.Entry<Integer, String> entry : monthsET.entrySet()) {
                        if (entry.getValue().equals(monthET.getEditableText().toString())) {
                            month = entry.getKey();
                            break;
                        }
                    }
                    if (month != 13) {
                        String[] daysResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                        dayET.setAdapter(monthArrayAdapterET);
                    } else {
                        int year = Integer.parseInt(yearET.getEditText().getText().toString());
                        if (year % 4 == 3) {
                            String[] daysResET = getResources().getStringArray(R.array.puagume6);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                        } else {
                            String[] daysResET = getResources().getStringArray(R.array.puagume5);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                            dayET.setText(dayET.getAdapter().getItem(4).toString(), false);
                        }
                    }
                }else{
                    resultGR.setText("");
                }
                if (!yearET.getEditText().getText().toString().equals("") && !monthET.getEditableText().toString().equals("") && !dayET.getEditableText().toString().equals("")) {
                    toGR(dayET.getText().toString(), monthET.getEditableText().toString(), yearET.getEditText().getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dayET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!yearET.getEditText().getText().toString().equals("")) {
                    int month = 0;
                    for (Map.Entry<Integer, String> entry : monthsET.entrySet()) {
                        if (entry.getValue().equals(monthET.getEditableText().toString())) {
                            month = entry.getKey();
                            break;
                        }
                    }
                    if (month != 13) {
                        String[] daysResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                        dayET.setAdapter(monthArrayAdapterET);
                    } else {
                        int year = Integer.parseInt(yearET.getEditText().getText().toString());
                        if (year % 4 == 3) {
                            String[] daysResET = getResources().getStringArray(R.array.puagume6);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                        } else {
                            String[] daysResET = getResources().getStringArray(R.array.puagume5);
                            ArrayAdapter monthArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysResET);
                            dayET.setAdapter(monthArrayAdapterET);
                        }
                    }
                }
                if (!yearET.getEditText().getText().toString().equals("") && !monthET.getEditableText().toString().equals("") && !dayET.getEditableText().toString().equals("")) {
                    toGR(dayET.getText().toString(), monthET.getEditableText().toString(), yearET.getEditText().getText().toString());
                }


            }
        });


        monthGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearGR);
            }
        });
        dayGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearGR);
            }
        });
        yearGR.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!dayGR.getEditableText().toString().equals("") && !monthGR.getEditableText().toString().equals("") && !yearGR.getEditText().getText().toString().equals("")) {
                    toET(dayGR.getEditableText().toString(), monthGR.getEditableText().toString(), yearGR.getEditText().getText().toString());
                }else{
                    resultET.setText("");
                }
                if (!yearGR.getEditText().getText().toString().equals("") && !monthGR.getEditableText().toString().equals("")) {
                    int monthId = monthsGR.get(monthGR.getEditableText().toString());
                    if (monthId == 1 || monthId == 3 || monthId == 5 || monthId == 7 || monthId == 8 || monthId == 10 || monthId == 12) {

                        String[] dayResET = getResources().getStringArray(R.array.daysGR);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (monthId == 4 || monthId == 6 || monthId == 9 || monthId == 11) {
                        String[] dayResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (monthId == 2) {
                        if (Integer.parseInt(yearGR.getEditText().getText().toString()) % 4 == 0) {
                            String[] dayResET = getResources().getStringArray(R.array.february29);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                        } else {
                            String[] dayResET = getResources().getStringArray(R.array.february28);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        monthGR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String day = dayGR.getEditableText().toString();
                if (!yearGR.getEditText().getText().toString().equals("")) {
                    if (i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11) {

                        String[] dayResET = getResources().getStringArray(R.array.daysGR);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (i == 3 || i == 5 || i == 8 || i == 10) {
                        String[] dayResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (i == 1) {
                        if (Integer.parseInt(yearGR.getEditText().getText().toString()) % 4 == 0) {
                            String[] dayResET = getResources().getStringArray(R.array.february29);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                            if(!day.equals("")){
                                if (Integer.parseInt(day) > 29) {
                                    dayGR.setText(dayGR.getAdapter().getItem(28).toString(), false);
                                }
                            }

                        } else {
                            String[] dayResET = getResources().getStringArray(R.array.february28);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                            if(!day.equals("")){
                                if (Integer.parseInt(day) > 28) {
                                    dayGR.setText(dayGR.getAdapter().getItem(27).toString(), false);
                                }
                            }
                        }
                    }
                }
                if (!dayGR.getEditableText().toString().equals("") && !monthGR.getEditableText().toString().equals("") && !yearGR.getEditText().getText().toString().equals("")) {
                    toET(dayGR.getEditableText().toString(), monthGR.getEditableText().toString(), yearGR.getEditText().getText().toString());
                }
            }
        });
        dayGR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!dayGR.getEditableText().toString().equals("") && !monthGR.getEditableText().toString().equals("") && !yearGR.getEditText().getText().toString().equals("")) {
                    toET(dayGR.getEditableText().toString(), monthGR.getEditableText().toString(), yearGR.getEditText().getText().toString());
                }

                if (!yearGR.getEditText().getText().toString().equals("")) {
                    if (i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11) {

                        String[] dayResET = getResources().getStringArray(R.array.daysGR);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (i == 3 || i == 5 || i == 8 || i == 10) {
                        String[] dayResET = getResources().getStringArray(R.array.days);
                        ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                        dayGR.setAdapter(daysArrayAdapterET);
                    } else if (i == 1) {
                        if (Integer.parseInt(yearGR.getEditText().getText().toString()) % 4 == 0) {
                            String[] dayResET = getResources().getStringArray(R.array.february29);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                        } else {
                            String[] dayResET = getResources().getStringArray(R.array.february28);
                            ArrayAdapter daysArrayAdapterET = new ArrayAdapter(getContext(), R.layout.dropdown_menu, dayResET);
                            dayGR.setAdapter(daysArrayAdapterET);
                        }
                    }
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

    public void toET(String dayGR, String monthGR, String yearGR) {
        int day = -1;
        int month = -1;
        int year = -1;

        int todaysDay = Integer.parseInt(dayGR);
        int todaysMonth = monthsGR.get(monthGR);
        int todaysYear = Integer.parseInt(yearGR);

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
        if (monthsGR.get(monthGR) == 4 || monthsGR.get(monthGR) == 6
                || monthsGR.get(monthGR) == 9 || monthsGR.get(monthGR) == 10) {

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
        resultET.setText(dayName + " " + monthsET.get(month) + " " + day + " " + year);
    }

    public void toGR(String dayET, String monthET, String yearET) {

        int day = Integer.parseInt(dayET);
        int month = 0;

        for (Map.Entry<Integer, String> entry : monthsET.entrySet()) {
            if (entry.getValue().equals(monthET)) {
                month = entry.getKey();
                break;
            }
        }
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
        String monthsName = "";
        for (Map.Entry<String, Integer> entry : monthsGR.entrySet()) {
            if (entry.getValue().equals(monthGR)) {
                monthsName = entry.getKey();
                break;
            }
        }
        resultGR.setText(monthsName + " " + dayGR + " " + yearGR);

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}