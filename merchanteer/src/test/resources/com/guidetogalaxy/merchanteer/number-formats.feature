Feature: Roman number evaluation
	In order to be able to trade goods using intergalactic number notations
	As a greedy intergalactic merchant
	I want to properly convert numbers in between roman and arabic formats
	
	Scenario Outline: Recognize well/malformed roman number
		Given I have a <string of letters>
		When I parse it as a roman number
		Then it <should or should not> work because of its form
			And I get the number <roman> in roman
		
		Examples:
			| string of letters	| should or should not	| roman		|
			| MCMIII			| should				| MCMIII	|
			| mcmiii			| should				| MCMIII	|
			| 235423			| should not			| 			|
			| MMMM				| should not			|			|
			| IIII				| should not			|			|
			| IVX				| should not			|			|
			| IMM				| should not			|			|
			| MMMCM				| should				| MMMCM		|
			| MMMIM				| should not			|			|
			| DD				| should not			|			|
			| LL				| should not			|			|
			| VV				| should not			|			|
			| IV				| should				| IV		|
			| IX				| should				| IX		|
			| IL				| should not			|			|
			| IC				| should not			|			|
			| ID				| should not			|			|
			| IM				| should not			|			|
			| XI				| should				| XI		|
			| XV				| should				| XV		|
			| XL				| should				| XL		|
			| XC				| should				| XC		|
			| XD				| should not			|			|
			| XM				| should not			|			|
			| CI				| should				| CI		|
			| CV				| should				| CV		|
			| CX				| should				| CX		|
			| CL				| should				| CL		|
			| CD				| should				| CD		|
			| CM				| should				| CM		|
			| DI				| should				| DI  		|
			| DV				| should				| DV  		|
			| DX				| should				| DX   		|
			| DL				| should				| DL   		|
			| DC				| should				| DC   		|
			| DM				| should not			|   		|


	Scenario Outline: Convert roman to arabic
		Given I have the number <roman> in roman
		When I convert it to arabic
		Then the arabic result should be <arabic>
		
		Examples:
			| roman			| arabic 	|
			| MMIV			| 2004		|
			| MM			| 2000		|
			| MCMXLIV		| 1944		|
			| MCMIII		| 1903		|
			| I				| 1			|
			| MMMCMXCIX		| 3999		|
