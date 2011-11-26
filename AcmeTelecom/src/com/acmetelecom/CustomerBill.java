package com.acmetelecom;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;

public class CustomerBill {

	private final Customer customer;
	private final List<CallEvent> customerEvents;
	
	public CustomerBill(Customer customer, List<CallEvent> customerEvents) {
		this.customer = customer;
		this.customerEvents = customerEvents;
	}

	protected List<Call> getCustomerCalls() {
		List<Call> calls = new ArrayList<Call>();

		// This assumes that only an end event can follow a start event.
		CallEvent start = null;
		for (CallEvent event : customerEvents) {
			if (event instanceof CallStart) {
				start = event;
			}
			if (event instanceof CallEnd && start != null) {
				calls.add(new Call(start, event));
				start = null;
			}
		}
		return calls;
	}
	
}
