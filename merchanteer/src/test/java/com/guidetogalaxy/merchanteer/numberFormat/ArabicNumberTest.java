package com.guidetogalaxy.merchanteer.numberFormat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.guidetogalaxy.merchanteer.numberFormat.ArabicNumber;

public class ArabicNumberTest {

	private ArabicNumber sample1;
	private ArabicNumber sample2;
	private ArabicNumber sample3;
	private ArabicNumber sample4;
	private ArabicNumber sample5;
	
	@Before
	public void init() throws Exception {
		sample1 = new ArabicNumber(-10000);
		sample2 = new ArabicNumber(-1);
		sample3 = new ArabicNumber(0);
		sample4 = new ArabicNumber(1);
		sample5 = new ArabicNumber(10000);
	}

	@Test
	public void testGetIntValue() {
		assertEquals("Wrong transformation to int!", -1000, sample1.getIntValue());
		assertEquals("Wrong transformation to int!", -1, sample2.getIntValue());
		assertEquals("Wrong transformation to int!", 0, sample3.getIntValue());
		assertEquals("Wrong transformation to int!", 1, sample4.getIntValue());
		assertEquals("Wrong transformation to int!", 1000, sample5.getIntValue());
	}

}
