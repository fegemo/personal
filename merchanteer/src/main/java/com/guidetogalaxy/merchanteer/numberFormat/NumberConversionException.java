package com.guidetogalaxy.merchanteer.numberFormat;

/**
 * Represents an error when converting between different number formats.
 * 
 * @author flávio coutinho
 *
 */
public class NumberConversionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a NumberConversionException with a description message.
	 * @param msg A description message.
	 */
	public NumberConversionException(String msg) {
		super(msg);
	}

}
