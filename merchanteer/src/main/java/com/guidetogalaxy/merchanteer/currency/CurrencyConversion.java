package com.guidetogalaxy.merchanteer.currency;

import java.util.HashMap;
import java.util.Map;


public enum CurrencyConversion {
	INSTANCE;
	
	private Map<String, Map<String, Double>> quotations;
	
	private CurrencyConversion() {
		this.quotations = new HashMap<>();
	}
	
	public void addQuotation(String currencyName, String otherCurrencyName, double ratio) {
		if (!quotations.containsKey(currencyName)) {
			quotations.put(currencyName, new HashMap<String, Double>());
		}
		quotations.get(currencyName).put(otherCurrencyName, ratio);
	}
	
	public double getQuotation(String currencyName, String otherCurrencyName) throws CurrencyConversionException {
		// tries to get the direct and counter-direct way
		if (quotations.containsKey(currencyName) && quotations.get(currencyName).containsKey(otherCurrencyName)) {
			return quotations.get(currencyName).get(otherCurrencyName);
		} else if (quotations.containsKey(otherCurrencyName) && quotations.get(otherCurrencyName).containsKey(currencyName)) {
			return 1/quotations.get(otherCurrencyName).get(currencyName);
		}
		
		// no conversion rules found for these currencies
		throw new CurrencyConversionException(String.format("There was no suitable conversion to be made between %s and %s.", currencyName, otherCurrencyName));
	}
	
}
