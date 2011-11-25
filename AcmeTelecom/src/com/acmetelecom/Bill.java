package com.acmetelecom;

import java.util.List;

import com.acmetelecom.customer.Customer;

public class Bill {
	
	private final Customer customer;
	private final List<BillingSystem.LineItem> calls;
	private final String totalBill;
	
	public Bill(Customer customer, List<BillingSystem.LineItem> calls,
			String totalBill) {
		this.customer = customer;
		this.calls = calls;
		this.totalBill = totalBill;
	}
	
	public void printBill(Printer printer) {
		printer.printHeading(customer.getFullName(), customer.getPhoneNumber(),
				customer.getPricePlan());
		for (BillingSystem.LineItem call : calls) {
			printer.printItem(call.date(), call.callee(), call.durationMinutes(),
					MoneyFormatter.penceToPounds(call.cost()));
		}
		printer.printTotal(totalBill);
	}

}
