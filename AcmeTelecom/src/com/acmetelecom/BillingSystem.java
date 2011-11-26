package com.acmetelecom;

import java.util.List;

import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.output.HtmlPrinter;

public class BillingSystem {
	
	private final CallsLogger callsLogger;
	private final CustomerDatabase customerDatabase;
	private final TariffLibrary tariffLibrary;
	
	public BillingSystem(CallsLogger callsLogger,
			CustomerDatabase customerDatabase, TariffLibrary tariffLibrary) {
		this.callsLogger = callsLogger;
		this.customerDatabase = customerDatabase;
		this.tariffLibrary = tariffLibrary;
	}
	
	public void createCustomerBills() {
		List<Customer> customers = customerDatabase.getCustomers();
		for (Customer customer : customers) {
			createBillFor(customer);
		}
		callsLogger.clear();
	}
    
	private void createBillFor(Customer customer) {
		CustomerBill customerBill = new CustomerBill(customer, tariffLibrary,
				callsLogger.getCustomerCallEvents(customer));
		customerBill.printBill(new HtmlPrinter(System.out));
	}

}
