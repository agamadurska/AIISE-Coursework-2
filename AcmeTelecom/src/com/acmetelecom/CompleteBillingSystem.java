package com.acmetelecom;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.google.inject.Inject;

public class CompleteBillingSystem implements BillingSystem {
	
	private final CallsLogger callsLogger;
	private final CustomerDatabase customerDatabase;
	private final TariffLibrary tariffLibrary;

	@Inject
	public CompleteBillingSystem(CallsLogger callsLogger,
			CustomerDatabase customerDatabase, TariffLibrary tariffLibrary) {
		this.callsLogger = callsLogger;
		this.customerDatabase = customerDatabase;
		this.tariffLibrary = tariffLibrary;
	}
	
	public List<CustomerBill> createCustomersBill() {
		List<CustomerBill> customersBill = new ArrayList<CustomerBill>();
		for (Customer customer : customerDatabase.getCustomers()) {
			customersBill.add(createBillFor(customer));
		}
		callsLogger.clear();
		return customersBill;
	}
	
	private CustomerBill createBillFor(Customer customer) {
		return new CustomerBill(customer, tariffLibrary,
				callsLogger.getCustomerCallEvents(customer));
	}

}
