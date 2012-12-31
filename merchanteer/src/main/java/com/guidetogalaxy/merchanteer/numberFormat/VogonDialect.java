package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton that stores the vogon words that represent roman symbols. 
 * 
 * @author flávio coutinho
 *
 */
public enum VogonDialect {
	/**
	 * The single instance that contains the vogon words.
	 */
	INSTANCE;
	
	private final Map<String, RomanSymbol> mapping;
	
	private VogonDialect() {
		mapping = new HashMap<>();
	}
	
	/**
	 * Adds a vogon word and its equivalent roman symbol to the dialect.
	 * In case the same vogon word is added to the dialect, only the last value is persisted,
	 * i.e., the older value is overriden.
	 * 
	 * @param vogonPhoneme a String with a vogon word that represents a roman symbol.
	 * @param romanEquivalent the roman symbol that this vogon word represents.
	 */
	public void addVogonToRomanMapping(String vogonPhoneme, RomanSymbol romanEquivalent) {
		mapping.put(vogonPhoneme, romanEquivalent);
	}
	
	/**
	 * Returns whether the provided word is part of the vogon dialect or not.
	 * 
	 * @param vogonPhoneme a String with a word being queried in the vogon dialect.
	 * @return true if the word exists in the dialect or false, otherwise.
	 */
	public boolean containsVogonWord(String vogonPhoneme) {
		return mapping.containsKey(vogonPhoneme);
	}
	
	/**
	 * Returns the roman symbol that is equivalent of a vogon word in the dialect.
	 * If the word does not exist in the dialect, it returns null.
	 * 
	 * @param vogonPhoneme a String with a word from the vogon dialect.
	 * @return the roman symbol equivalent to the vogon word.
	 */
	public RomanSymbol getRomanEquivalent(String vogonPhoneme) {
		return mapping.get(vogonPhoneme);
	}
}
