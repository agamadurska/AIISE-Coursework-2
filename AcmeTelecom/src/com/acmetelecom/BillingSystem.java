package com.acmetelecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
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
		CustomerBill customerBill = new CustomerBill(customer,
				getCustomerCallEvents(customer)); 
		List<Call> calls = customerBill.getCustomerCalls();
		BigDecimal totalBill = new BigDecimal(0);
		List<LineItem> items = new ArrayList<LineItem>();

		for (Call call : calls) {

			Tariff tariff = tariffLibrary.tarriffFor(customer);

			BigDecimal cost;

			DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
			if (peakPeriod.offPeak(call.startTime()) &&
					peakPeriod.offPeak(call.endTime()) &&
					call.durationSeconds() < 12 * 60 * 60) {
				cost = new BigDecimal(call.durationSeconds())
						.multiply(tariff.offPeakRate());
			} else {
				cost = new BigDecimal(call.durationSeconds())
						.multiply(tariff.peakRate());
			}

			cost = cost.setScale(0, RoundingMode.HALF_UP);
			BigDecimal callCost = cost;
			totalBill = totalBill.add(callCost);
			items.add(new LineItem(call, callCost));
		}

		Bill bill = new Bill(customer, items,
				MoneyFormatter.penceToPounds(totalBill));
		bill.printBill(new HtmlPrinter(System.out));
	}

	/**
	 * State class to represent the cost of an call.
	 */
	static class LineItem {
		private Call call;
		private BigDecimal callCost;

		public LineItem(Call call, BigDecimal callCost) {
			this.call = call;
			this.callCost = callCost;
		}

		public String date() {
			return call.date();
		}

		public PhoneEntity callee() {
			return call.callee();
		}

		public String durationMinutes() {
			return "" + call.durationSeconds() / 60 + ":" +
					String.format("%02d", call.durationSeconds() % 60);
		}

		public BigDecimal cost() {
			return callCost;
		}
	}

}
