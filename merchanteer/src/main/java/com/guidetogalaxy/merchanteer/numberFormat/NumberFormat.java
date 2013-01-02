package com.guidetogalaxy.merchanteer.numberFormat;

public interface NumberFormat {
	/**
	 * Returns an int value of a number, despite of its format.
	 * This method is necessary so that we bring any value to a format that the machine can calculate.
	 * 
	 * @return an integer value that represents a number in any format.
	 * @throws NumberConversionException 
	 */
	int getIntValue();
}
