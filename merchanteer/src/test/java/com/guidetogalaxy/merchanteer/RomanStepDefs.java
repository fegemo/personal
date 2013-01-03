package com.guidetogalaxy.merchanteer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.guidetogalaxy.merchanteer.numberFormat.ArabicNumber;
import com.guidetogalaxy.merchanteer.numberFormat.MalformedNumberException;
import com.guidetogalaxy.merchanteer.numberFormat.RomanNumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RomanStepDefs {
	
	private String letters;
	private String romanCandidate;
	private String arabicCandidate;
	private RomanNumber roman;
	private ArabicNumber arabic;
	
	@Given("^I have a (\\w+)$")
	public void I_have_a(String letters) {
	    this.letters = letters;
	}

	@When("^I parse it as a roman number$")
	public void I_parse_it_as_a_roman_number() {
	    try {
			this.roman = RomanNumber.fromString(letters);
		} catch (MalformedNumberException e) {
			this.roman = null;
		}
	}

	@Then("^it should work because of its form$")
	public void it_should_work_because_of_its_form() {
		assertNotNull("the conversion to roman number failed", roman);
	}

	@Then("^it should not work because of its form$")
	public void it_should_not_work_because_of_its_form() {
	    assertNull("the conversion to roman number should have failed but didn't", roman);
	}

	@Then("^I get the number (\\w+) in roman$")
	public void I_get_the_number_in_roman(String roman) {
	    assertEquals(this.roman.toString(), roman);
	}

	@Given("^I have the number (\\w+) in roman$")
	public void I_have_the_number_in_roman(String letters) {
	    this.romanCandidate = letters; 
	}

	@When("^I convert it to arabic$")
	public void I_convert_it_to_arabic() {
		try {
			this.arabic = ArabicNumber.fromString(String.valueOf(RomanNumber.fromString(romanCandidate).getIntValue()));
		} catch (MalformedNumberException ex) {
			this.arabic = null;
		}
	}

	@Then("^the arabic result should be (\\w+)$")
	public void the_arabic_result_should_be(String arabic) {
	    assertEquals(this.arabic.toString(), arabic);
	}
	
	@Given("^I have the number (\\w+) in arabic$")
	public void I_have_the_number_in_arabic(String letters) {
		this.arabicCandidate = letters; 
	}

	@When("^I convert it to roman$")
	public void I_convert_it_to_roman() {
		try {
			this.roman = RomanNumber.fromString(arabicCandidate);
		} catch (MalformedNumberException ex) {
			this.roman = null;
		}
	}
	
	@Then("^the roman result should be (\\w+)$")
	public void the_roman_result_should_be(String roman) {
	    assertEquals(this.roman.toString(), roman);
	}
	
	@Then("^the conversion should fail as the number cannot be represented in roman notation$")
	public void the_conversion_should_fail_as_the_number_cannot_be_represented_in_roman_notation() {
		assertNull("the conversion to roman should have failed but did not", this.roman); 
	}
}
