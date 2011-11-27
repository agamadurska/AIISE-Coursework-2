package com.acmetelecom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.call.SimpleCallsLogger;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.entity.Phone;

public class TestBillingSystem extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCustomerBills() {
		Customer customer1 = new Customer("", "42", "");
		Customer customer2 = new Customer("", "47", "");
		
		CustomerDatabase customerDatabase = 
				EasyMock.createMock(CustomerDatabase.class);
		TariffLibrary tariffLibrary =
				EasyMock.createMock(TariffLibrary.class);
		EasyMock.expect(tariffLibrary.tarriffFor(customer1))
				.andReturn(Tariff.Standard).anyTimes();
		EasyMock.expect(tariffLibrary.tarriffFor(customer2))
				.andReturn(Tariff.Standard).anyTimes();
		EasyMock.expect(customerDatabase.getCustomers())
				.andReturn(Arrays.asList(customer1, customer2)).anyTimes();
		EasyMock.replay(tariffLibrary);
		EasyMock.replay(customerDatabase);
		
		CallEvent callStart = new CallStart(new Phone("47"), new Phone("42"), 1000);
		CallEvent callEnd = new CallEnd(new Phone("47"), new Phone("42"), 5000);
		CallsLogger callsLogger = new SimpleCallsLogger();
		callsLogger.addCallEvent(callStart);
		callsLogger.addCallEvent(callEnd);
		CompleteBillingSystem completeBillingSystem = new CompleteBillingSystem(
				callsLogger, customerDatabase, tariffLibrary);
		List<CustomerBill> customersBill = Arrays.asList(
				new CustomerBill(customer1, tariffLibrary, new ArrayList<CallEvent>()),
				new CustomerBill(customer2, tariffLibrary,
						Arrays.asList(callStart, callEnd)));
		assertEquals(customersBill, completeBillingSystem.createCustomersBill());
	}

}
