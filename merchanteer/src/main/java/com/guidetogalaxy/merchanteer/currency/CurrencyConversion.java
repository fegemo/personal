package com.guidetogalaxy.merchanteer.currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A conversion table utility that enables transforming values from one currency to another,
 * when there is a direct quotation between the two currencies or when one can be inferred.
 * 
 * @author flávio coutinho
 *
 */
public enum CurrencyConversion {
	INSTANCE;
	
	private Map<String, Map<String, Double>> quotations;
	
	private CurrencyConversion() {
		this.quotations = new HashMap<>();
	}
	
	/**
	 * Erases all the quotations that were in the table.
	 */
	public void clearQuotations() {
		this.quotations.clear();
	}
	
	/**
	 * Adds a quotation in the table.
	 * 
	 * @param currencyName The first currency. 
	 * @param otherCurrencyName The other currency.
	 * @param ratio The value of the first currency over the second.
	 */
	public void addQuotation(String currencyName, String otherCurrencyName, double ratio) {
		if (!quotations.containsKey(currencyName)) {
			quotations.put(currencyName, new HashMap<String, Double>());
		}
		// inserts the quotation to the table
		quotations.get(currencyName).put(otherCurrencyName, ratio);
		quotations.get(currencyName).put(currencyName, 1d);
		
		if (!quotations.containsKey(otherCurrencyName)) {
			quotations.put(otherCurrencyName, new HashMap<String, Double>());
		}
		// inserts the quotation in the opposite direction on the table
		quotations.get(otherCurrencyName).put(currencyName, 1f / ratio);
		quotations.get(otherCurrencyName).put(otherCurrencyName, 1d);
	}
	
	/**
	 * Returns the quotation (if there is one) between two currencies.
	 * The value returned is how much of the second currency is worth 1 of the first currency. 
	 * 
	 * @param currencyName the name of the currency that represents the 1 value.
	 * @param otherCurrencyName the name of the currency we are returning the quotation from.
	 * @return how much of the second currency is worth 1 of the first currency.
	 * @throws CurrencyConversionException in case there's not an available quotation between the currencies.
	 */
	public double getQuotation(String currencyName, String otherCurrencyName) throws CurrencyConversionException {
		// tries to get the direct way
		if (quotations.containsKey(currencyName) && quotations.get(currencyName).containsKey(otherCurrencyName)) {
			return quotations.get(currencyName).get(otherCurrencyName);
		}
		
		// checks for transitivity among quotations so as to fill in more quotations inferrable from the data
		Double quotation = getTransitiveQuotation(currencyName, otherCurrencyName);
		
		// if a quotation was inferred, we add it to the table
		if (quotation != null) {
			addQuotation(currencyName, otherCurrencyName, quotation);
			return quotation;
		}

		// no conversion rules found for these currencies, even with transitive inference
		throw new CurrencyConversionException(String.format("There was no suitable conversion to be made between %s and %s.", currencyName, otherCurrencyName));
	}
	
	/**
	 * Tries to fetch the quotation value between two currencies that do not have a direct mapping, but
	 * may have its quotation inferred by using quotations from other currencies (transitively).
	 * 
	 * @param currencyName The first quotation.
	 * @param otherCurrencyName The other quotation.
	 * @return The value of the quotation (if one could be inferred) or null, otherwise.
	 */
	private Double getTransitiveQuotation(String currencyName, String otherCurrencyName) {
		return getTransitiveQuotationRecursive(currencyName, otherCurrencyName, currencyName, new ArrayList<String>());
	}
	
	/**
	 * Works recursively to try to transitively fetch a quotation between the first two currencies.
	 * 
	 * @param currencyName The first currency.
	 * @param otherCurrencyName The other currency.
	 * @param lastCurrencyName The last currency that was analyzed.
	 * @param exhaustedTries All the currencies that have been analyzed.
	 * @return The value of the quotation (if one could be inferred) or null, otherwise.
	 */
	private Double getTransitiveQuotationRecursive(String currencyName, String otherCurrencyName, String lastCurrencyName, List<String> exhaustedTries) {
		// tries to get a quotation the direct way
		// this is the stop condition for the recursion
		if (quotations.containsKey(lastCurrencyName) && quotations.get(lastCurrencyName).containsKey(otherCurrencyName)) {
			return quotations.get(lastCurrencyName).get(otherCurrencyName);
		}
		
		Map<String, Double> currentCurrencyQuotations = quotations.get(lastCurrencyName);
		
		for (String currentCurrencyName : currentCurrencyQuotations.keySet()) {
			if (currentCurrencyName.equals(lastCurrencyName) || currentCurrencyName.equals(currencyName) || exhaustedTries.contains(currentCurrencyName)) {
				continue;
			}
			
			// marks this currency (currentCurrencyName) as having been visited
			exhaustedTries.add(currentCurrencyName);
			Double henceforwardResult = getTransitiveQuotationRecursive(currencyName, otherCurrencyName, currentCurrencyName, exhaustedTries);
			if (henceforwardResult != null) {
				return currentCurrencyQuotations.get(currentCurrencyName) * henceforwardResult;
			}
		}
		
		
		return null;
	}
	
}
