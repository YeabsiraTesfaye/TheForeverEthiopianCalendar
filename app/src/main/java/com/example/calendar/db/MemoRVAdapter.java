package com.example.calendar.db;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;
import com.example.calendar.ui.notifications.NotificationsFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MemoRVAdapter extends RecyclerView.Adapter<MemoRVAdapter.ViewHolder> {

	// variable for our array list and context
	private ArrayList<MemoModal> memoModalArrayList;
	private Context context;
	DBHandler dbHandler;
	static HashMap<Integer, String> months1 = new HashMap<>();
	static HashMap<String, Integer> months = new HashMap<>();
	// constructor
	public MemoRVAdapter(ArrayList<MemoModal> memoModalArrayList, Context context) {
		this.memoModalArrayList = memoModalArrayList;
		dbHandler = new DBHandler(context);
		this.context = context;
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
		months.put("ሐምሌ", 11);
		months.put("ነሀሴ", 12);
		months.put("ጳጉሜ", 13);

		months1.put(1, "መስከረም");
		months1.put(2, "ጥቅምት");
		months1.put(3, "ህዳር");
		months1.put(4, "ታህሳስ");
		months1.put(5, "ጥር");
		months1.put(6, "የካቲት");
		months1.put(7, "መጋቢት");
		months1.put(8, "ሚያዚያ");
		months1.put(9, "ግንቦት");
		months1.put(10, "ሰኔ");
		months1.put(11, "ሐምሌ");
		months1.put(12, "ነሀሴ");
		months1.put(13, "ጳጉሜ");

	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// on below line we are inflating our layout
		// file for our recycler view items.
		View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		// on below line we are setting data
		// to our views of recycler view item.
		MemoModal modal = memoModalArrayList.get(position);
		holder.date.setText(months1.get(modal.getMonth())+" "+modal.getDay()+" "+modal.getYear());
		holder.time.setText(modal.getHour()+" : "+modal.getMinute()+" "+modal.getAmpm());
		holder.description.setText(modal.getDescription());
		holder.id.setText(modal.getId()+"");
		holder.itemView.setOnClickListener(view -> {
			displayDialog(modal.getDay()+"", modal.getMonth(), modal.getYear()+"", modal.getMinute(), modal.getHour(),modal.getDescription(),modal.getAmpm(), modal.getId(),position);
		});
	}

	@Override
	public int getItemCount() {
		// returning the size of our array list
		return memoModalArrayList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		// creating variables for our text views.
		private TextView date, time, description,id;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			// initializing our text views
			date = itemView.findViewById(R.id.date1);
			time = itemView.findViewById(R.id.time1);
			description = itemView.findViewById(R.id.description);
			id = itemView.findViewById(R.id.id);
		}


	}



	void displayDialog(String dayd, int monthd, String yeard, int minuted, int hourd, String Descriptiond, String am_pmd,int id, int position){
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView = inflater.inflate(R.layout.custom_dialog_2,null);

		String[] months1Res = context.getResources().getStringArray(R.array.months);
		ArrayAdapter monthArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, months1Res);
		AutoCompleteTextView month = mView.findViewById(R.id.months);
		month.setText(months1.get(monthd));
		month.setAdapter(monthArrayAdapter);

		String[] days = context.getResources().getStringArray(R.array.days);
		String[] daysPuagume5 = context.getResources().getStringArray(R.array.puagume5);
		String[] daysPuagume6 = context.getResources().getStringArray(R.array.puagume6);

		final TimePicker timePicker = mView.findViewById(R.id.timePicker);

		timePicker.setHour(hourd);
		timePicker.setMinute(minuted);

		EditText desc = mView.findViewById(R.id.desc);
		desc.setText(Descriptiond);

		AutoCompleteTextView dayET = mView.findViewById(R.id.days);
		AutoCompleteTextView monthET = mView.findViewById(R.id.months);
		TextInputLayout yearET = mView.findViewById(R.id.year);


		yearET.getEditText().setText(yeard);

		if (monthET.getText().toString().equals("ጳጉሜ")) {
				if(Integer.parseInt(yearET.getEditText().getText().toString())%4 == 3) {
					ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume6);
					AutoCompleteTextView day = mView.findViewById(R.id.days);
					day.setText(dayd);
					day.setAdapter(dayArrayAdapter);
				}else{
					ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume5);
					AutoCompleteTextView day = mView.findViewById(R.id.days);
					day.setText(dayd);
					day.setAdapter(dayArrayAdapter);
				}
		}else{
			ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, days);
			AutoCompleteTextView day = mView.findViewById(R.id.days);
			day.setText(dayd);
			day.setAdapter(dayArrayAdapter);
		}


		monthET.setOnItemClickListener((adapterView, view, i, l) -> {
			String dayval = dayET.getEditableText().toString();

			String[] daysPuagume51 = context.getResources().getStringArray(R.array.puagume5);
			String[] daysPuagume61 = context.getResources().getStringArray(R.array.puagume6);
			if (monthET.getText().toString().equals("ጳጉሜ")) {
				if(!yearET.getEditText().getText().toString().equals("")){
					if(Integer.parseInt(yearET.getEditText().getText().toString())%4 == 3) {
						ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume61);
						AutoCompleteTextView day = mView.findViewById(R.id.days);
						day.setText(dayd);
						day.setAdapter(dayArrayAdapter);
						if(!day.equals("")){
							if (Integer.parseInt(dayval) > 6) {
								dayET.setText(dayET.getAdapter().getItem(5).toString(), false);
							}
						}
					}else{
						ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume51);
						AutoCompleteTextView day = mView.findViewById(R.id.days);
						day.setText(dayd);
						day.setAdapter(dayArrayAdapter);
						if(!dayval.equals("")){
							if (Integer.parseInt(dayval) > 5) {
								dayET.setText(dayET.getAdapter().getItem(4).toString(), false);
							}
						}
					}
				}
			}else{
				ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, days);
				AutoCompleteTextView day = mView.findViewById(R.id.days);
				day.setText(dayd);
				day.setAdapter(dayArrayAdapter);
			}
		});
		yearET.getEditText().addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String[] daysPuagume5 = context.getResources().getStringArray(R.array.puagume5);
				String[] daysPuagume6 = context.getResources().getStringArray(R.array.puagume6);
				if (monthET.getText().toString().equals("ጳጉሜ")) {
					if(!yearET.getEditText().getText().toString().equals("")){
						if(Integer.parseInt(yearET.getEditText().getText().toString())%4 == 3) {
							ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume6);
							AutoCompleteTextView day = mView.findViewById(R.id.days);
							day.setAdapter(dayArrayAdapter);
						}else{
							ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, daysPuagume5);
							AutoCompleteTextView day = mView.findViewById(R.id.days);
							day.setAdapter(dayArrayAdapter);
						}
					}
				}else{
					ArrayAdapter dayArrayAdapter = new ArrayAdapter(context, R.layout.dropdown_menu, days);
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
				hideKeyboardFrom(context, yearET);
			}
		});
		monthET.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				hideKeyboardFrom(context, yearET);
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
					Toast.makeText(context, "Day cant be empty", Toast.LENGTH_SHORT).show();
					return;
				}
				int month=0;
				if(!monthET.getText().toString().equals("")){
					month = months.get(monthET.getText().toString());
				}else{
					Toast.makeText(context, "Month cant be empty", Toast.LENGTH_SHORT).show();
					return;
				}
				int year = 0;

				if (yearET.getEditText().getText().toString().isEmpty()) {
					Toast.makeText(context, "Please enter all the data.. y", Toast.LENGTH_SHORT).show();
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

				SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
				int theYear = sh.getInt("year", 0);
				int theMonth = sh.getInt("month", 0);
				int theDay = sh.getInt("day", 0);
				if(year < theYear){
					Toast.makeText(context, "Year cant be less than "+theYear, Toast.LENGTH_SHORT).show();
					return;
				}
				if(year == theYear){
					if(month < theMonth){
						for(Map.Entry<String, Integer> entry: months.entrySet()) {

							// if give value is equal to value from entry
							// print the corresponding key
							if(entry.getValue() == theMonth) {
								Toast.makeText(context, "Month cant be less than "+entry.getKey(), Toast.LENGTH_SHORT).show();
								break;
							}
						}
						return;
					}else if(month == theMonth){
						if(day < theDay){
							Toast.makeText(context, "Day cant be less than "+theDay, Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
				if (description.isEmpty()) {
					Toast.makeText(context, "Please enter all the data..", Toast.LENGTH_SHORT).show();
					return;
				}
				dbHandler.updateCourse(id+"",day+"", month+"", year+"", hour+"", minute+"", ampm, description);
				Toast.makeText(context, "Data has been updated.", Toast.LENGTH_SHORT).show();
				
				((AppCompatActivity) context).runOnUiThread(() -> {
					MemoRVAdapter.this.notifyDataSetChanged();
					//context.refreshInbox();
				});

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
