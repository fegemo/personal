package com.guidetogalaxy.merchanteer.currency;

public class CurrencyConversionException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a CurrencyConversionException with a description message.
	 * @param msg A description message.
	 */
	public CurrencyConversionException(String msg) {
		super(msg);
	}
}
