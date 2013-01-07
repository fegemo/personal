package com.guidetogalaxy.merchanteer.numberFormat;

/**
 * A format of representing numbers. All implementing classes need to be able
 * to be translated to a common, calculable format, which is integers.
 * 
 * @author flávio coutinho
 *
 */
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
