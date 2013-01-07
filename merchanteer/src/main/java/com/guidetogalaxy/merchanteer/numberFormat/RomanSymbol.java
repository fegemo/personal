package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.Arrays;
import java.util.Comparator;

/**
 * An enumeration of the valid roman symbols that are recognized by the Merchanteer program.
 * 
 * @author flávio coutinho
 *
 */
public enum RomanSymbol {
	I (1, RomanSymbolType.REPEATER),
	V (5, RomanSymbolType.DEDUCIBLE),
	X (10, RomanSymbolType.REPEATER),
	L (50, RomanSymbolType.DEDUCIBLE),
	C (100, RomanSymbolType.REPEATER),
	D (500, RomanSymbolType.DEDUCIBLE),
	M (1000, RomanSymbolType.REPEATER);
	
	private final int intValue;
	private final RomanSymbolType type;

	private static final RomanSymbolComparator DESC_COMPARATOR = new RomanSymbolComparator(false);
	private static final RomanSymbolComparator ASC_COMPARATOR = new RomanSymbolComparator(true);
	
	RomanSymbol(int intValue, RomanSymbolType type) {
		this.intValue = intValue;
		this.type = type;
	}
	
	/**
	 * Returns the integer value of the symbol.
	 * 
	 * @return The integer value of the symbol.
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * Returns the type of the symbol in a roman number (that says if it can repeat etc.).
	 * 
	 * @return The type of the symbol.
	 */
	public RomanSymbolType getType() {
		return type;
	}
	
	/**
	 * Determines whether the first symbol can be subtracted from the second.
	 * 
	 * @param minuend the initial quantity, i.e., the one that we have before subtraction.
	 * @param subtrahend what it going to be subtracted from the initial quantity.
	 * @return true if the first symbol can be subtracted from the second or false, otherwise.
	 */
	public static boolean canSubtract(RomanSymbol minuend, RomanSymbol subtrahend) {
		return subtrahend.type.getCanSubtractNextTwoSymbols() && (minuend == getNext(subtrahend) || (getNext(subtrahend) != null && minuend == getNext(getNext(subtrahend))));
	}

	/**
	 * Returns the next roman symbol in increasing value.
	 * 
	 * @param s The symbol of which we're querying the next.
	 * @return The next roman symbol.
	 */
	private static RomanSymbol getNext(RomanSymbol s) {
		RomanSymbol[] values = RomanSymbol.orderedValues(true);
		for (RomanSymbol i : values) {
			if (i.intValue > s.intValue) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Returns a RomanSymbol instance from a character (i, v, etc.).
	 * 
	 * @param s The character that represents the symbol.
	 * @return The matching RomanSymbol instance.
	 * @throws MalformedNumberException In case the character does not represent a valid RomanSymbol. 
	 */
	public static RomanSymbol fromCharacter(char s) throws MalformedNumberException {
		try {
			return RomanSymbol.valueOf(String.valueOf(s));			
		} catch (Exception ex) {
			throw new MalformedNumberException("An attempt to convert a char with a non recognized value (" + s + ") to a roman symbol generated an error.");
		}
		
	}
	
	/**
	 * Returns a list of all RomanSymbol instances ordered by increasing or decreasing value.
	 * 
	 * @param ascending True if the order should be ascending, false otherwise.
	 * @return a list of all ordered RomanSymbol instances. 
	 */
	private static RomanSymbol[] orderedValues(boolean ascending) {
		RomanSymbol[] values = RomanSymbol.values(); 
		Arrays.sort(values, ascending ? ASC_COMPARATOR : DESC_COMPARATOR);
		return values;
	}
}

/**
 * The type of a roman symbol. It represents the role of a symbol in a roman number,
 * which can be used for validation purposes.
 * 
 * @author flávio coutinho
 *
 */
enum RomanSymbolType {
	REPEATER	(3, true),
	DEDUCIBLE	(1, false);
	
	private final int maxRepeat;
	private final boolean canSubtractNextTwoSymbols;

	RomanSymbolType(int maxRepeat, boolean canSubtractNextTwoSymbols) {
		this.maxRepeat = maxRepeat;
		this.canSubtractNextTwoSymbols = canSubtractNextTwoSymbols;
	}
	
	/**
	 * Returns the max number the symbol can repeat.
	 * 
	 * @return The max number the symbol can repeat.
	 */
	int getMaxRepeat() {
		return maxRepeat;
	}
	
	/**
	 * Returns if this symbol can subtract other symbols in a roman number.
	 * 
	 * @return If this symbol can subtract other symbols in a roman number.
	 */
	boolean getCanSubtractNextTwoSymbols() {
		return canSubtractNextTwoSymbols;
	}
}


/**
 * A comparator that considers the integer value of each symbol to sort them.
 * 
 * @author flávio coutinho
 *
 */
class RomanSymbolComparator implements Comparator<RomanSymbol>
{
	private final boolean ascending;
	
	RomanSymbolComparator(boolean ascending) {
		this.ascending = ascending;
	}
	
	public int compare(RomanSymbol o1, RomanSymbol o2) {
		int orderResult = 0;
		if (ascending) {
			orderResult = o1.getIntValue() - o2.getIntValue();
		} else {
			orderResult = o2.getIntValue() - o1.getIntValue();
		}
		return orderResult;
	}
	
}