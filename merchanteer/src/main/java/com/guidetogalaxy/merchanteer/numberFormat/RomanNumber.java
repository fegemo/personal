package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RomanNumber implements NumberFormat {
	private final List<RomanSymbol> representation;
	/*private static final String IDENTITY = "";*/
	public static final int MAX_ROMAN_INT_VALUE = 3999;
	public static final int MIN_ROMAN_INT_VALUE = 0;
	
	
	public RomanNumber(String candidateRepresentation) {
		this.representation = buildRomanNumberRepresentation(candidateRepresentation);
	}
	
	private RomanNumber(List<RomanSymbol> symbols) {
		this.representation = symbols;
	}
	
	private List<RomanSymbol> buildRomanNumberRepresentation(String candidateRepresentation) {
		List<RomanSymbol> representation = new ArrayList<RomanSymbol>();
		
		
		return representation;
	}
	
	/*
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
	*/
	
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
	
	public static RomanNumber fromString(String representation) throws MalformedNumberException {
		List<RomanSymbol> symbols = new ArrayList<>();
		char[] representationChars = representation.toCharArray();
		
		for (char c : representationChars) {
			RomanSymbol symbol = RomanSymbol.fromCharacter(c);
			symbols.add(symbol);
		}
		
		return new RomanNumber(symbols);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (RomanSymbol s : representation) {
			b.append(s.toString().toUpperCase());
		}
		return b.toString();
	}

	@Override
	public int getIntValue() {
		int bucket = 0;
		RomanSymbol lastSymbol = null;
		
		for (RomanSymbol symbol : representation) {
			// check if we need to add or subtract the current symbol
			if (symbol.getIntValue() <= lastSymbol.getIntValue()) {
				bucket += symbol.getIntValue();
			} else {
				// subtract
				bucket -= symbol.getIntValue();
			}
			
			lastSymbol = symbol;
		}
		
		return bucket;
	}
	
	/**
	 * Checks if the provided roman symbols sequence is a valid roman number. If not, an exception is thrown describing the reason.
	 * 
	 * It ensures that:
	 *  (a) the symbols are in decreasing order (except when subtractions can occur)
	 *  (b) a symbol cannot repeat more than 1 or 3 times (depending on the symbol)
	 *  (c) a symbol can only subtract the two next symbols in increasing order
	 *  
	 * @param symbols
	 * @return
	 * @throws MalformedNumberException 
	 */
	private static void validateRomanSequence(List<RomanSymbol> symbols) throws MalformedNumberException {
		RomanSymbol highestSymbol = symbols.get(0);
		RomanSymbol lastSymbol = null;
		Map<RomanSymbol, Integer> usages = new HashMap<>(RomanSymbol.values().length); 
		int symbolCounter = 0;
		

		for (int i = 0; i < symbols.size(); i++) {
			//highestSymbolSoFar = highestSymbolSoFar == null ? symbol : (symbol.getIntValue() > highestSymbolSoFar.getIntValue() ? symbol : highestSymbolSoFar); 
			RomanSymbol current = symbols.get(i);

			// Asserting (a) that the symbols are in decreasing order
			if (lastSymbol != null && current.getIntValue() > lastSymbol.getIntValue() && ) {
				throw new MalformedNumberException(String.format("There is a roman symbol (\"%s\" in the position %d) higher than the previous and that is not being subtracted.", current, i + 1));
			}
			
			
			
			

			lastSymbol = current;
		}
	}
}

