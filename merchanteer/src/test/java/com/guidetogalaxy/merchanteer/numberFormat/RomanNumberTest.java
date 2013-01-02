package com.guidetogalaxy.merchanteer.numberFormat;

import static org.junit.Assert.*;

import org.junit.Test;

public class RomanNumberTest {

	@Test
	public void testFromStringSucessful() {
		testFromStringIndividualSuccessful("I");
		testFromStringIndividualSuccessful("II");
		testFromStringIndividualSuccessful("III");
		testFromStringIndividualSuccessful("IV");
		testFromStringIndividualSuccessful("V");
		testFromStringIndividualSuccessful("VI");
		testFromStringIndividualSuccessful("VII");
		testFromStringIndividualSuccessful("VIII");
		testFromStringIndividualSuccessful("IX");
		testFromStringIndividualSuccessful("X");
		testFromStringIndividualSuccessful("DXXXI");
		testFromStringIndividualSuccessful("DCCCXCIX");
		testFromStringIndividualSuccessful("MCCCXXVIII");
		testFromStringIndividualSuccessful("MMMCMXCIX");
		testFromStringIndividualSuccessful("MMMDCXXXIX");
	}

	@Test
	public void testFromStringError() {
		testFromStringIndividualError("");
		testFromStringIndividualError("asdfasd");
		testFromStringIndividualError("2134123");
		testFromStringIndividualError("Ib");
		testFromStringIndividualError("I1");
		testFromStringIndividualError("1I");
		testFromStringIndividualError("aI");
		testFromStringIndividualError("VV");
		testFromStringIndividualError("IVX");
		testFromStringIndividualError("IIII");
		testFromStringIndividualError("IL");
		testFromStringIndividualError("LIL");
		testFromStringIndividualError("LDL");
		testFromStringIndividualError("IIII");
		testFromStringIndividualError("MMMM");
	}
	
	@Test
	public void testToString() throws MalformedNumberException {
		RomanNumber sample1 = RomanNumber.fromString("I");
		RomanNumber sample2 = RomanNumber.fromString("IV");
		RomanNumber sample3 = RomanNumber.fromString("DXXXI");
		RomanNumber sample4 = RomanNumber.fromString("MCCCXXVIII");
		RomanNumber sample5 = RomanNumber.fromString("MMMDCXXXIX");
		
		assertEquals("I", sample1.toString());
		assertEquals("IV", sample2.toString());
		assertEquals("DXXXI", sample3.toString());
		assertEquals("MCCCXXVIII", sample4.toString());
		assertEquals("MMMDCXXXIX", sample5.toString());
	}

	@Test
	public void testGetIntValue() throws MalformedNumberException {
		RomanNumber sample1 = RomanNumber.fromString("I");
		RomanNumber sample2 = RomanNumber.fromString("II");
		RomanNumber sample3 = RomanNumber.fromString("III");
		RomanNumber sample4 = RomanNumber.fromString("IV");
		RomanNumber sample5 = RomanNumber.fromString("V");
		RomanNumber sample6 = RomanNumber.fromString("VI");
		RomanNumber sample7 = RomanNumber.fromString("VII");
		RomanNumber sample8 = RomanNumber.fromString("VIII");
		RomanNumber sample9 = RomanNumber.fromString("IX");
		RomanNumber sample10 = RomanNumber.fromString("X");
		RomanNumber sample11 = RomanNumber.fromString("DXXXI");
		RomanNumber sample12 = RomanNumber.fromString("MCCCXXVIII");
		RomanNumber sample13 = RomanNumber.fromString("MMMDCXXXIX");
		
		String arabicConversionMessage = "The conversion from roman to arabic did not provide the correct value.";
		assertEquals(arabicConversionMessage, 1, sample1.getIntValue());
		assertEquals(arabicConversionMessage, 2, sample2.getIntValue());
		assertEquals(arabicConversionMessage, 3, sample3.getIntValue());
		assertEquals(arabicConversionMessage, 4, sample4.getIntValue());
		assertEquals(arabicConversionMessage, 5, sample5.getIntValue());
		assertEquals(arabicConversionMessage, 6, sample6.getIntValue());
		assertEquals(arabicConversionMessage, 7, sample7.getIntValue());
		assertEquals(arabicConversionMessage, 8, sample8.getIntValue());
		assertEquals(arabicConversionMessage, 9, sample9.getIntValue());
		assertEquals(arabicConversionMessage, 10, sample10.getIntValue());
		assertEquals(arabicConversionMessage, 531, sample11.getIntValue());
		assertEquals(arabicConversionMessage, 1328, sample12.getIntValue());
		assertEquals(arabicConversionMessage, 3639, sample13.getIntValue());
	}
	
	
	private void testFromStringIndividualSuccessful(String roman) {
		String wellformedNumberMessage = "It wasn't possible to create a number from a string, but it should have been.";
		try {
			RomanNumber.fromString(roman);
		} catch (MalformedNumberException ex) {
			fail(wellformedNumberMessage);
		}
		
	}

	private void testFromStringIndividualError(String roman) {
		String malformedNumberMessage = "It was possible to create a number from a string, but it should NOT have been.";
		try {
			RomanNumber.fromString(roman);
			fail(malformedNumberMessage);
		} catch (MalformedNumberException ex) {
			// do nothing, as this is expected
		}
		
	}

}
