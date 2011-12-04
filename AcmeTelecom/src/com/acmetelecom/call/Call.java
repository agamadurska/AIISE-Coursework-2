package com.acmetelecom.call;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.acmetelecom.entity.PhoneEntity;

public class Call {

	private CallEvent start;
	private CallEvent end;
	private BigDecimal callCost;
	
	public Call(CallEvent start, CallEvent end) {
		this.start = start;
		this.end = end;
		this.callCost = new BigDecimal(0);
	}
	
	public Call(CallEvent start, CallEvent end, BigDecimal callCost) {
		this.start = start;
		this.end = end;
		this.callCost = callCost;		
	}
	
	public PhoneEntity callee() {
		return start.getCallee();
	}

	public int durationSeconds() {
		return (int) (((end.time() - start.time()) / 1000));
	}
	
	public String date() {
		return SimpleDateFormat.getInstance().format(new Date(start.time()));
	}
	
	public Date startTime() {
	  	return new Date(start.time());
	}
	
	public Date endTime() {
	    return new Date(end.time());
	}
	  
	public void setCallCost(BigDecimal callCost) {
	  	this.callCost = callCost;
	}
  
	public String durationMinutes() {
		int duration = durationSeconds();
		return "" + duration / 60 + ":" + String.format("%02d", duration % 60);
	}

	public BigDecimal cost() {
		return callCost;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Call) {
			Call call = (Call)object;
			return callee().equals(call.callee()) &&
					startTime().equals(call.startTime()) &&
					endTime().equals(call.endTime()) &&
					cost().compareTo(call.cost()) == 0;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return start.hashCode() + end.hashCode() + callCost.hashCode();
	}
}
