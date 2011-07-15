package edu.luc.clearing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CheckParserTest {
	private CheckParser parser;
	
	@Before
	public void setup(){
		parser = new CheckParser();
	}

	@Test
	public void shouldIgnoreCase() throws Exception {
		assertEquals(300, parser.parseAmount("Three").intValue());
	}

	@Test
	public void shouldHandleZeroAmount() throws Exception {
		assertEquals(0, parser.parseAmount("zero").intValue());
	}

	@Test
	public void shouldIgnoreSpace() throws Exception {
		assertEquals(400, parser.parseAmount("Four ").intValue());
	}
	
	@Test
	public void shouldMatchWithDollars() throws Exception {
		assertEquals(100, parser.parseAmount("One dollar").intValue());
		assertEquals(200, parser.parseAmount("two dollars").intValue());
		assertEquals(300, parser.parseAmount("three  dollars").intValue());
	}
	
	@Test
	public void shouldMatchWithTwoDigits() throws Exception{
		assertEquals(2100, parser.parseAmount("twenty one").intValue());
		assertEquals(7200, parser.parseAmount("seventy two").intValue());
	}
	
	@Test
	public void shouldMatchWithAndCentces()  throws Exception{
		
	}

}
