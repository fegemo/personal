package com.guidetogalaxy.merchanteer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;


public class App 
{
    public static void main(String[] args)
    {
    	InputStream input = null;
    	NotesProcessor processor = null;
    	boolean shouldLoadFilefromArgs = args.length > 0;
    	

    	try {
        	if (shouldLoadFilefromArgs) {
        		input = new FileInputStream(new File(args[0]));
        	} else {
        		input = App.class.getClassLoader().getResourceAsStream("provided_test_input.txt");
        	}

        	processor = new NotesProcessor(input);
        	processor.process(System.out);
			
        } catch (FileNotFoundException|NoSuchFileException e) {
			System.out.println(String.format("Unable to find the provided file: %s.", args[0]));
			System.out.println(String.format("Runing from %s.\n", new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath())));
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
