package com.guidetogalaxy.merchanteer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.guidetogalaxy.merchanteer.currency.CurrencyConversion;
import com.guidetogalaxy.merchanteer.currency.CurrencyConversionException;
import com.guidetogalaxy.merchanteer.numberFormat.MalformedNumberException;
import com.guidetogalaxy.merchanteer.numberFormat.RomanSymbol;
import com.guidetogalaxy.merchanteer.numberFormat.VogonDialect;
import com.guidetogalaxy.merchanteer.numberFormat.VogonNumber;
import com.guidetogalaxy.merchanteer.util.Tuple;

public class NotesProcessor {

	//private final String filePathString;
	private final InputStream input;
	private final List<Tuple<NotesLine, String>> questions;
	private final List<Tuple<String, String>> skippedLines;
	private static final Logger LOGGER = Logger.getLogger(NotesProcessor.class.getName());

	public NotesProcessor(InputStream input) {
		this.input = input;
		this.questions = new ArrayList<>();
		this.skippedLines = new ArrayList<>();
	}
	
	public void process(OutputStream out) throws IOException {
		this.questions.clear();

		try (Scanner scanner = new Scanner(input, StandardCharsets.UTF_8.name())) {
			String line = null;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				
				try {
					processLine(line);
				} catch (NotesFormatException | MalformedNumberException ex) {
					skippedLines.add(new Tuple<>(line, ex.getMessage()));
				}
			}
		}
		
		// traverses the list of questions and give a proper answer to them
		for (Tuple<NotesLine, String> t : questions) {
			try {
				answerQuestion(t.x, t.y, out);
			} catch (CurrencyConversionException|MalformedNumberException ex) {
				skippedLines.add(new Tuple<>(t.y, ex.getMessage()));
			}
		}
		
	}
	
	private void processLine(String line) throws NotesFormatException, MalformedNumberException {
		LOGGER.info(String.format("Reading line: %s", line));

		// tries to match a string pattern
		NotesLine[] linePatterns = NotesLine.values();
		
		for (NotesLine lp : linePatterns) {
			Matcher m = lp.getPattern().matcher(line);
			
			if (m.matches()) {
				LOGGER.info(String.format("\tThe pattern %s matched!", lp));
				
				switch (lp.getType()) {
				case DEFINITION:
					// extract params based on the type of the definition
					switch (lp) {
					case VOGON_ROMAN_MAPPING:
						// create a new vogon number and map it to a roman symbol
						VogonDialect.INSTANCE.addVogonToRomanMapping(m.group(1), RomanSymbol.fromCharacter(m.group(2).charAt(0)));
						
						break;
						
					case VOGON_CURRENCY_QUOTATION:
						// parses to check if all the vogon words have been declared
						//String[] regexResults = line.split(lp.regexString);
						String[] vogonNumbers = m.group(1).split("\\s");
						String firstCurrency = m.group(2);
						Integer numberOfSecondCurrency = Integer.parseInt(m.group(3));
						String secondCurrency = m.group(4);
						for (String vn : vogonNumbers) {
							if (!VogonDialect.INSTANCE.containsVogonWord(vn)) {
								throw new NotesFormatException(String.format("A vogon word was used (%s), but was not declared before.", vn));
							}
						}
						VogonNumber materialQuantity = VogonNumber.fromString(m.group(1));
						
												
						// calculates how many Credits (the money currency) is one Silver|Gold|Iron (material currency) worth
						double ratio = numberOfSecondCurrency / materialQuantity.getIntValue();
						
						// adds an entry to the quotations table
						CurrencyConversion.INSTANCE.addQuotation(firstCurrency, secondCurrency, ratio);
						
						break;
					}
					break;
					
				case QUESTION:
					// add to a list of questions that must be answered after the notes file is read
					// the processing of questions (to find answers) is delayed to until the file is fully read
					// so that the program is robust enough to properly consider out of order lines (aka side notes)
					questions.add(new Tuple<>(lp, line));
					LOGGER.info("Added the line as a question.");
					break;
					
				case UNKNOWN:
					// just ignore, as vogon poetry suck =)
					// on a second thought, let's print something in response
					questions.add(new Tuple<>(lp, line));
					LOGGER.info("Added the line as an unkown phrase.");
					break;
				}
				
				break;
			}
		}
	}
	
	private void answerQuestion(NotesLine type, String question, OutputStream out) throws IOException, CurrencyConversionException, MalformedNumberException {
		LOGGER.info(String.format("Answering question: %s of type %s.", question, type));
		Matcher m = type.getPattern().matcher(question);
		m.matches();
		String response = "";
		
		switch (type) {
		case HOW_MUCH_VOGON_ARABIC_VALUE:
			// converts a vogon number to arabic and print the response
			VogonNumber number = VogonNumber.fromString(m.group(1));
			response = type.getResponse(m.group(1).trim(), number.getIntValue());
			LOGGER.info(String.format("Responding: %s", response));

			break;

		case HOW_MANY_VOGON_CREDITS_VALUE:
			// uses the currency conversion table to return the number of one currency in another one
			// then, prints the response
			// ps: the original currency is represented with a vogon number
			String covertedCurrency = m.group(1);
			String originalCurrency = m.group(3);
			double quotationOriginalToConverted = CurrencyConversion.INSTANCE.getQuotation(originalCurrency, covertedCurrency);

			VogonNumber valueInOriginalCurrency = VogonNumber.fromString(m.group(2));
			long valueInConvertedCurrency = Math.round(valueInOriginalCurrency.getIntValue() * quotationOriginalToConverted);

			response = type.getResponse(m.group(2).trim(), m.group(3).trim(), valueInConvertedCurrency);
			LOGGER.info(String.format("Responding: %s", response));
			
			break;
			
		case CRAZY_LINE_OR_VOGON_POETRY:
			// prints a response to a vogon poem
			response = type.getResponse();
			LOGGER.info(String.format("Responding: %s", response));
			break;
		}
		
		out.write(response.getBytes(StandardCharsets.UTF_8));
		out.write('\n');
	}
	
	
	private enum NotesLine {
		VOGON_ROMAN_MAPPING				(NotesLineType.DEFINITION, 	"^([a-z]+) is ([I|V|X|L|C|D|M])$",							ResponseLine.NO_RESPONSE),
		VOGON_CURRENCY_QUOTATION		(NotesLineType.DEFINITION, 	"^((?:[a-z]+ )+)([A-Z]\\w+) is (\\d+) ([A-Z]\\w+)$",		ResponseLine.NO_RESPONSE),
		HOW_MUCH_VOGON_ARABIC_VALUE		(NotesLineType.QUESTION, 	"^how much is ((?:\\w+ )+)\\?$",							ResponseLine.ANSWER_VOGON_ARABIC_VALUE),
		HOW_MANY_VOGON_CREDITS_VALUE	(NotesLineType.QUESTION, 	"^how many ([A-Z]\\w+) is ((?:\\w+ )+)([A-Z]\\w+) \\?$",	ResponseLine.ANSWER_VOGON_CREDITS_VALUE),
		CRAZY_LINE_OR_VOGON_POETRY		(NotesLineType.UNKNOWN, 	".+",														ResponseLine.RESPONSE_UNKNOWN_CRAZY_LINE_OR_VOGON_POEM);
		
		private final NotesLineType type;
		private final Pattern pattern;
		private final ResponseLine response;
		
		NotesLine(NotesLineType type, String regexString,  ResponseLine response) {
			this.type = type; 
			this.pattern = Pattern.compile(regexString);
			this.response = response;
		}
		
		NotesLineType getType() {
			return type;
		}
		
		Pattern getPattern() {
			return pattern;
		}
		
		String getResponse(Object... responseParams) {
			return String.format(response.formattedString, responseParams);
		}
		
		enum ResponseLine {
			NO_RESPONSE(""),
			ANSWER_VOGON_ARABIC_VALUE("%s is %d"),
			ANSWER_VOGON_CREDITS_VALUE("%s %s is %d Credits"),
			RESPONSE_UNKNOWN_CRAZY_LINE_OR_VOGON_POEM("I have no idea what you are talking about");
			
			final String formattedString;
			
			private ResponseLine(String formattedString) {
				this.formattedString = formattedString;
			}
		}
	}
	
	enum NotesLineType {
		DEFINITION,
		QUESTION,
		UNKNOWN;
	}
	

}
