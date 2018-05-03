package com.softwaresandbox.makeastats;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoRule;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.junit.MockitoJUnit.rule;

public class AbstractUnitTest {

    @Rule
    public MockitoRule mockitoRule = rule();
    @Rule
    public ExpectedException expectedException = none();

}
