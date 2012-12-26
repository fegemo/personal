package com.guidetogalaxy.merchanteer.numberFormat;

import java.util.HashMap;
import java.util.Map;

public enum VogonDialect {
	INSTANCE;
	
	private final Map<String, RomanSymbol> mapping;
	
	private VogonDialect() {
		mapping = new HashMap<>();
	}
	
	public void addVogonToRomanMapping(String vogonPhoneme, RomanSymbol romanEquivalent) {
		// TODO implement adding/editing to the map
		
		
	}
	
	public boolean containsVogonWord(String vogonPhoneme) {
		return mapping.containsKey(vogonPhoneme);
	}
}
