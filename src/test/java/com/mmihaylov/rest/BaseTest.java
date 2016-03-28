package com.mmihaylov.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;

public class BaseTest {

    protected Injector injector = Guice.createInjector(new Configuration());

    @Before
    public void setUp() {
        injector.injectMembers(this);
    }
}
