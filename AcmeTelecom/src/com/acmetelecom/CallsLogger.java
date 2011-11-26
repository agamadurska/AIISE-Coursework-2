package com.acmetelecom;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.entity.PhoneEntity;

public class CallsLogger {

	// Stores all the call start and end events.
	private final List<CallEvent> callLog;
	
	public CallsLogger() {
		this.callLog = new ArrayList<CallEvent>();
	}
	
	public void callInitiated(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallStart(caller, callee));
	}

	public void callCompleted(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallEnd(caller, callee));
	}
	
	public void clear() {
		callLog.clear();
	}
	
	public List<CallEvent> getCustomerCallEvents(Customer customer) {
		List<CallEvent> customerEvents = new ArrayList<CallEvent>();
		for (CallEvent callEvent : callLog) {
			if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
				customerEvents.add(callEvent);
			}
		}
		return customerEvents;
	}

}
