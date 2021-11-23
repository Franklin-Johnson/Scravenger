package web;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class webDisplayTest {

	@Before
	public void setUp(){
		
	}
	
	@Test(expected=ArithmeticException.class)
	public void divtest(){
		webDisplay w = new webDisplay();
		boolean actual = true;
		boolean expected = true;
		assertEquals(actual, expected);
	}
	
	@After
	public void teardown() {
		
	}

}
