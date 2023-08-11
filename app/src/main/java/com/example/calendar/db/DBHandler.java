package com.example.calendar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

	// creating a constant variables for our database.
	// below variable is for our database name.
	private static final String DB_NAME = "memo";
	
	// below int is our database version
	private static final int DB_VERSION = 1;
	
	// below variable is for our table name.
	private static final String TABLE_NAME = "mymemos";
	
	// below variable is for our id column.
	private static final String ID_COL = "id";
	
	// below variable is for our course name column
	private static final String DAY_COL = "day";
	private static final String MONTH_COL = "month";
	private static final String YEAR_COL = "year";

	// below variable id for our course duration column.
	private static final String HOUR_COL = "hour";
	private static final String MINUTE_COL = "minute";

	private static final String AMPM_COL = "ampm";

	// below variable for our course description column.
	private static final String DESCRIPTION_COL = "description";


	// creating a constructor for our database handler.
	public DBHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	// below method is for creating a database by running a sqlite query
	@Override
	public void onCreate(SQLiteDatabase db) {
		// on below line we are creating
		// an sqlite query and we are
		// setting our column names
		// along with their data types.
		String query = "CREATE TABLE " + TABLE_NAME + " ("
				+ ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DAY_COL + " INTEGER,"
				+ MONTH_COL + " INTEGER,"
				+ YEAR_COL + " INTEGER,"
				+ HOUR_COL + " INTEGER,"
				+ MINUTE_COL + " INTEGER,"
				+ DESCRIPTION_COL + " INTEGER,"
				+ AMPM_COL + " INTEGER)";

		// at last we are calling a exec sql
		// method to execute above sql query
		db.execSQL(query);
	}

	// this method is use to add new course to our sqlite database.
	public void addNewMemo(int day, int month, int year, int hour, int minute, String description, String ampm) {
		
		// on below line we are creating a variable for
		// our sqlite database and calling writable method
		// as we are writing data in our database.
		SQLiteDatabase db = this.getWritableDatabase();
		
		// on below line we are creating a
		// variable for content values.
		ContentValues values = new ContentValues();
		
		// on below line we are passing all values
		// along with its key and value pair.
		values.put(DAY_COL, day);
		values.put(MONTH_COL, month);
		values.put(YEAR_COL, year);
		values.put(HOUR_COL, hour);
		values.put(MINUTE_COL, minute);
		values.put(DESCRIPTION_COL, description);
		values.put(AMPM_COL, ampm);

		// after adding all values we are passing
		// content values to our table.
		db.insert(TABLE_NAME, null, values);
		
		// at last we are closing our
		// database after adding database.
		db.close();
	}

	public void addNewMemo(int id, int day, int month, int year, int hour, int minute, String description, String ampm) {

		// on below line we are creating a variable for
		// our sqlite database and calling writable method
		// as we are writing data in our database.
		SQLiteDatabase db = this.getWritableDatabase();

		// on below line we are creating a
		// variable for content values.
		ContentValues values = new ContentValues();

		// on below line we are passing all values
		// along with its key and value pair.
		values.put(ID_COL, id);
		values.put(DAY_COL, day);
		values.put(MONTH_COL, month);
		values.put(YEAR_COL, year);
		values.put(HOUR_COL, hour);
		values.put(MINUTE_COL, minute);
		values.put(DESCRIPTION_COL, description);
		values.put(AMPM_COL, ampm);

		// after adding all values we are passing
		// content values to our table.
		db.insert(TABLE_NAME, null, values);

		// at last we are closing our
		// database after adding database.
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// this method is called to check if the table exists already.
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	// we have created a new method for reading all the courses.
	public ArrayList<MemoModal> readMemo()
	{
		// on below line we are creating a
		// database for reading our database.
		SQLiteDatabase db = this.getReadableDatabase();

		// on below line we are creating a cursor with query to
		// read data from database.
		Cursor courses
				= db.rawQuery("SELECT * FROM " + TABLE_NAME+
				" ORDER BY "+ YEAR_COL+","+ MONTH_COL+","+DAY_COL+ " desc", null);

		// on below line we are creating a new array list.
		ArrayList<MemoModal> courseModalArrayList
				= new ArrayList<>();

		// moving our cursor to first position.
		if (courses.moveToFirst()) {
			do {
				// on below line we are adding the data from
				// cursor to our array list.
				courseModalArrayList.add(new MemoModal(
						courses.getInt(0),
						courses.getInt(1),
						courses.getInt(2),
						courses.getInt(3),
						courses.getInt(4),
						courses.getInt(5),
						courses.getString(6),
						courses.getString(7)));
				System.out.println(courses.getString(0)+" what");
			} while (courses.moveToNext());
			// moving our cursor to next.
		}
		// at last closing our cursor
		// and returning our array list.
		courses.close();
		return courseModalArrayList;
	}


	// below is the method for deleting our course.
	public void deleteCourse(String id) {

		// on below line we are creating
		// a variable to write our database.
		SQLiteDatabase db = this.getWritableDatabase();

		// on below line we are calling a method to delete our
		// course and we are comparing it with our course name.
		db.delete(TABLE_NAME, "id=?", new String[]{id});
		db.close();
	}

	// below is the method for updating our courses
	public void updateCourse(String id,String day, String month, String year,
							 String hour, String minuite, String ampm, String description) {

		// calling a method to get writable database.
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		// on below line we are passing all values
		// along with its key and value pair.
		values.put(DAY_COL, day);
		values.put(MONTH_COL, month);
		values.put(YEAR_COL, year);
		values.put(HOUR_COL, hour);
		values.put(MINUTE_COL, minuite);
		values.put(AMPM_COL, ampm);
		values.put(DESCRIPTION_COL, description);

		// on below line we are calling a update method to update our database and passing our values.
		// and we are comparing it with name of our course which is stored in original name variable.
		db.update(TABLE_NAME, values, "id=?", new String[]{id});
		db.close();
	}


}
