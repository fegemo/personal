package com.guidetogalaxy.merchanteer.numberFormat;


public final class ArabicNumber implements NumberFormat {
	private final int representation;
	private static final int IDENTITY = 0;
	
	public ArabicNumber(int representation) {
		this.representation = representation;
	}
	
	public static ArabicNumber fromRomanFormat(String inRoman) throws NumberConversionException {
		ArabicNumber arabic = new ArabicNumber(IDENTITY);
		//...
		return arabic;
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
