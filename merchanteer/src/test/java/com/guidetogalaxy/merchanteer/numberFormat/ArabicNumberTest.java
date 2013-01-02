package com.guidetogalaxy.merchanteer.numberFormat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.guidetogalaxy.merchanteer.numberFormat.ArabicNumber;

public class ArabicNumberTest {

	private ArabicNumber fromInt1;
	private ArabicNumber fromInt2;
	private ArabicNumber fromInt3;
	private ArabicNumber fromInt4;
	private ArabicNumber fromInt5;
	
	private ArabicNumber fromStr1;
	private ArabicNumber fromStr2;
	private ArabicNumber fromStr3;
	private ArabicNumber fromStr4;
	private ArabicNumber fromStr5;
	
	@Before
	public void init() throws Exception {
		fromInt1 = ArabicNumber.fromInt(-10000);
		fromInt2 = ArabicNumber.fromInt(-1);
		fromInt3 = ArabicNumber.fromInt(0);
		fromInt4 = ArabicNumber.fromInt(1);
		fromInt5 = ArabicNumber.fromInt(10000);
	}
	
	@Test
	public void testStrConstructorAndEqualsOverride() throws MalformedNumberException {
		fromStr1 = ArabicNumber.fromString("-10000");
		fromStr2 = ArabicNumber.fromString("-1");
		fromStr3 = ArabicNumber.fromString("0");
		fromStr4 = ArabicNumber.fromString("1");
		fromStr5 = ArabicNumber.fromString("10000");
		
		String constructorMismatchMessage = "The value generated from the string construction was different than the value generated from the integer.";
		assertEquals(constructorMismatchMessage, fromInt1.getIntValue(), fromStr1.getIntValue());
		assertEquals(constructorMismatchMessage, fromInt2.getIntValue(), fromStr2.getIntValue());
		assertEquals(constructorMismatchMessage, fromInt3.getIntValue(), fromStr3.getIntValue());
		assertEquals(constructorMismatchMessage, fromInt4.getIntValue(), fromStr4.getIntValue());
		assertEquals(constructorMismatchMessage, fromInt5.getIntValue(), fromStr5.getIntValue());

		String equalsMismatchMessage = "The equals method should have returned identical values for ArabicNumber instances for the same integer.";
		assertEquals(equalsMismatchMessage, fromInt1, fromStr1);
		assertEquals(equalsMismatchMessage, fromInt2, fromStr2);
		assertEquals(equalsMismatchMessage, fromInt3, fromStr3);
		assertEquals(equalsMismatchMessage, fromInt4, fromStr4);
		assertEquals(equalsMismatchMessage, fromInt5, fromStr5);
	}

	@Test
	public void testGetIntValue() {
		String transformationMessage = "Wrong transformation to int!"; 
		assertEquals(transformationMessage, -10000, fromInt1.getIntValue());
		assertEquals(transformationMessage, -1, fromInt2.getIntValue());
		assertEquals(transformationMessage, 0, fromInt3.getIntValue());
		assertEquals(transformationMessage, 1, fromInt4.getIntValue());
		assertEquals(transformationMessage, 10000, fromInt5.getIntValue());
	}

}
