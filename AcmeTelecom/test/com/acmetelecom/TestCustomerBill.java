package com.acmetelecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private TariffLibrary tariffLibrary;
    private Tariff tariff;

    private DaytimePeakPeriod daytimePeakPeriod;
    private int peakStart;
    private int peakEnd;

    @Before
	public void setUp() throws Exception {
		customer = new Customer("", "42", "");
		phone1 = new Phone("42");
		phone2 = new Phone("47");

        tariffLibrary = new MockTariffLibrary();
        tariff = tariffLibrary.tarriffFor(customer);

        daytimePeakPeriod = new DaytimePeakPeriod();

        peakStart = (int) daytimePeakPeriod.getPeakStartHours();
        peakEnd = (int) daytimePeakPeriod.getPeakEndHours();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCustomerCalls() {
		long start1 = new DateTime(2011, 11, 10, 17, 10, 0).toDate().getTime();
		long end1   = new DateTime(2011, 11, 10, 17, 13, 0).toDate().getTime();

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

		long timeStart1 = new DateTime(2011, 11, 10, peakEnd   - 1, 50, 0).toDate().getTime();
		long timeStart2 = new DateTime(2011, 11, 10, peakStart - 1, 50, 0).toDate().getTime();

		long timeEnd1 = new DateTime(2011, 11, 10, peakEnd - 1, 51, 0).toDate().getTime();
		long timeEnd2 = new DateTime(2011, 11, 10, peakEnd     , 1, 0).toDate().getTime();
		long timeEnd3 = new DateTime(2011, 11, 10,  peakStart, 1, 0).toDate().getTime();

		CallEvent start1 = new CallStart(phone1, phone2, timeStart1);
		CallEvent end1 = new CallEnd(phone1, phone2, timeEnd1);
		CallEvent end2 = new CallEnd(phone1, phone2, timeEnd2);
		CallEvent start2 = new CallStart(phone1, phone2, timeStart2);
		CallEvent end3 = new CallEnd(phone1, phone2, timeEnd3);

		Call call1 = new Call(start1, end1);
		Call call2 = new Call(start1, end2);
        Call call3 = new Call(start2, end3);

        BigDecimal expectedCost = calculateExpectedCharge(60, 0);
        assertEquals(expectedCost.setScale(1), customerBill.computeCost(call1));

        expectedCost = calculateExpectedCharge(10 * 60, 60);
        assertEquals(expectedCost, customerBill.computeCost(call2));

        expectedCost = calculateExpectedCharge(60, 10 * 60);
        assertEquals(expectedCost, customerBill.computeCost(call3));

	}

    @Test
	public void testChargePeak() {

		long timeStart = new DateTime(2011, 11, 10, peakStart, 50, 0).toDate().getTime();
		long timeEnd   = new DateTime(2011, 11, 10, peakStart, 51, 0).toDate().getTime();

		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart),
				new CallEnd(phone1, phone2, timeEnd));

		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);

        BigDecimal expectedCharge = new BigDecimal(60)
					.multiply(tariff.peakRate());
        expectedCharge = expectedCharge.setScale(0, RoundingMode.HALF_UP);

        assertEquals(expectedCharge, customerBill.charge());
	}

    @Test
	public void testChargeOffPeak() {

		long timeStart = new DateTime(2011, 11, 10, peakEnd, 50, 0).toDate().getTime();
		long timeEnd   = new DateTime(2011, 11, 10, peakEnd, 51, 0).toDate().getTime();

		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart),
				new CallEnd(phone1, phone2, timeEnd));

		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);

        BigDecimal expectedCharge = new BigDecimal(60)
					.multiply(tariff.offPeakRate());
        expectedCharge = expectedCharge.setScale(0, RoundingMode.HALF_UP);

        assertEquals(expectedCharge, customerBill.charge());
	}

    @Test
	public void testChargePeakToOffPeak() {

		long timeStart = new DateTime(2011, 11, 10, peakEnd - 1, 59, 0).toDate().getTime();
		long timeEnd   = new DateTime(2011, 11, 10, peakEnd, 1, 0).toDate().getTime();

		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart),
				new CallEnd(phone1, phone2, timeEnd));

		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);

        BigDecimal expectedCharge = new BigDecimal(60)
					.multiply(tariff.offPeakRate());
        expectedCharge = expectedCharge.add(new BigDecimal(60)
					.multiply(tariff.peakRate()));

        expectedCharge = expectedCharge.setScale(0, RoundingMode.HALF_UP);

        assertEquals(expectedCharge, customerBill.charge());
	}

    @Test
	public void testChargeOffPeakToPeak() {

		long timeStart = new DateTime(2011, 11, 10, peakStart - 1, 59, 0).toDate().getTime();
		long timeEnd = new DateTime(2011, 11, 10, peakStart, 2, 0).toDate().getTime();

		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart),
				new CallEnd(phone1, phone2, timeEnd));

		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);

        BigDecimal expectedCharge = new BigDecimal(60)
					.multiply(tariff.offPeakRate());
        expectedCharge = expectedCharge.add(new BigDecimal(2 * 60)
					.multiply(tariff.peakRate()));

        expectedCharge = expectedCharge.setScale(0, RoundingMode.HALF_UP);

        assertEquals(expectedCharge, customerBill.charge());
	}

@Test
	public void testChargeMultipleCalls() {
		Phone phone3 = new Phone("2323");

        // Peak-time call
		long timeStart1 = new DateTime(2011, 11, 10, peakStart, 50, 0).toDate().getTime();
		long timeEnd1   = new DateTime(2011, 11, 10, peakStart, 51, 0).toDate().getTime();

        // Cross-time call
		long timeStart2 = new DateTime(2011, 11, 10, peakEnd - 1, 57, 0).toDate().getTime();
		long timeEnd2   = new DateTime(2011, 11, 10, peakEnd    ,  1, 0).toDate().getTime();

        // Off-Peak call
		long timeStart3 = new DateTime(2011, 11, 10, peakEnd + 1, 50, 0).toDate().getTime();
		long timeEnd3   = new DateTime(2011, 11, 10, peakEnd + 1, 51, 0).toDate().getTime();

		List<CallEvent> callEvents = Arrays.asList(
				new CallStart(phone1, phone2, timeStart1),
				new CallEnd(phone1, phone2, timeEnd1),
				new CallStart(phone1, phone3, timeStart2),
				new CallEnd(phone1, phone3, timeEnd2),
				new CallStart(phone1, phone2, timeStart3),
				new CallEnd(phone1, phone2, timeEnd3));

		CustomerBill customerBill = new CustomerBill(customer,
				new MockTariffLibrary(), callEvents);

        BigDecimal expectedCharge = calculateExpectedCharge(4 * 60, 2 * 60);

        assertEquals(expectedCharge.setScale(0, RoundingMode.HALF_UP), customerBill.charge());
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

    private BigDecimal calculateExpectedCharge(int peakSeconds, int offPeakSeconds) {
        BigDecimal expectedCharge = new BigDecimal(offPeakSeconds)
					.multiply(tariff.offPeakRate());
        expectedCharge = expectedCharge.add(new BigDecimal(peakSeconds)
					.multiply(tariff.peakRate()));

        return expectedCharge;
    }

}
