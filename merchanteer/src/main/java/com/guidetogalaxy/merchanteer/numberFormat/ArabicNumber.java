package com.guidetogalaxy.merchanteer.numberFormat;

public final class ArabicNumber {
	private final int representation;
	private static final int IDENTITY = 0;
	
	public ArabicNumber(int representation) {
		this.representation = representation;
	}
	
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
	
	@Override
	public String toString() {
		return String.valueOf(representation);
	}
}
