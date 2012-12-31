package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum RomanSymbol {
	I (1, 3, true),
	V (5, 1, false),
	X (10, 3, true),
	L (50, 1, false),
	C (100, 3, true),
	D (500, 1, false),
	M (1000, 3, true);
	
	private final int intValue;
	private final int maxRepeat;
	private final boolean canSubtractNextTwoSymbols;
	private final int magnitude;
	private static final RomanSymbolComparator DESC_COMPARATOR = new RomanSymbolComparator(false);
	private static final RomanSymbolComparator ASC_COMPARATOR = new RomanSymbolComparator(true);
	
	RomanSymbol(int intValue, int maxRepeat, boolean canSubtractNextTwoSymbols) {
		this.intValue = intValue;
		this.maxRepeat = maxRepeat;
		this.canSubtractNextTwoSymbols = canSubtractNextTwoSymbols;
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
	
	public int getMaxRepeat() {
		return maxRepeat;
	}
	
	/**
	 * 
	 * @param minuend the initial quantity, i.e., the one that we have before subtraction.
	 * @param subtrahend what it going to be subtracted from the initial quantity.
	 * @return
	 */
	public boolean canSubtract(RomanSymbol minuend, RomanSymbol subtrahend) {
		return subtrahend.canSubtractNextTwoSymbols && (minuend == getNext(subtrahend) || (getNext(subtrahend) != null && minuend == getNext(getNext(subtrahend))));
	}

	public static RomanSymbol getNext(RomanSymbol s) {
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
	
	public static RomanSymbol[] orderedValues(boolean ascending) {
		RomanSymbol[] values = RomanSymbol.values(); 
		Arrays.sort(values, ascending ? ASC_COMPARATOR : DESC_COMPARATOR);
		return values;
	}
	
	public static RomanSymbol[] getByMagnitude(int magnitude) {
		RomanSymbol[] values = orderedValues(true);
		List<RomanSymbol> valuesOfThisMagnitude = new ArrayList<RomanSymbol>(2);
		for (RomanSymbol s : values) {
			if (s.magnitude == magnitude) valuesOfThisMagnitude.add(s);
		}
		return (RomanSymbol[]) valuesOfThisMagnitude.toArray();
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