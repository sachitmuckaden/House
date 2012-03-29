package com.MAS.utils;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampUtil{
	
	public Timestamp getTimeStamp()
	{
		long current_time;
		Date date = new Date();
		current_time = date.getTime();
		Timestamp currenttime = new Timestamp(current_time);
		return currenttime;
	}
}
