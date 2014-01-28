package com.rowe.trivia;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class ObjectifyTestCaseSupport {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public final void setupObjectify(){
		helper.setUp();
		CoreConfiguration.registerObjectify();
	}
	
	@After
	public final void teardownObjectify(){
		helper.tearDown();
	}
}
