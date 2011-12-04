package com.acmetelecom.call;

import java.util.List;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.entity.PhoneEntity;

/**
 * Interface for a call-logger.
 */
public interface CallsLogger {

	void callInitiated(PhoneEntity caller, PhoneEntity callee);
	
	void callCompleted(PhoneEntity caller, PhoneEntity callee);
	
	void addCallEvent(CallEvent callEvent);
	
	void clear();
	
	List<CallEvent> getCustomerCallEvents(Customer customer);
	
}
