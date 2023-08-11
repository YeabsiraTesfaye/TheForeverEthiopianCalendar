package com.example.calendar.db;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoRVAdapter extends RecyclerView.Adapter<MemoRVAdapter.ViewHolder> {

	// variable for our array list and context
	private ArrayList<MemoModal> memoModalArrayList;
	private Context context;
	static HashMap<Integer, String> months = new HashMap<>();
	// constructor
	public MemoRVAdapter(ArrayList<MemoModal> memoModalArrayList, Context context) {
		this.memoModalArrayList = memoModalArrayList;
		this.context = context;
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
		holder.date.setText(months.get(modal.getMonth())+" "+modal.getDay()+" "+modal.getYear());
		holder.time.setText(modal.getHour()+" : "+modal.getMinute()+" "+modal.getAmpm());
		holder.description.setText(modal.getDescription());

	}

	@Override
	public int getItemCount() {
		// returning the size of our array list
		return memoModalArrayList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		
		// creating variables for our text views.
		private TextView date, time, description;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			// initializing our text views
			date = itemView.findViewById(R.id.date1);
			time = itemView.findViewById(R.id.time1);
			description = itemView.findViewById(R.id.description);
		}


	}
}
