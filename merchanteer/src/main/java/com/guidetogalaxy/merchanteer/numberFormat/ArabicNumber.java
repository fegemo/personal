package com.guidetogalaxy.merchanteer.numberFormat;

/**
 * A number format that uses arabic digits on base 10. It's Earth's most common number format
 * by the time the merchant left.
 * 
 * The only way to create Arabic values are by using static constructors that turn objects in
 * other formats to Arabic (String, int).
 * 
 * The class is immutable.
 * 
 * @author flávio coutinho
 *
 */
public final class ArabicNumber implements NumberFormat {
	private final int representation;
	
	/**
	 * Creates a number from an integer value.
	 * 
	 * @param representation the value representing this number.
	 */
	private ArabicNumber(int representation) {
		this.representation = representation;
	}

	/**
	 * Builds an ArabicNumber from a string.
	 * 
	 * @param representation The string that contains the number.
	 * @return The created ArabicNumber.
	 * @throws MalformedNumberException In case there's not a recognized integer value inside the string.
	 */
	public static ArabicNumber fromString(String representation) throws MalformedNumberException {
		Integer extractedValue = null;
		try {
			extractedValue = Integer.parseInt(representation);
		} catch (NumberFormatException ex) {
			throw new MalformedNumberException(String.format("There wasn't an integer number in the String \"%s\".", representation), ex);
		}
		
		return new ArabicNumber(extractedValue);
	}
	
	/**
	 * Builds an ArabicNumber from an integer.
	 * 
	 * @param representation The integer to get an ArabicNumber from.
	 * @return The created ArabicNumber. 
	 */
	public static ArabicNumber fromInt(int representation) {
		return new ArabicNumber(representation);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ArabicNumber && ((ArabicNumber)obj).representation == representation;
	}
	
	@Override
	public int hashCode() {
		return representation;
	}
	
	@Override
	public String toString() {
		return String.valueOf(representation);
	}

	/**
	 * Returns the integer value of this number.
	 */
	public int getIntValue() {
		return representation;
	}
}
