package com.acmetelecom;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;

public class TestCall extends TestCase {

	public static final Phone caller1 = new Phone("08989999");
	public static final Phone caller2 = new Phone("42");
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDurationSeconds() {
		long startTimeStamp = 1000;
		long endTimeStamp = 10000;
		CallEvent callStart = new CallStart(caller1, caller2, startTimeStamp);
		CallEvent callEnd = new CallEnd(caller1, caller2, endTimeStamp);
		Call call = new Call(callStart, callEnd);
		assertEquals(call.durationSeconds(),
			TestUtils.millisecondsToSeconds(endTimeStamp - startTimeStamp));
	}
	
	@Test
	public void testDurationSeconds2() {
		long startTimeStamp = 1000;
		long endTimeStamp = 6546;
		CallEvent callStart = new CallStart(caller1, caller2, startTimeStamp);
		CallEvent callEnd = new CallEnd(caller1, caller2, endTimeStamp);
		Call call = new Call(callStart, callEnd);
		assertEquals(call.durationSeconds(),
			TestUtils.millisecondsToSeconds(endTimeStamp - startTimeStamp));
	}
	
}
