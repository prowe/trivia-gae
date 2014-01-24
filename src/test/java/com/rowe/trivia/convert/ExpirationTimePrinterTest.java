package com.rowe.trivia.convert;

import static org.junit.Assert.*;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class ExpirationTimePrinterTest {
	
	private ExpirationTimePrinter printer;

	@Before
	public void setup(){
		printer = new ExpirationTimePrinter();
	}

	@Test
	public void testPrint_tomorrow() {
		DateTime dt = new LocalDate().toDateTime(new LocalTime(13, 45));
		assertEquals("tomorrow at 1:45 PM", printer.print(dt, Locale.getDefault()));
	}

}
