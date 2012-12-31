package com.guidetogalaxy.merchanteer.numberFormat;

public final class VogonNumber implements NumberFormat {

	private final String representation;
	
	
	private VogonNumber(String representation) {
		this.representation = representation;
	}
	
	private static boolean areVogonWordsValid(String phrase) {
		// splits the representation in different words
		String[] words = phrase.split(" ");

		// tries to match each word with a word from the vogon dialect
		for (String word : words) {
			if (!VogonDialect.INSTANCE.containsVogonWord(word)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static VogonNumber fromString(String representation) throws MalformedNumberException {
		if (!areVogonWordsValid(representation)) {
			throw new MalformedNumberException(String.format("When trying to create a VogonNumber from the phrase \"%s\", one of its words was not recognized from the vogon dialect.", representation));
		}
		
		return new VogonNumber(representation);
	}
	
	@Override
	public int getIntValue() {
		// Convert to a roman number, then the roman number to arabic
		return 0;
	}
	
	@Override
	public String toString() {
		return representation.trim();
	}
}
