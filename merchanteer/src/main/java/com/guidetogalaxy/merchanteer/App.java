package com.guidetogalaxy.merchanteer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

/**
 * Entry point for the Merchanteer app.
 * 
 * @author flávio coutinho
 *
 */
public class App 
{
	/**
	 * On start, if the program was run with an argument, it is considered to be a path
	 * to a file containing merchant's notes. If there's no argument, the program is
	 * run with a simple sample file that was provided as a test resource.
	 * 
	 * @param args the first parameter can be an absolute path to a file or nothing (to
	 * run the sample file).
	 */
    public static void main(String[] args)
    {
    	InputStream input = null;
    	NotesProcessor processor = null;
    	boolean shouldLoadFilefromArgs = args.length > 0;
    	

    	try {
        	if (shouldLoadFilefromArgs) {
        		input = new FileInputStream(new File(args[0]));
        	} else {
        		input = App.class.getClassLoader().getResourceAsStream("provided_test.input.txt");
        	}

        	processor = new NotesProcessor(input);
        	
        	// runs everything
        	processor.process(System.out);
			
        } catch (FileNotFoundException|NoSuchFileException e) {
			System.out.println(String.format("Unable to find the provided file: %s.", args[0]));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
