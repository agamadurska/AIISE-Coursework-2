package com.acmetelecom;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

class DaytimePeakPeriod {
	
	private long pickStart = 7;
	private long pickEnd = 19;
	

	public boolean offPeak(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour < pickStart || hour >= pickEnd;
	}
	
	public long getPickStart(){
		return pickStart * 60 * 60 * 1000;
	}
	
	public long getPickEnd(){
		return pickEnd * 60 * 60 * 1000;
	}
	

}
