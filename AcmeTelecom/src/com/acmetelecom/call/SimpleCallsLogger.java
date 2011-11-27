package com.acmetelecom.call;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.entity.Phone;
import com.acmetelecom.entity.PhoneEntity;

public class SimpleCallsLogger implements CallsLogger {

	// Stores all the call start and end events.
	private final List<CallEvent> callLog;
	
	public SimpleCallsLogger() {
		this.callLog = new ArrayList<CallEvent>();
	}
	
	public void callInitiated(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallStart(caller, callee));
	}

	public void callCompleted(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallEnd(caller, callee));
	}
	
	public void addCallEvent(CallEvent callEvent) {
		callLog.add(callEvent);
	}
	
	public void clear() {
		callLog.clear();
	}
	
	public List<CallEvent> getCustomerCallEvents(Customer customer) {
		List<CallEvent> customerEvents = new ArrayList<CallEvent>();
		for (CallEvent callEvent : callLog) {
			if (callEvent.getCaller().equals(new Phone(customer.getPhoneNumber()))) {
				customerEvents.add(callEvent);
			}
		}
		return customerEvents;
	}

}
