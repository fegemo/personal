package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A number in the roman format.
 * 
 * The only way to create Roman values are by using static constructors that turn objects in
 * other formats to Roman (String).
 * 
 * @author flávio coutinho
 *
 */
public class RomanNumber implements NumberFormat {
	private final List<RomanSymbol> representation;
	
	/**
	 * Builds a roman number from a series of roman symbols.
	 * 
	 * @param symbols The symbols (I, V, X etc.) that build up this roman number.
	 */
	private RomanNumber(List<RomanSymbol> symbols) {
		this.representation = symbols;
	}
	
	/**
	 * Creates a roman number from a string by parsing the roman symbols inside it.
	 * 
	 * @param representation The string with the roman symbols. 
	 * @return The created RomanNumber. 
	 * @throws MalformedNumberException In case the symbols are invalid or do not match the roman
	 * numbers rules.
	 */
	public static RomanNumber fromString(String representation) throws MalformedNumberException {
		List<RomanSymbol> symbols = new ArrayList<>();
		char[] representationChars = representation.toUpperCase().toCharArray();
		
		for (char c : representationChars) {
			RomanSymbol symbol = RomanSymbol.fromCharacter(c);
			symbols.add(symbol);
		}

		validateRomanSequence(symbols);
		
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

	/**
	 * Converts and returns this roman number to integer.
	 */
	public int getIntValue() {
		int bucket = 0;
		RomanSymbol lastSymbol = representation.get(0);
		
		for (RomanSymbol current : representation) {
			bucket += current.getIntValue();
			if (current.getIntValue() > lastSymbol.getIntValue()) {
				bucket -= 2*lastSymbol.getIntValue();
			}
			
			lastSymbol = current;
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
	 * @throws MalformedNumberException 
	 */
	private static void validateRomanSequence(List<RomanSymbol> symbols) throws MalformedNumberException {
		if (symbols.size() == 0) {
			throw new MalformedNumberException("The number was empty.");
		}
		int highestValue = symbols.get(0).getIntValue();
		RomanSymbol lastSymbol = null;
		Map<RomanSymbol, Integer> usages = new HashMap<>(RomanSymbol.values().length); 
		

		for (int i = 0; i < symbols.size(); i++) {
			RomanSymbol current = symbols.get(i);
			
			// giving name to the horse
			boolean isFirstSymbol = i == 0;
			boolean isCurrentBiggerThanLast = !isFirstSymbol && current.getIntValue() > lastSymbol.getIntValue();
			boolean canLastSubtractCurrent = !isFirstSymbol && RomanSymbol.canSubtract(current, lastSymbol);
			
			// asserting (a) that the symbols are in decreasing order
			if (current.getIntValue() > highestValue && !canLastSubtractCurrent) {
				throw new MalformedNumberException(String.format("There is a roman symbol (\"%s\" in the position %d) higher than the highest (\"%s\"). The decreasing order rule was hurt.", current, i + 1, lastSymbol));
			}
			
			// asserting (c) that a symbol can only subtract the following two in increasing order
			if (isCurrentBiggerThanLast && !canLastSubtractCurrent) {
				throw new MalformedNumberException(String.format("There is a roman symbol (\"%s\" in the position %d) higher than the previous (\"%s\") and that is not being subtracted. The subtraction rule was hurt.", current, i + 1, lastSymbol));
			}
			
			
			usages.put(current, usages.containsKey(current) ? usages.get(current) + 1 : 1);
			Integer symbolCounter = usages.get(current);
			
			// asserting (b) that a symbol cannot repeat more than 1 or 3 times
			if (symbolCounter > current.getType().getMaxRepeat()) {
				// exceeded!
				boolean didExceed = true;
				if (current.getType() == RomanSymbolType.REPEATER) {
					boolean reappearingForSubtraction = RomanSymbol.canSubtract(current, lastSymbol) && symbols.get(i-2) == current; 
					if (symbolCounter == current.getType().getMaxRepeat() + 1 && reappearingForSubtraction) {
						didExceed = false;
					}
				}
				
				if (didExceed) {
					throw new MalformedNumberException(String.format("A symbol (\"%s\") appeared more than the number of times it could (%d). Last appearence on position %d.", current, current.getType().getMaxRepeat(), i + 1));
				}
			}
			

			lastSymbol = current;
		}
	}
}

