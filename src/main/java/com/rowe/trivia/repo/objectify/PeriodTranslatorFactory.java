package com.rowe.trivia.repo.objectify;

import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.Property;
import com.googlecode.objectify.impl.translate.CreateContext;
import com.googlecode.objectify.impl.translate.LoadContext;
import com.googlecode.objectify.impl.translate.SaveContext;
import com.googlecode.objectify.impl.translate.SkipException;
import com.googlecode.objectify.impl.translate.ValueTranslator;
import com.googlecode.objectify.impl.translate.ValueTranslatorFactory;

public class PeriodTranslatorFactory extends
	ValueTranslatorFactory<Period, String>{
	private PeriodFormatter periodFormatter = ISOPeriodFormat.standard();

	public PeriodTranslatorFactory() {
		super(Period.class);
	}
	
	@Override
	protected ValueTranslator<Period, String> createSafe(Path path, Property property, Type type, CreateContext ctx) {
		return new ValueTranslator<Period, String>(path, String.class) {
			@Override
			protected Period loadValue(String value, LoadContext ctx) throws SkipException {
				if(value == null){
					return null;
				}
				return periodFormatter.parsePeriod(value);
			}
			@Override
			protected String saveValue(Period value, SaveContext ctx)
					throws SkipException {
				if(value == null){
					return null;
				}
				return periodFormatter.print(value);
			}
		};
	}
	
	
}
