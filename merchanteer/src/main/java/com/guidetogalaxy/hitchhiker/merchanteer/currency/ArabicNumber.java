package com.guidetogalaxy.hitchhiker.merchanteer.currency;

public final class ArabicNumber {
	private final int representation;
	private static final int IDENTITY = 0;
	
	public ArabicNumber(int representation) {
		this.representation = representation;
	}
	
	public static ArabicNumber fromRomanFormat(String inRoman) throws CurrencyConversionException {
		ArabicNumber arabic = new ArabicNumber(IDENTITY);
		//...
		return arabic;
	}
	
	@Override
	public String toString() {
		return String.valueOf(representation);
	}
}
