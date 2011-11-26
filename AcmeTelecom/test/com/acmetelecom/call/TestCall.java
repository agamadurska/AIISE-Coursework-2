package com.acmetelecom.call;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.TestUtils;
import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.entity.Phone;

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
	
	@Test
	public void testEqualsAndHashCode() {
		CallEvent callStart = new CallStart(caller1, caller2, 1000);
		CallEvent callStart2 = new CallStart(caller1, caller2, 2000);
		CallEvent callEnd = new CallEnd(caller1, caller2, 10000);
		Call call1 = new Call(callStart, callEnd, new BigDecimal(0));
		Call call2 = new Call(callStart, callEnd);
		Call call3 = new Call(callStart2, callEnd);
		assertEquals(call1, call2);
		assertEquals(call1.hashCode(), call2.hashCode());
		assertFalse(call1.equals(call3));
		assertFalse(call1.hashCode() == call3.hashCode());
		assertFalse(call1.equals(new Object()));
	}
	
}
