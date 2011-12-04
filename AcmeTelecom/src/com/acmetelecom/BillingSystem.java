package com.acmetelecom;

import java.util.List;

/**
 * Interface for the billing system.
 */
public interface BillingSystem {

	List<CustomerBill> createCustomersBill();
	
}
