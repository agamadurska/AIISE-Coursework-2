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
	public void testOffPeak() {
		DaytimePeakPeriod daytimePeakPeriod = new DaytimePeakPeriod();
		DateTime peakTime = new DateTime(2012, 06, 06, 18, 06, 06);
		DateTime offpeakTime = new DateTime(2012, 04, 03, 03, 23, 17);
		assertTrue(daytimePeakPeriod.offPeak(offpeakTime.toDate()));
		assertFalse(daytimePeakPeriod.offPeak(peakTime.toDate()));
	}

}
