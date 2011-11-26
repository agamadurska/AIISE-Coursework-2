package com.acmetelecom;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.call.SimpleCallsLogger;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.entity.Phone;
import com.acmetelecom.entity.PhoneEntity;

public class TestSimpleCallsLogger {

	private Customer customer;
	private CallsLogger callsLogger;
	private PhoneEntity caller;
	private PhoneEntity callee;
	
	@Before
	public void setUp() throws Exception {
		callsLogger = new SimpleCallsLogger();
		customer = new Customer("", "42", "");
		caller = new Phone("42");
		callee = new Phone("47");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testgetCustomerCallEvents() {
		customer = new Customer("", "42", "");
		List<CallEvent> expectedCallEvents = Arrays.asList(
				new CallStart(caller, callee), new CallEnd(caller, callee));
		callsLogger.callInitiated(caller, callee);
		callsLogger.callCompleted(caller, callee);
		callsLogger.callInitiated(callee, caller);
		assertEquals(expectedCallEvents,
				callsLogger.getCustomerCallEvents(customer));
	}

	@Test
	public void testClear() {
		callsLogger.callInitiated(caller, callee);
		assertEquals(1, callsLogger.getCustomerCallEvents(customer).size());
		callsLogger.clear();
		assertEquals(0, callsLogger.getCustomerCallEvents(customer).size());
	}
	
}
