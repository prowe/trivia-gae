package com.rowe.trivia.repo.objectify;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.Property;
import com.googlecode.objectify.impl.translate.CreateContext;
import com.googlecode.objectify.impl.translate.LoadContext;
import com.googlecode.objectify.impl.translate.SaveContext;
import com.googlecode.objectify.impl.translate.SkipException;
import com.googlecode.objectify.impl.translate.ValueTranslator;
import com.googlecode.objectify.impl.translate.ValueTranslatorFactory;


public class URLTranslatorFactory extends ValueTranslatorFactory<URL, String>{

	protected URLTranslatorFactory() {
		super(URL.class);
	}

	@Override
	protected ValueTranslator<URL, String> createSafe(Path path,
			Property property, Type type, CreateContext ctx) {
		return new ValueTranslator<URL, String>(path, null) {
			@Override
			protected URL loadValue(String value, LoadContext ctx)
					throws SkipException {
				try {
					return new URL(value);
				} catch (MalformedURLException e) {
					throw new IllegalArgumentException(e);
				}
			}
			
			@Override
			protected String saveValue(URL value, SaveContext ctx)
					throws SkipException {
				return value.toString();
			}
		};
	}
}
