package com.acmetelecom.output;

import com.acmetelecom.Phone;

public interface Printer {

	void printHeading(String name, String phoneNumber, String pricePlan);

	void printItem(String time, Phone callee, String duration, String cost);

	void printTotal(String total);

}
