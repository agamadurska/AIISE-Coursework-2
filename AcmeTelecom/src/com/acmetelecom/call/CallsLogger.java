package com.acmetelecom.call;

import java.util.List;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.entity.PhoneEntity;

public interface CallsLogger {

	void callInitiated(PhoneEntity caller, PhoneEntity callee);
	
	void callCompleted(PhoneEntity caller, PhoneEntity callee);
	
	void clear();
	
	List<CallEvent> getCustomerCallEvents(Customer customer);
	
}
