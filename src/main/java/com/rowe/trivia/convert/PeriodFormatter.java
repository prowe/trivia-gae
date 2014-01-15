package com.rowe.trivia.convert;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.format.Formatter;

public class PeriodFormatter implements Formatter<Period>{
	private org.joda.time.format.PeriodFormatter format = null;
	
	public PeriodFormatter() {
		format = new PeriodFormatterBuilder()
			.appendDays()
			.appendSuffix(" day", " days")
			.appendPrefix(" ")
			.appendHours()
			.appendSuffix(" hour", " hours")
			.appendPrefix(" ")
			.appendMinutes()
			.appendSuffix(" minute", " minutes")
			.toFormatter();
	}

	@Override
	public String print(Period object, Locale locale) {
		return format.print(object);
	}

	@Override
	public Period parse(String text, Locale locale) throws ParseException {
		return format.parsePeriod(text);
	}

}
