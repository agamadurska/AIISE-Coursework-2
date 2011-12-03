package com.acmetelecom;

import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.entity.Phone;
import com.acmetelecom.output.Printer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestSystem {

    public static void main(String[] args) throws Exception {
		System.out.println("Running...");

		Phone phone1 = new Phone("447722113434");
		Phone phone2 = new Phone("447766814143");
		Phone phone3 = new Phone("447777765432");
		Phone phone4 = new Phone("447711111111");

		Injector injector = Guice.createInjector(new BillingModule());
		CustomerDatabase customerDatabase =
				injector.getInstance(CustomerDatabase.class);
		TariffLibrary tariffLibrary = injector.getInstance(TariffLibrary.class);
		CallsLogger callsLogger = injector.getInstance(CallsLogger.class);

		callsLogger.callInitiated(phone1, phone2);
		sleepSeconds(2);
		callsLogger.callCompleted(phone1, phone2);
		callsLogger.callInitiated(phone1, phone4);
		sleepSeconds(3);
		callsLogger.callCompleted(phone1, phone4);
		callsLogger.callInitiated(phone3, phone4);
		sleepSeconds(6);
		callsLogger.callCompleted(phone3, phone4);

		BillingSystem billingSystem = new CompleteBillingSystem(callsLogger,
				customerDatabase, tariffLibrary);
		Printer printer = injector.getInstance(Printer.class);

		for (CustomerBill customerBill : billingSystem.createCustomersBill()) {
			customerBill.printBill(printer);
		}
	}

	private static void sleepSeconds(int n) throws InterruptedException {
		Thread.sleep(n * 1000);
	}

}
