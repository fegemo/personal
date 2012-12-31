package com.guidetogalaxy.merchanteer;

import java.io.IOException;


public class App 
{
    public static void main(String[] args)
    {
        NotesProcessor processor = new NotesProcessor(args[0]);
        
        try {
			processor.process(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
