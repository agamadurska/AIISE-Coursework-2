package com.acmetelecom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.entity.PhoneEntity;
import com.acmetelecom.output.HtmlPrinter;

public class BillingSystem {
	
	// Stores all the call start and end events.
	private final List<CallEvent> callLog;
	private final CustomerDatabase customerDatabase;
	private final TariffLibrary tariffLibrary;
	
	public BillingSystem(CustomerDatabase customerDatabase,
			TariffLibrary tariffLibrary) {
		this.callLog = new ArrayList<CallEvent>();
		this.customerDatabase = customerDatabase;
		this.tariffLibrary = tariffLibrary;
	}
	
	public void callInitiated(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallStart(caller, callee));
	}

	public void callCompleted(PhoneEntity caller, PhoneEntity callee) {
		callLog.add(new CallEnd(caller, callee));
	}

	public void createCustomerBills() {
		List<Customer> customers = customerDatabase.getCustomers();
		for (Customer customer : customers) {
			createBillFor(customer);
		}
		callLog.clear();
	}

	/**
	 * Create bills for all the customers.
	 * NOTE: This method should only be used for testing
	 * @param customers a list containing all the customers.
	 */
	protected void createCustomerBills(List<Customer> customers) {
		for (Customer customer : customers) {
			createBillFor(customer);
		}
		callLog.clear();    	
	}
    
	protected List<CallEvent> getCustomerCallEvents(Customer customer) {
		List<CallEvent> customerEvents = new ArrayList<CallEvent>();
		for (CallEvent callEvent : callLog) {
			if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
				customerEvents.add(callEvent);
			}
		}
		return customerEvents;
	}
     
	private void createBillFor(Customer customer) {
		CustomerBill customerBill = new CustomerBill(customer, tariffLibrary,
				getCustomerCallEvents(customer));
		BigDecimal totalBill = customerBill.charge();
		Bill bill = new Bill(customer, customerBill.getCustomerCalls(),
				MoneyFormatter.penceToPounds(totalBill));
		bill.printBill(new HtmlPrinter(System.out));
	}

}
