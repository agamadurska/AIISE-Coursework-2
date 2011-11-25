package com.acmetelecom;

import java.util.List;

import com.acmetelecom.customer.Customer;

public class CustomerBill {

	private final Customer customer;
	private final List<CallEvent> customerEvents;
	
	public CustomerBill(Customer customer, List<CallEvent> customerEvents) {
		this.customer = customer;
		this.customerEvents = customerEvents;
	}

	
}
