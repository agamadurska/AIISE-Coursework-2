package com.acmetelecom;

public class Runner {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Running...");
		PhoneEntity phone1 = new PhoneEntity("447722113434");
		PhoneEntity phone2 = new PhoneEntity("447766814143");
		PhoneEntity phone3 = new PhoneEntity("447777765432");
		PhoneEntity phone4 = new PhoneEntity("447711111111");
		BillingSystem billingSystem = new BillingSystem();
		billingSystem.callInitiated(phone1, phone2);
		sleepSeconds(2);
		billingSystem.callCompleted(phone1, phone2);
		billingSystem.callInitiated(phone1, phone4);
		sleepSeconds(3);
		billingSystem.callCompleted(phone1, phone4);
		billingSystem.callInitiated(phone3, phone4);
		sleepSeconds(6);
		billingSystem.callCompleted(phone3, phone4);
		billingSystem.createCustomerBills();
	}
	
	private static void sleepSeconds(int n) throws InterruptedException {
		Thread.sleep(n * 1000);
	}

}
