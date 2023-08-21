package com.example.calendar.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;
import com.example.calendar.databinding.FragmentDashboardBinding;
import com.example.calendar.db.DBHandler;
import com.example.calendar.db.MemoModal;
import com.example.calendar.db.MemoRVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ArrayList<MemoModal> memoModalArrayList;
    private DBHandler dbHandler;
    private MemoRVAdapter memoRVAdapter;
    static HashMap<String, Integer> months = new HashMap<>();

    private RecyclerView coursesRV;
    private FloatingActionButton fab;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences prefs = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        months.put("መስከረም", 1);
        months.put("ጥቅምት", 2);
        months.put("ህዳር", 3);
        months.put("ታህሳስ", 4);
        months.put("ጥር", 5);
        months.put("የካቲት", 6);
        months.put("መጋቢት", 7);
        months.put("ሚያዚያ", 8);
        months.put("ግንቦት", 9);
        months.put("ሰኔ", 10);
        months.put("ሀምሌ", 11);
        months.put("ነሀሴ", 12);
        months.put("ጳጉሜን", 13);

        memoModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getContext());

        memoModalArrayList = dbHandler.readMemo();

        memoRVAdapter = new MemoRVAdapter(memoModalArrayList, getContext());
        coursesRV = root.findViewById(R.id.idRVCourses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);
        coursesRV.setAdapter(memoRVAdapter);
        fab = root.findViewById(R.id.add_person_fab);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int id = memoModalArrayList.get(position).getId();
                MemoModal deletedCourse = memoModalArrayList.get(position);
                dbHandler.deleteCourse(id+"");
                memoModalArrayList.remove(position);
                memoRVAdapter.notifyItemRemoved(position);

                Snackbar.make(coursesRV, "delete memo?", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memoModalArrayList.add(position,deletedCourse);
                        dbHandler.addNewMemo(deletedCourse.getId(),deletedCourse.getDay(),deletedCourse.getMonth(),deletedCourse.getYear(),deletedCourse.getHour(),deletedCourse.getMinute(),deletedCourse.getDescription(),deletedCourse.getAmpm());
                        memoRVAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(coursesRV);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void displayDialog(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog_2,null);

        String[] monthsRes = getResources().getStringArray(R.array.months);
        ArrayAdapter monthArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, monthsRes);
        AutoCompleteTextView month = mView.findViewById(R.id.months);
        month.setAdapter(monthArrayAdapter);

        String[] days = getResources().getStringArray(R.array.days);


        final TimePicker timePicker = mView.findViewById(R.id.timePicker);
        EditText desc = mView.findViewById(R.id.desc);
        AutoCompleteTextView dayET = mView.findViewById(R.id.days);
        AutoCompleteTextView monthET = mView.findViewById(R.id.months);
        TextInputLayout yearET = mView.findViewById(R.id.year);

        monthET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String dayval = dayET.getEditableText().toString();

                String[] daysPuagume5 = getResources().getStringArray(R.array.puagume5);
                String[] daysPuagume6 = getResources().getStringArray(R.array.puagume6);
                if (monthET.getText().toString().equals("ጳጉሜ")) {
                    if(!yearET.getEditText().getText().toString().equals("")){
                        if(Integer.parseInt(yearET.getEditText().getText().toString())%4 == 3) {
                            ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysPuagume6);
                            AutoCompleteTextView day = mView.findViewById(R.id.days);
                            day.setAdapter(dayArrayAdapter);
                            if(!day.equals("")){
                                if (Integer.parseInt(dayval) > 6) {
                                    dayET.setText(dayET.getAdapter().getItem(5).toString(), false);
                                }
                            }
                        }else{
                            ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysPuagume5);
                            AutoCompleteTextView day = mView.findViewById(R.id.days);
                            day.setAdapter(dayArrayAdapter);
                            if(!dayval.equals("")){
                                if (Integer.parseInt(dayval) > 5) {
                                    dayET.setText(dayET.getAdapter().getItem(4).toString(), false);
                                }
                            }
                        }
                    }
                }else{
                    ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, days);
                    AutoCompleteTextView day = mView.findViewById(R.id.days);
                    day.setAdapter(dayArrayAdapter);
                }
            }
        });
        yearET.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] daysPuagume5 = getResources().getStringArray(R.array.puagume5);
                String[] daysPuagume6 = getResources().getStringArray(R.array.puagume6);
                if (monthET.getText().toString().equals("ጳጉሜ")) {
                    if(!yearET.getEditText().getText().toString().equals("")){
                        if(Integer.parseInt(yearET.getEditText().getText().toString())%4 == 3) {
                            ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysPuagume6);
                            AutoCompleteTextView day = mView.findViewById(R.id.days);
                            day.setAdapter(dayArrayAdapter);
                        }else{
                            ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, daysPuagume5);
                            AutoCompleteTextView day = mView.findViewById(R.id.days);
                            day.setAdapter(dayArrayAdapter);
                        }
                    }
                }else{
                    ArrayAdapter dayArrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_menu, days);
                    AutoCompleteTextView day = mView.findViewById(R.id.days);
                    day.setAdapter(dayArrayAdapter);
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button btn_cancel = mView.findViewById(R.id.btn_cancel);
        Button btn_okay = mView.findViewById(R.id.btn_okay);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        dayET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearET);
            }
        });
        monthET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), yearET);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = 0;
                if(!dayET.getText().toString().equals("")){
                    day = Integer.parseInt(dayET.getText().toString());
                }else{
                    Toast.makeText(getContext(), "Day cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                int month=0;
                if(!monthET.getText().toString().equals("")){
                    month = months.get(monthET.getText().toString());
                }else{
                    Toast.makeText(getContext(), "Month cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                int year = 0;

                if (yearET.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the data.. y", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    year = Integer.parseInt(yearET.getEditText().getText().toString());
                }

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

                SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                int theYear = sh.getInt("year", 0);
                int theMonth = sh.getInt("month", 0);
                int theDay = sh.getInt("day", 0);
                if(year < theYear){
                    Toast.makeText(getContext(), "Year cant be less than "+theYear, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(year == theYear){
                    if(month < theMonth){
                        for(Map.Entry<String, Integer> entry: months.entrySet()) {

                            // if give value is equal to value from entry
                            // print the corresponding key
                            if(entry.getValue() == theMonth) {
                                Toast.makeText(getContext(), "Month cant be less than "+entry.getKey(), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        return;
                    }else if(month == theMonth){
                        if(day < theDay){
                            Toast.makeText(getContext(), "Day cant be less than "+theDay, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHandler.addNewMemo(day, month, year, hour, minute, description, ampm);
                MemoModal memo = new MemoModal(1,day,month,year,hour,minute,description,ampm);
                memoModalArrayList.add(memo);
                memoRVAdapter.notifyDataSetChanged();


                Toast.makeText(getContext(), "Data has been added.", Toast.LENGTH_SHORT).show();

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}