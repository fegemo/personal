package com.guidetogalaxy.merchanteer.numberFormat;

public class MalformedNumberException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MalformedNumberException(String msg) {
		super(msg);
	}

	public MalformedNumberException(String msg, Exception inner) {
		super(msg, inner);
	}

}
