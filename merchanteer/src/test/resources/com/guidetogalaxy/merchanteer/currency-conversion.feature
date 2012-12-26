Feature: Convert among currencies considering different quotations
	In order to trade my different materials with people from other planets that use different currencies
	As a greedy intergalactic merchant
	I want to convert numbers in a currency to others
	
	Background:
		Given a currency named "Iron"
		And a currency named "Silver"
		And a currency named "Platinum"
		And a currency named "Credits"
	
	Scenario: valid, direct conversions
		Given one "Iron" is worth 30 "Credits"
		And one "Silver" is worth 35 "Credits"
		And one "Platinum" is worth 100 "Credits"
		When I convert 80 "Iron" to "Credits"
		Then the result should be 2400
		When I convert 80 "Silver" to "Credits"
		Then the result should be 2800
		When I convert 80 "Platinum" to "Credits"
		Then the result should be 8000
		
	Scenario: valid, indirect conversions
	
	
	Scenario: invalid conversions
	