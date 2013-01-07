package com.guidetogalaxy.merchanteer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

/**
 * Responsible for reading the merchant's notes, interpreting sentences and questions,
 * converting among number formats, currencies and also answering pertinent questions.
 * 
 * @author flávio coutinho
 *
 */
public class NotesProcessor {

	private final InputStream input;
	private final List<Tuple<NotesLine, String>> questions;
	private final List<Tuple<String, String>> skippedLines;
	private static final Logger LOGGER = Logger.getLogger(NotesProcessor.class.getName());

	/**
	 * Creates a NotesProcessor that is bound to an input that can read text in the format
	 * of the notes.
	 * 
	 * @param input an InputStream from a source input containing text from the merchant's notes. 
	 */
	public NotesProcessor(InputStream input) {
		this.input = input;
		this.questions = new ArrayList<>();
		this.skippedLines = new ArrayList<>();
	}
	
	/**
	 * Processes the notes contained in the input that this NotesProcessor is bound.
	 * The output is written on the provided output stream.
	 * 
	 * @param out OutputStream in which the output of reading the notes is written to.
	 * @throws IOException In case an IO error happens when writing the output or reading the input.
	 */
	public void process(OutputStream out) throws IOException {
		this.questions.clear();

		try (Scanner scanner = new Scanner(input)) {
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
				answerQuestion(t.getX(), t.getY(), out);
			} catch (CurrencyConversionException|MalformedNumberException ex) {
				skippedLines.add(new Tuple<>(t.getY(), ex.getMessage()));
			}
		}
		
		LOGGER.info(String.format("Lines skipped: %d", skippedLines.size()));
		// TODO display information about lines skipped on the output (besides log)
	}
	
	/**
	 * Processes one line of the input. This function just interprets the sentence from the notes.
	 * If it is a question, it saves it to be answered later, when all the lines have been read and interpreted,
	 * so the order of the lines on the notes do not need to be guaranteed with questions only coming after
	 * the other sentences.
	 * 
	 * @param line The line of text of the input being processed.
	 * @throws NotesFormatException In case an error happens when interpreting the line.
	 * @throws MalformedNumberException If a number in this line is not in a recognized format (Roman, Arabic, Vogon).
	 */
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
						double ratio = numberOfSecondCurrency / (float)materialQuantity.getIntValue();
						
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
	
	/**
	 * Answers a question that has been pre-processed.
	 * 
	 * @param type The type of the question.
	 * @param question The question statement.
	 * @param out The output to write to.
	 * @throws IOException In case there's an IO error when writing to the output.
	 * @throws CurrencyConversionException If it was not possible to convert between the currencies provided by the question statement.
	 * @throws MalformedNumberException If one or more of the number on the question statement was/were not in a recognized format (Roman, Arabic, Vogon).
	 */
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
		
		out.write(response.getBytes());
		out.write('\n');
	}
	
	/**
	 * The enumeration of recognized line formats for the merchant's notes to be processed.
	 * 
	 * @author flávio coutinho
	 *
	 */
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
		
		/**
		 * Returns the type of the notes line (question, definition, unknown etc.).
		 *  
		 * @return the type of the notes line (question, definition, unknown etc.).
		 */
		NotesLineType getType() {
			return type;
		}
		
		/**
		 * Returns the regex pattern that represents this notes line format to be used to parse and extract
		 * information from actual notes lines
		 * .
		 * @return the regex pattern that represents this notes line format.
		 */
		Pattern getPattern() {
			return pattern;
		}
		
		/**
		 * Returns the response text expected for this line, properly filled with the correct
		 * values (numbers, currency names etc.).
		 * 
		 * @param responseParams an array with objects to fill in the response text.
		 * @return the response text filled with the correct values.
		 */
		String getResponse(Object... responseParams) {
			return String.format(response.formattedString, responseParams);
		}
		
		/**
		 * The responses expected from the program after the lines have been read and processed.
		 *  
		 * @author flávio coutinho
		 *
		 */
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
	
	/**
	 * An enumeration of the types of notes lines expected from the merchant's notes.
	 * 
	 * @author flávio coutinho
	 *
	 */
	private enum NotesLineType {
		DEFINITION,
		QUESTION,
		UNKNOWN;
	}
	

}
