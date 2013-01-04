package com.guidetogalaxy.merchanteer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.guidetogalaxy.merchanteer.currency.CurrencyConversion;
import com.guidetogalaxy.merchanteer.currency.CurrencyConversionException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CurrencyStepDefs {
	
	private Double converted = null;
	private Exception ex = null;
	
	@Given("^a currency named \"([^\"]*)\"$")
	public void a_currency_named(String arg1) throws Throwable {
	   CurrencyConversion.INSTANCE.addQuotation(arg1, arg1, 1);
	}

	@Given("^one \"([^\"]*)\" is worth (\\d+) \"([^\"]*)\"$")
	public void one_is_worth(String arg1, int arg2, String arg3) throws Throwable {
		CurrencyConversion.INSTANCE.addQuotation(arg1, arg3, arg2);
	}

	@When("^I convert (\\d+) \"([^\"]*)\" to \"([^\"]*)\"$")
	public void I_convert_to(int arg1, String arg2, String arg3) throws Throwable {
		try {
			converted = CurrencyConversion.INSTANCE.getQuotation(arg2, arg3) * arg1;
			this.ex = null;
		} catch (CurrencyConversionException ex) {
			converted = null;
			this.ex = ex;
		}
	}

	@Then("^the result should be (\\d+)$")
	public void the_result_should_be(int arg1) throws Throwable {
	    assertEquals(arg1, Math.round(converted));
	}

	@Given("^I have a fresh quotation table$")
	public void I_have_a_fresh_quotation_table() throws Throwable {
	    CurrencyConversion.INSTANCE.clearQuotations();
	}
	
	@Then("^a new quotation from \"([^\"]*)\" to \"([^\"]*)\" should be added to the table: \"([^\"]*)\"$")
	public void a_new_quotation_from_to_should_be_added_to_the_table(String arg1, String arg2, String arg3) throws Throwable {
		assertEquals(Double.parseDouble(arg3), CurrencyConversion.INSTANCE.getQuotation(arg1, arg2), 0.001d);
	}	

	@Then("^I should receive an error of conversion$")
	public void I_should_receive_an_error_of_conversion() throws Throwable {
	    assertTrue(ex != null && ex instanceof CurrencyConversionException);
	}
	
}
