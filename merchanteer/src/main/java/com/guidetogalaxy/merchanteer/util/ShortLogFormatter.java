package com.guidetogalaxy.merchanteer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A formatter class to be used to print shorter log phrases (1 line only).
 * 
 * @author flávio coutinho
 *
 */
public class ShortLogFormatter extends Formatter {

	private static final DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss");

	@Override
	public String format(LogRecord record) {
		return String.format("[%s]\t%s %s.%s: %s%s",
				record.getLevel(),
				dateFormatter.format(new Date(record.getMillis())),
				record.getLoggerName(),
				record.getSourceMethodName(),
				record.getMessage(),
				System.getProperty("line.separator"));
	}

}
