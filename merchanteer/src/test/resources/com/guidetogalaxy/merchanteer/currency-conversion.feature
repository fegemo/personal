Feature: Convert among currencies considering different quotations
	In order to trade my different materials with people from other planets that use different currencies
	As a greedy intergalactic merchant
	I want to convert numbers in a currency to others
	
	Background:
		Given I have a fresh quotation table
		And a currency named "Iron"
		And a currency named "Silver"
		And a currency named "Gold"
		And a currency named "Credits"
	
	Scenario: valid, direct conversions
		Given one "Iron" is worth 30 "Credits"
		And one "Silver" is worth 35 "Credits"
		And one "Gold" is worth 100 "Credits"
		When I convert 80 "Iron" to "Credits"
		Then the result should be 2400
		When I convert 80 "Silver" to "Credits"
		Then the result should be 2800
		When I convert 80 "Gold" to "Credits"
		Then the result should be 8000
		
#	Scenario: valid, transitive conversions
#		Given one "Iron" is worth 10 "Credits"
#		And one "Gold" is worth 100 "Credits"
#		And one "Silver" is worth 2 "Iron"
#		And one "Gold" is worth 10 "Iron"
#		When I convert 30 "Gold" to "Silver"
#		Then the result should be 150
#		And a new quotation from "Gold" to "Silver" should be added to the table: "5"
	
	Scenario: invalid conversions
		Given one "Iron" is worth 10 "Credits"
		And one "Gold" is worth 100 "Credits"
		And one "Gold" is worth 10 "Iron"
		When I convert 30 "Gold" to "Silver"
		Then I should receive an error of conversion
	