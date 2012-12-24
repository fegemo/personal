package com.guidetogalaxy.merchanteer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.guidetogalaxy.merchanteer.currency.CurrencyConversion;
import com.guidetogalaxy.merchanteer.numberFormat.VogonDialect;
import com.guidetogalaxy.merchanteer.numberFormat.VogonNumber;

public class NotesProcessor {

	private final String filePathString;
	private final List<String> questions;
	
	public NotesProcessor(String filePathString) {
		this.filePathString = filePathString;
		this.questions = new ArrayList<>();
	}
	
	public void process(OutputStream out) throws IOException {
		Path path = Paths.get(filePathString);
		try (Scanner scanner = new Scanner(path, StandardCharsets.UTF_8.name())) {
			String line = null;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				processLine(line, out);
			}
		}
	}
	
	private void processLine(String line, OutputStream out) {
		// tries to match a string pattern
		NotesLine[] linePatterns = NotesLine.values();
		
		for (NotesLine lp : linePatterns) {
			if (line.matches(lp.regexString)) {
				
				String[] regexResults = null;
				switch (lp.getType()) {
				case DEFINITION:
					// extract params based on the type of the definition
					switch (lp) {
					case VOGON_ROMAN_MAPPING:
						// create a new vogon number and map it to a roman symbol
						regexResults = line.split(lp.regexString);
						VogonDialect.INSTANCE.addVogonToRomanMapping(regexResults[0], RomanSymbol.fromCharacter(regexResults[1].charAt(0)));
						
						break;
						
					case VOGON_CURRENCY_QUOTATION:
						// parses to check if all the vogon words have been declared
						regexResults = line.split(lp.regexString);
						String[] vogonNumbers = Arrays.copyOfRange(regexResults, 0, regexResults.length - 2);
						String firstCurrency = regexResults[regexResults.length - 2];
						String secondCurrency = regexResults[regexResults.length - 1];
						for (String vn : vogonNumbers) {
							if (!VogonDialect.INSTANCE.containsVogonWord(vn)) {
								throw new NotesFormatException(String.format("A vogon word was used (%s), but was not declared before.", vn));
							}
						}
						//VogonNumber number = new VogonNumber(String vogonNumbers)
						
												
						// calculates the ratio
						//double ratio = ;
						
						// adds an entry to the quotations table
						//CurrencyConversion.INSTANCE.addQuotation(currencyName, otherCurrencyName, ratio);
						
						break;
					}
					break;
					
				case QUESTION:
					// add to a list of questions that must be answered after the notes file is read
					// the processing of questions (to find answers) is delayed to until the file is fully read
					// so that the program is robust enough to properly consider out of order lines (aka side notes)
					questions.add(line);
					break;
					
				case UNKNOWN:
					// just ignore, as vogon poetry suck =)
					break;
				}
			}
		}
	}
	
	
	enum NotesLine {
		VOGON_ROMAN_MAPPING				(NotesLineType.DEFINITION, 	"^(.+?) is ([I|V|X|L|C|D|M])$"),
		VOGON_CURRENCY_QUOTATION		(NotesLineType.DEFINITION, 	"^(.+? )+ (.+?) is (\\d+) (.+?)$"),
		HOW_MUCH_VOGON_ARABIC_VALUE		(NotesLineType.QUESTION, 	"^how much is (.+? )+?$"),
		HOW_MANY_VOGON_CREDITS_VALUE	(NotesLineType.QUESTION, 	"^how many (.+?) is (.+? )+?$"),
		CRAZY_LINE_OR_VOGON_POETRY		(NotesLineType.UNKNOWN, 	".+");
		
		private final NotesLineType type;
		private final String regexString;
		
		NotesLine(NotesLineType type, String regexString) {
			this.type = type; 
			this.regexString = regexString;
		}
		
		NotesLineType getType() {
			return type;
		}		
	}
	
	enum NotesLineType {
		DEFINITION,
		QUESTION,
		UNKNOWN;
	}
	
	enum ResponseLine {
		ANSWER_VOGON_ARABIC_VALUE("%s is %i"),
		ANSWER_VOGON_CREDITS_VALUE("%s %s is %i Credits"),
		REPONSE_UNKNOWN_CRAZY_LINE_OR_VOGON_POEM("I have no idea what you are talking about");
		
		final String formattedString;
		
		private ResponseLine(String formattedString) {
			this.formattedString = formattedString;
		}
	}
}
