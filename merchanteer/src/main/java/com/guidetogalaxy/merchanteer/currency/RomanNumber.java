package com.guidetogalaxy.merchanteer.currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.guidetogalaxy.merchanteer.numberFormat.MalformedNumberException;
import com.guidetogalaxy.merchanteer.numberFormat.NumberConversionException;
import com.guidetogalaxy.merchanteer.numberFormat.NumberFormat;

public final class RomanNumber implements NumberFormat {
	private final List<RomanSymbol> representation;
	private static final String IDENTITY = "";
	public static final int MAX_ROMAN_INT_VALUE = 3999;
	public static final int MIN_ROMAN_INT_VALUE = 0;
	
	
	public RomanNumber(String candidateRepresentation) {
		this.representation = buildRomanNumberRepresentation(candidateRepresentation);
	}
	
	private List<RomanSymbol> buildRomanNumberRepresentation(String candidateRepresentation) {
		List<RomanSymbol> representation = new ArrayList<RomanSymbol>();
		
		
		return representation;
	}
	
	public static RomanNumber fromArabicFormat(String inArabic) throws NumberConversionException, MalformedNumberException {
		// pre-conditions
		try {
			int intValue = Integer.parseInt(inArabic);
			if (intValue < MIN_ROMAN_INT_VALUE) {
				throw new NumberConversionException("The provided arabic number candidate was smaller than what can be represented by Roman numbers.");
			} else if (intValue > MAX_ROMAN_INT_VALUE) {
				throw new NumberConversionException("The provided arabic number candidate was bigger than what can be represented by Roman numbers.");
			}
		} catch (NumberFormatException ex) {
			throw new MalformedNumberException("The provided arabic number candidate was not a recognized number.");
		}
		
		RomanNumber roman = new RomanNumber(IDENTITY);
		
		char[] inArabicChars = inArabic.toCharArray();
		char c;
		for (int index = 0; index < inArabicChars.length; index++) {
			c = inArabicChars[index];
			int digit = Character.getNumericValue(c);
			if (digit == -1) {
				throw new NumberConversionException("At least one digit of the arabic number was not a number.");
			}
			
			roman.representation.addAll(convertArabicDigitIntoRoman(digit, index));
		}
		return roman;
	}
	
	private static List<RomanSymbol> convertArabicDigitIntoRoman(int digit, int magnitude) {
		List<RomanSymbol> roman = new ArrayList<RomanSymbol>();
		
		// Finds the two symbols from this magnitude
		// Finds a third symbol (adjacent) that may be used for the last digit (the next symbol)
		RomanSymbol[] symbols = RomanSymbol.getByMagnitude(magnitude);
		RomanSymbol repeater = symbols[0];
		RomanSymbol guyInTheMiddle = symbols[1];
		RomanSymbol fromNextMagnitude = RomanSymbol.getNext(guyInTheMiddle);
		
		//
		switch (digit) {
		case 3:
			roman.add(repeater);
		case 2:
			roman.add(repeater);
		case 1:
			roman.add(repeater);
			break;
		case 4:
			roman.add(repeater);
		case 5:
			roman.add(guyInTheMiddle);
			break;
		case 6:
			roman.add(guyInTheMiddle);
			roman.add(repeater);
			break;
		case 7:
			roman.add(guyInTheMiddle);
			roman.add(repeater);
			roman.add(repeater);
			break;
		case 8:
			roman.add(guyInTheMiddle);
			roman.add(repeater);
			roman.add(repeater);
			roman.add(repeater);
			break;
		case 9:
			roman.add(repeater);
			roman.add(fromNextMagnitude);
			break;
		}
		
		return roman;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (RomanSymbol s : representation) {
			b.append(s.toString().toUpperCase());
		}
		return b.toString();
	}
}

