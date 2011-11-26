package com.acmetelecom;

import com.acmetelecom.call.SimpleCallsLogger;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.entity.Phone;

public class Runner {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Running...");
		Phone phone1 = new Phone("447722113434");
		Phone phone2 = new Phone("447766814143");
		Phone phone3 = new Phone("447777765432");
		Phone phone4 = new Phone("447711111111");
		SimpleCallsLogger callsLogger = new SimpleCallsLogger();
		callsLogger.callInitiated(phone1, phone2);
		sleepSeconds(2);
		callsLogger.callCompleted(phone1, phone2);
		callsLogger.callInitiated(phone1, phone4);
		sleepSeconds(3);
		callsLogger.callCompleted(phone1, phone4);
		callsLogger.callInitiated(phone3, phone4);
		sleepSeconds(6);
		callsLogger.callCompleted(phone3, phone4);
		BillingSystem billingSystem = new BillingSystem(callsLogger,
				CentralCustomerDatabase.getInstance(),
				CentralTariffDatabase.getInstance());
		billingSystem.createCustomerBills();
	}
	
	private static void sleepSeconds(int n) throws InterruptedException {
		Thread.sleep(n * 1000);
	}

}
