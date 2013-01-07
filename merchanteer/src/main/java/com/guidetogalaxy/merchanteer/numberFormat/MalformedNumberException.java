package com.guidetogalaxy.merchanteer.numberFormat;

/**
 * Represents an error when trying to use a number in a format that is not recognized.
 * 
 * @author flávio coutinho
 *
 */
public class MalformedNumberException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a MalformedNumberException with a description message.
	 * @param msg A description message.
	 */
	public MalformedNumberException(String msg) {
		super(msg);
	}

	/**
	 * Creates a MalformedNumberException with a description message and the inner exception.
	 * @param msg A description message.
	 * @param inner The inner exception.
	 */
	public MalformedNumberException(String msg, Exception inner) {
		super(msg, inner);
	}

}
