package com.acmetelecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.entity.Phone;
import com.acmetelecom.entity.PhoneEntity;

public class TestCustomerBill {

	private Customer customer;
	private PhoneEntity phone1;
	private PhoneEntity phone2;
	
	@Before
	public void setUp() throws Exception {
		customer = new Customer("", "42", "");
		phone1 = new Phone("42");
		phone2 = new Phone("47");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCustomerCalls() {
		long start1 = new DateTime(2011, 11, 10, 17, 10, 0).toDate().getTime();
		long end1 = new DateTime(2011, 11, 10, 17, 13, 0).toDate().getTime();
		CallEvent event1 = new CallStart(phone1, phone2,start1);
		CallEvent event2 = new CallEnd(phone1, phone2, end1);
		List<CallEvent> customerEvents = Arrays.asList(event1, event2);
		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), customerEvents);
		assertEquals(Arrays.asList(new Call(event1, event2)),
				customerBill.getCustomerCalls());
	}

	@Test
	public void testComputeCost() {
		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), new ArrayList<CallEvent>());
		long timeStart1 = new DateTime(2011, 11, 10, 18, 50, 0).toDate().getTime();
		long timeStart2 = new DateTime(2011, 11, 10, 06, 50, 0).toDate().getTime();
		long timeEnd1 = new DateTime(2011, 11, 10, 18, 51, 0).toDate().getTime();
		long timeEnd2 = new DateTime(2011, 11, 10, 19, 1, 0).toDate().getTime();
		long timeEnd3 = new DateTime(2011, 11, 10, 07, 1, 0).toDate().getTime();
		CallEvent start1 = new CallStart(phone1, phone2, timeStart1);
		CallEvent end1 = new CallEnd(phone1, phone2, timeEnd1);
		CallEvent end2 = new CallEnd(phone1, phone2, timeEnd2);
		CallEvent start2 = new CallStart(phone1, phone2, timeStart2);
		CallEvent end3 = new CallEnd(phone1, phone2, timeEnd3);
		Call call1 = new Call(start1, end1);
		Call call2 = new Call(start1, end2);
		Call call3 = new Call(start2, end3);
		assertTrue(new BigDecimal(30.0).compareTo(
				customerBill.computeCost(call1)) == 0);
		assertTrue(new BigDecimal(312.0).compareTo(
				customerBill.computeCost(call2)
						.setScale(2, BigDecimal.ROUND_HALF_DOWN)) == 0);
		assertTrue(new BigDecimal(150.0).compareTo(
				customerBill.computeCost(call3)
						.setScale(2, BigDecimal.ROUND_HALF_DOWN)) == 0);
	}

	@Test
	public void testCharge() {
		Phone phone3 = new Phone("2323");

		long timeStart1 = new DateTime(2011, 11, 10, 18, 50, 0).toDate().getTime();
		long timeEnd1 = new DateTime(2011, 11, 10, 18, 51, 0).toDate().getTime();
		long timeStart2 = new DateTime(2011, 11, 10, 18, 57, 0).toDate().getTime();
		long timeEnd2 = new DateTime(2011, 11, 10, 19, 1, 0).toDate().getTime();
		long timeStart3 = new DateTime(2011, 11, 10, 19, 50, 0).toDate().getTime();
		long timeEnd3 = new DateTime(2011, 11, 10, 19, 51, 0).toDate().getTime();
		
		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart1),
				new CallEnd(phone1, phone2, timeEnd1),
				new CallStart(phone1, phone3, timeStart2),
				new CallEnd(phone1, phone3, timeEnd2),
				new CallStart(phone1, phone2, timeStart3),
				new CallEnd(phone1, phone2, timeEnd3));
		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);
		assertTrue(new BigDecimal(144).compareTo(customerBill.charge()) == 0);
	}
	
	@Test
	public void testEqualsAndHashCode() {
		Customer customer = new Customer("", "42", "");
		TariffLibrary tariffLibrary = EasyMock.createMock(TariffLibrary.class);
		EasyMock.expect(tariffLibrary.tarriffFor(customer))
				.andReturn(Tariff.Standard).anyTimes();
		EasyMock.replay(tariffLibrary);
		CustomerBill customer1 = new CustomerBill(customer, tariffLibrary,
				new ArrayList<CallEvent>());
		CustomerBill customer2 = new CustomerBill(customer, tariffLibrary,
				new ArrayList<CallEvent>());
		assertEquals(customer1, customer2);
		assertEquals(customer1.hashCode(), customer2.hashCode());
	}

	private class MockTariffLibrary implements TariffLibrary {

		@Override
		public Tariff tarriffFor(Customer customer) {
			return Tariff.Standard;
		}
		
	}
	
}
