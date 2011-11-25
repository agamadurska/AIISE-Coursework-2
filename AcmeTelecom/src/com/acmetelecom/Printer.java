package com.acmetelecom;

public interface Printer {

	void printHeading(String name, String phoneNumber, String pricePlan);

	void printItem(String time, PhoneEntity callee, String duration, String cost);

	void printTotal(String total);

}
