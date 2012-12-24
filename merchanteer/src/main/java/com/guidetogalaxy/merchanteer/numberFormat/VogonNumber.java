package com.guidetogalaxy.merchanteer.numberFormat;

public class VogonNumber implements NumberFormat {

	private final String representation;
	
	
	public VogonNumber(String representation) {
		this.representation = representation;
	}
	
	@Override
	public int getIntValue() {
		// TODO add the conversion logic from vogon to roman and roman to arabic
		return 0;
	}
	
	@Override
	public String toString() {
		return representation.trim();
	}
}
