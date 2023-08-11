package com.example.calendar.db;

public class MemoModal {

	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private String description;
	private String ampm;
	private int id;

	// creating getter and setter methods
	public int getDay() { return day; }

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getMonth() {return month;}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getYear() { return year; }
	public int getHour() { return hour; }
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	public int getMinute() { return minute; }
	public void setMinute(int minute)
	{
		this.minute = minute;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getDescription()
	{
		return description;
	}

	public void
	setDescription(String description)
	{
		this.description = description;
	}

	public String getAmpm()
	{
		return ampm;
	}

	public void
	setAmpm(String ampm)
	{
		this.ampm = ampm;
	}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	// constructor
	public MemoModal(int id,
			int day,
					int month,
					int year,
					int hour,
					 int minute,
					 String description,
					 String ampm)
	{
		this.id=id;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		this.description = description;
		this.ampm = ampm;
	}
}
