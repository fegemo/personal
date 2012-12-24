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
		List<RomanSymbol> rep = new ArrayList<RomanSymbol>();
		
		
		return rep;
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
		final RomanSymbol[] symbols = RomanSymbol.orderedValues();
		List<RomanSymbol> roman = new ArrayList<RomanSymbol>();
		
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (RomanSymbol s : representation) {
			b.append(s.toString().toUpperCase());
		}
		return b.toString();
	}
	
	
	private enum RomanSymbol {
		I (1,		RomanSymbolRole.REPEATER),
		V (5, 		RomanSymbolRole.GUY_IN_THE_MIDDLE),
		X (10,		RomanSymbolRole.REPEATER),
		L (50,		RomanSymbolRole.GUY_IN_THE_MIDDLE),
		C (100,		RomanSymbolRole.REPEATER),
		D (500,		RomanSymbolRole.GUY_IN_THE_MIDDLE),
		M (1000,	RomanSymbolRole.REPEATER);
		
		private final int intValue;
		private final RomanSymbolRole role;
		private final int magnitude;
		private static final RomanSymbolComparator DESC_COMPARATOR = new RomanSymbolComparator(false);
		
		RomanSymbol(int intValue, RomanSymbolRole role) {
			this.intValue = intValue;
			this.role = role;
			int magnitude = -1;
			while (intValue > 0) {
				magnitude++;
				intValue /= 10;
			}
			this.magnitude = magnitude;
		}
		
		public int getIntValue() {
			return intValue;
		}
		
		public RomanSymbolRole getRole() {
			return role;
		}
		
		public int getMagnitude() {
			return magnitude;
		}
		
		public static RomanSymbol[] orderedValues() {
			RomanSymbol[] values = RomanSymbol.values(); 
			Arrays.sort(values, DESC_COMPARATOR);
			return values;
		}
		
		public static RomanSymbol[] getByMagnitude(int magnitude) {
			RomanSymbol[] values = orderedValues();
			List<RomanSymbol> valuesOfThisMagnitude = new ArrayList<RomanSymbol>(2);
			for (RomanSymbol s : values) {
				if (s.magnitude == magnitude) valuesOfThisMagnitude.add(s);
			}
			return (RomanSymbol[]) valuesOfThisMagnitude.toArray();
		}
	}
	
	static enum RomanSymbolRole {
		REPEATER,			// Can appear 3 times and also reduce the next two (bigger) symbols
		GUY_IN_THE_MIDDLE	// Only appears once at most, cannot reduce other symbols
	}
	
	static class RomanSymbolComparator implements Comparator<RomanSymbol>
	{
		private final boolean ascending;
		
		RomanSymbolComparator(boolean ascending) {
			this.ascending = ascending;
		}
		
		public int compare(RomanSymbol o1, RomanSymbol o2) {
			int orderResult = 0;
			if (ascending) {
				orderResult = o1.intValue - o2.intValue;
			} else {
				orderResult = o2.intValue - o1.intValue;
			}
			return orderResult;
		}
		
	}

	public int getIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
