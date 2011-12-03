package com.acmetelecom;

import java.util.Calendar;
import java.util.Date;

class DaytimePeakPeriod {
	
	private final long peakStart;
	private final long peakEnd;
	
	public DaytimePeakPeriod() {
		this.peakStart = 7;
		this.peakEnd = 19;
	}
	
	/**
	 * Constructor for DaytimePeakPeriod.
	 * 
	 * @param peakStart as hour value. 
	 * @param peakEnd as hour value.
	 */
	public DaytimePeakPeriod(long peakStart, long peakEnd) {
		this.peakStart = peakStart;
		this.peakEnd = peakEnd;
	}

	public boolean offPeak(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour < peakStart || hour >= peakEnd;
	}
	
	/**
	 * @return peak start time in milliseconds
	 */
	public long getPeakStart(){
		return peakStart * 60 * 60 * 1000;
	}

	/**
	 * @return peak end time in milliseconds
	 */
	public long getPeakEnd(){
		return peakEnd * 60 * 60 * 1000;
	}

    /**
    * @return peak start hours
    */

    public long getPeakStartHours() {
        return peakStart;
    }

    /**
    * @return peak end hours
    */

    public long getPeakEndHours() {
        return peakEnd;
    }


}
