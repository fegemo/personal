package com.guidetogalaxy.merchanteer;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.junit.Cucumber.Options;

/**
 * Runs all the cucumber feature files.
 * 
 * @author flávio coutinho
 *
 */
@RunWith(Cucumber.class)
@Options(format={"pretty", "html:target/cucumber"})
public class RunCukesTest {

}
