package com.guidetogalaxy.merchanteer.numberFormat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.guidetogalaxy.merchanteer.RunCukesTest;

@RunWith(Suite.class)
@SuiteClasses({ ArabicNumberTest.class, RomanNumberTest.class,
		RomanSymbolTest.class, RunCukesTest.class })
public class NumberFormatSuite {

}
