package com.rowe.trivia.convert;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.format.Printer;

public class ExpirationTimePrinter implements Printer<DateTime>{
	private final DateTimeFormatter tomorrowFormatter;
	private final DateTimeFormatter thisWeekFormatter;
	private DateTimeFormatter beyondWeekFormatter;

	public ExpirationTimePrinter() {
		tomorrowFormatter = new DateTimeFormatterBuilder()
			.appendLiteral("tomorrow at ")
			.appendHourOfHalfday(1)
			.appendLiteral(':')
			.appendMinuteOfHour(2)
			.appendLiteral(" ")
			.appendHalfdayOfDayText()
			.toFormatter();
		
		thisWeekFormatter = new DateTimeFormatterBuilder()
			.appendLiteral("on ")
			.appendDayOfWeekText()
			.appendLiteral(" at ")
			.appendHourOfHalfday(1)
			.appendLiteral(':')
			.appendMinuteOfHour(2)
			.appendLiteral(" ")
			.appendHalfdayOfDayText()
			.toFormatter();
		
		beyondWeekFormatter = new DateTimeFormatterBuilder()
			.appendMonthOfYear(1)
			.appendLiteral('/')
			.appendDayOfMonth(1)
			.appendLiteral('/')
			.appendYear(4, 4)
			.appendLiteral(" at ")
			.appendHourOfHalfday(1)
			.appendLiteral(':')
			.appendMinuteOfHour(2)
			.appendLiteral(" ")
			.appendHalfdayOfDayText()
			.toFormatter();
	}
	
	@Override
	public String print(DateTime object, Locale locale) {
		DateTime now = new DateTime();
		
		if(object.isBefore(now.toDateMidnight().plusDays(2))){
			return tomorrowFormatter.print(object);
		}
		if(object.isBefore(now.toDateMidnight().plusWeeks(1))){
			return thisWeekFormatter.print(object);
		}
		return beyondWeekFormatter.print(object);
	}

}
