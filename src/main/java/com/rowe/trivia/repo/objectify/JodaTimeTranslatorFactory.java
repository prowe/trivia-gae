package com.rowe.trivia.repo.objectify;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.ReadableInstant;
import org.joda.time.base.AbstractInstant;

import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.Property;
import com.googlecode.objectify.impl.TypeUtils;
import com.googlecode.objectify.impl.translate.CreateContext;
import com.googlecode.objectify.impl.translate.LoadContext;
import com.googlecode.objectify.impl.translate.SaveContext;
import com.googlecode.objectify.impl.translate.ValueTranslator;
import com.googlecode.objectify.impl.translate.ValueTranslatorFactory;
import com.googlecode.objectify.repackaged.gentyref.GenericTypeReflector;

public class JodaTimeTranslatorFactory extends
		ValueTranslatorFactory<ReadableInstant, Date> {
	
	public JodaTimeTranslatorFactory() {
		super(ReadableInstant.class);
	}

	@Override
	protected ValueTranslator<ReadableInstant, Date> createSafe(Path path,
			Property property, Type type, CreateContext ctx) {
		final Class<?> clazz = GenericTypeReflector.erase(type);

		return new ValueTranslator<ReadableInstant, Date>(path, Date.class) {
			@Override
			protected ReadableInstant loadValue(Date value, LoadContext ctx) {
				// All the Joda instants have a constructor that will take a
				// Date
				Constructor<?> ctor = TypeUtils.getConstructor(clazz,
						Object.class);
				ReadableInstant instance = (ReadableInstant) TypeUtils
						.newInstance(ctor, value);

				// If possible, ensure that the return ReadableInstant is in UTC
				if (AbstractInstant.class.isAssignableFrom(clazz)) {
					instance = ((AbstractInstant) instance); //.toDateTime(DateTimeZone.UTC);
				}
				return instance;
			}

			@Override
			protected Date saveValue(ReadableInstant value, SaveContext ctx) {
				return value.toInstant().toDate();
			}
		};
	}
}
