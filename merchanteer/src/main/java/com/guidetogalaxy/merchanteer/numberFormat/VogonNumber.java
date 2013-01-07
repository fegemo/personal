package com.guidetogalaxy.merchanteer.numberFormat;

/**
 * A number in the vogon language.
 * 
 * Side note: as the vogons were the secret colonizers of the roman civilization, they tought the colonized people
 * how to operate with numbers. Thus, there's a direct relation to every vogon word and a roman symbol.
 * @author flávio coutinho
 *
 */
public final class VogonNumber implements NumberFormat {

	private final String representation;
	private final RomanNumber equivalentRomanNumber;
	
	private VogonNumber(String representation, RomanNumber equivalent) {
		this.representation = representation;
		this.equivalentRomanNumber = equivalent;
	}
	
	/**
	 * Creates a vogon number from a string representation with a vogon phrase.
	 * 
	 * @param representation a phrase containing vogon words for numbers.
	 * @return the generated vogon number.
	 * @throws MalformedNumberException if one of the words used is not a known vogon word or if the equivalent roman number is malformed. 
	 */
	public static VogonNumber fromString(String representation) throws MalformedNumberException {
		representation = representation.trim();
		
		if (representation.length() == 0) {
			throw new MalformedNumberException("A vogon number cannot be created with an empty representation.");
		}

		String[] words = representation.split(" ");
		StringBuilder romanString = new StringBuilder(words.length);

		// tries to match each word with a word from the vogon dialect and
		// gets a roman symbol for each vogon word 
		for (String word : words) {
			if (!VogonDialect.INSTANCE.containsVogonWord(word)) {
				throw new MalformedNumberException(String.format("When trying to create a VogonNumber from the phrase \"%s\", one of its words was not recognized from the vogon dialect.", representation));
			}
			
			romanString.append(VogonDialect.INSTANCE.getRomanEquivalent(word));
		}		
		
		
		// with the roman number in hads, we can return its int value
		RomanNumber roman = RomanNumber.fromString(romanString.toString());	
		
		
		return new VogonNumber(representation, roman);
	}

	/**
	 * Returns an integer value for this VogonNumber.
	 */
	public int getIntValue() {
		return equivalentRomanNumber.getIntValue();
	}
	
	@Override
	public String toString() {
		return representation;
	}
}
