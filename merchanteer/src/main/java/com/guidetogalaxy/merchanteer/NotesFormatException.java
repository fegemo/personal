package com.guidetogalaxy.merchanteer;

/**
 * An exception describing an error when reading the merchant's notes.
 *  
 * @author flávio coutinho
 *
 */
public class NotesFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a NotesFormatException with a description message.
	 * 
	 * @param msg the message describing the exception.
	 */
	public NotesFormatException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a NotesFormatException with a description message and an inner exception.
	 * 
	 * @param msg the message describing the exception. 
	 * @param inner the exception that cause this NotesFormatException.
	 */
	public NotesFormatException(String msg, Exception inner) {
		super(msg, inner);
	}
}
