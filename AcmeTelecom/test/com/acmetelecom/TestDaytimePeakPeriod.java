package com.acmetelecom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDaytimePeakPeriod {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOffPeakTime() {
		DaytimePeakPeriod daytimePeakPeriod = new DaytimePeakPeriod();
        int hour = (int) daytimePeakPeriod.getPeakEndHours();

		DateTime offpeakTime = new DateTime(2012, 4, 3, hour, 23, 17);

		assertTrue(daytimePeakPeriod.offPeak(offpeakTime.toDate()));
	}

    @Test
	public void testPeakTime() {
		DaytimePeakPeriod daytimePeakPeriod = new DaytimePeakPeriod();
        int hour = (int) daytimePeakPeriod.getPeakStartHours();

		DateTime peakTime = new DateTime(2012, 6, 6, hour, 16, 6);

		assertFalse(daytimePeakPeriod.offPeak(peakTime.toDate()));
	}

}
