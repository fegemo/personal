package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.Arrays;
import java.util.Comparator;

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
		/*
		int magnitude = -1;
		while (intValue > 0) {
			magnitude++;
			intValue /= 10;
		}
		this.magnitude = magnitude;
		*/
	}
	
	public int getIntValue() {
		return intValue;
	}

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

	private static RomanSymbol getNext(RomanSymbol s) {
		RomanSymbol[] values = RomanSymbol.orderedValues(true);
		for (RomanSymbol i : values) {
			if (i.intValue > s.intValue) {
				return i;
			}
		}
		return null;
	}
	
	public static RomanSymbol fromCharacter(char s) throws MalformedNumberException {
		try {
			return RomanSymbol.valueOf(String.valueOf(s));			
		} catch (Exception ex) {
			throw new MalformedNumberException("An attempt to convert a char with a non recognized value (" + s + ") to a roman symbol generated an error.");
		}
		
	}
	
	
	private static RomanSymbol[] orderedValues(boolean ascending) {
		RomanSymbol[] values = RomanSymbol.values(); 
		Arrays.sort(values, ascending ? ASC_COMPARATOR : DESC_COMPARATOR);
		return values;
	}
	
	/*
	public static RomanSymbol[] getByMagnitude(int magnitude) {
		RomanSymbol[] values = orderedValues(true);
		List<RomanSymbol> valuesOfThisMagnitude = new ArrayList<RomanSymbol>(2);
		for (RomanSymbol s : values) {
			if (s.magnitude == magnitude) valuesOfThisMagnitude.add(s);
		}
		return (RomanSymbol[]) valuesOfThisMagnitude.toArray();
	}
	*/
}

enum RomanSymbolType {
	REPEATER	(3, true),
	DEDUCIBLE	(1, false);
	
	private final int maxRepeat;
	private final boolean canSubtractNextTwoSymbols;

	RomanSymbolType(int maxRepeat, boolean canSubtractNextTwoSymbols) {
		this.maxRepeat = maxRepeat;
		this.canSubtractNextTwoSymbols = canSubtractNextTwoSymbols;
	}
	
	int getMaxRepeat() {
		return maxRepeat;
	}
	
	boolean getCanSubtractNextTwoSymbols() {
		return canSubtractNextTwoSymbols;
	}
}

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