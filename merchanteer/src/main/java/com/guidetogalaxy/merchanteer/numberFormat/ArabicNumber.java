package com.guidetogalaxy.merchanteer.numberFormat;

public final class ArabicNumber implements NumberFormat {
	private final int representation;
	
	private ArabicNumber(int representation) {
		this.representation = representation;
	}
	
	/*
	public static ArabicNumber fromRomanFormat(String inRoman) throws CurrencyConversionException {
		char[] inRomanChars = inRoman.toCharArray();
		RomanSymbol highestSymbolSofar = null;
		RomanSymbol lastSymbol = null;
		int numberOfHighestNumberAppearences = 0;
		int arabicValue = 0;
		
		for (char character : inRomanChars) {
			RomanSymbol symbol = null;
			try {
				symbol = RomanSymbol.fromCharacter(character);
				if (highestSymbolSofar == null) {
					highestSymbolSofar = symbol;
					numberOfHighestNumberAppearences++;
				} else if (symbol == highestSymbolSofar) {
					numberOfHighestNumberAppearences++;
					if (numberOfHighestNumberAppearences)
				}
				else if (highestSymbolSofar.getIntValue() < symbol.getIntValue()) {
					throw new MalformedCurrencyException("A roman symbol bigger the the previous was found to the right of the former.");
				}
				
				arabicValue += symbol.getIntValue();
				lastSymbol = symbol;
				
			} catch (MalformedCurrencyException ex) {
				throw new CurrencyConversionException(ex.getMessage());
			}
		}

		return new ArabicNumber(arabicValue);
	}
	*/
	
	public static ArabicNumber fromString(String representation) throws MalformedNumberException {
		Integer extractedValue = null;
		try {
			extractedValue = Integer.parseInt(representation);
		} catch (NumberFormatException ex) {
			throw new MalformedNumberException(String.format("There wasn't an integer number in the String \"%s\".", representation));
		}
		
		return new ArabicNumber(extractedValue);
	}
	
	public static ArabicNumber fromInt(int representation) {
		return new ArabicNumber(representation);
	}
	
	@Override
	public String toString() {
		return String.valueOf(representation);
	}

	@Override
	public int getIntValue() {
		return representation;
	}
}
