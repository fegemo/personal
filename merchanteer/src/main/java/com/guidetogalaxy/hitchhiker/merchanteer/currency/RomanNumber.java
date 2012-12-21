package com.guidetogalaxy.hitchhiker.merchanteer.currency;

import java.util.LinkedList;
import java.util.List;

public final class RomanNumber {
	private final List<RomanSymbol> representation;
	private static final String IDENTITY = "";
	public static final int MAX_ROMAN_INT_VALUE = 3999;
	public static final int MIN_ROMAN_INT_VALUE = 0;
	
	
	public RomanNumber(String candidateRepresentation) {
		this.representation = buildRomanNumberRepresentation(candidateRepresentation);
	}
	
	private List<RomanSymbol> buildRomanNumberRepresentation(String candidateRepresentation) {
		List<RomanSymbol> rep = new LinkedList<RomanSymbol>();
		
		
		return rep;
	}
	
	public static RomanNumber fromArabicFormat(String inArabic) throws CurrencyConversionException {
		// pre-conditions
		try {
			int intValue = Integer.parseInt(inArabic);
			if (intValue < MIN_ROMAN_INT_VALUE) {
				throw new CurrencyConversionException("The provided arabic number candidate was smaller than what can be represented by Roman numbers.");
			} else if (intValue > MAX_ROMAN_INT_VALUE) {
				throw new CurrencyConversionException("The provided arabic number candidate was bigger than what can be represented by Roman numbers.");
			}
		} catch (NumberFormatException ex) {
			throw new MalformedConversionException("The provided arabic number candidate was not a recognized number.");
		}
		
		RomanNumber roman = new RomanNumber(IDENTITY);
		char[] inArabicChars = inArabic.toCharArray();
		char c;
		int digitValue;
		for (int index = 0; index < inArabicChars.length; index++) {
			c = inArabicChars[index];
			int digit = Character.getNumericValue(c);
			if (digit == -1) {
				throw new CurrencyConversionException("At least one digit of the arabic number was not a number.");
			}
			digitValue = digit * 10 ^ (inArabicChars.length - index - 1);
			
		}
		return roman;
	}
	
	@Override
	public String toString() {
		return representation.toUpperCase();
	}
	
	
	private enum RomanSymbol {
		I (1, 3),
		V (5, 1),
		X (10, 3),
		L (50, 1),
		C (100, 3),
		D (500, 1),
		M (1000, 3);
		
		private final int intValue;
		private final int maxRepeat;
		
		RomanSymbol(int intValue, int maxRepeat) {
			this.intValue = intValue;
			this.maxRepeat = maxRepeat;
		}
	}
}
