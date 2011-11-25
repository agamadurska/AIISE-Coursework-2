package com.acmetelecom;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestHtmlPrinter {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPrintHeading() {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		HtmlPrinter htmlPrinter = new HtmlPrinter(new PrintStream(byteOutputStream));
		htmlPrinter.printHeading("Mondialu", "8989", "50$");
		System.out.println();
		assertEquals(byteOutputStream.toString(),
				"<html>\n<head></head>\n<body>\n<h1>\nAcme Telecom\n</h1>\n" +
				"<h2>Mondialu/8989 - Price Plan: 50$</h2>\n" +
				"<table border=\"1\">\n<tr><th width=\"160\">Time</th>" +
				"<th width=\"160\">Number</th><th width=\"160\">Duration</th>" +
				"<th width=\"160\">Cost</th></tr>\n");
	}

	@Test
	public void testPrintItem() {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		HtmlPrinter htmlPrinter = new HtmlPrinter(new PrintStream(byteOutputStream));
		//htmlPrinter.printItem(time, callee, duration, cost);
		System.out.println(byteOutputStream.toString());
	}

	@Test
	public void testPrintTotal() {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		HtmlPrinter htmlPrinter = new HtmlPrinter(new PrintStream(byteOutputStream));
		System.out.println(byteOutputStream.toString());
	}

}
