package com.acmetelecom;

import java.util.List;

import com.acmetelecom.customer.Customer;

/**
 * This class dispatches a billing report to the HmtlPrinter.
 */
public class BillGenerator {

	private final Printer printer;
	
	public BillGenerator(Printer printer) {
		this.printer = printer;
	}
	
    public void send(Customer customer, List<BillingSystem.LineItem> calls, String totalBill) {
        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (BillingSystem.LineItem call : calls) {
            printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

}
