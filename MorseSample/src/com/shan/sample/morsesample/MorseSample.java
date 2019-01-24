package com.shan.sample.morsesample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MorseSample {
	
	private static final String MORSE[] = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..",
            "--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..",
            ".-.-.-","--..--","..--..","-..-.",".--.-.",
            ".----","..---","...--","....-",".....","-....","--...","---..","----.","-----"
            };
	
	private static final String[] ALPHABET = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",".","\\,","\\?","\\/","\\@","1","2","3","4","5","6","7","8","9","0"};
	
	public static void main(String[] args) throws Exception
    {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input file location:");
        String inputPath = sc.next();
        
        System.out.println("Enter output file location:");
        String outputPath = sc.next();
        
        System.out.println( "conversion to English or Morse? Enter 'morse' for Morse Code and 'english' for English" );

        String selection = sc.next();

        if( selection.equalsIgnoreCase("morse") )
        	morseEncoding(inputPath, outputPath);
        else if( selection.equalsIgnoreCase("english") )
        	morseDecoding(inputPath, outputPath);
        else    
            System.out.println("Your entry is invalid");
        
        sc.close();
        
    }

    public static void morseEncoding(String inputPath, String outputPath) throws Exception
    {
        
    	System.out.println("File read from this location = " + inputPath);
    	
    	String line;
		FileReader f1 = new FileReader(new File(inputPath));
		BufferedReader read = new BufferedReader(f1);
		List<String> alphabets = Arrays.asList(ALPHABET);
		
		StringBuffer sb = new StringBuffer();
		
		while((line = read.readLine()) != null) {
			// Let's assume that every character is separated from each other with a space
			String[] words = line.split(" ");
			if (words.length > 0) {  int wl = 0;
				for (String word : words) {
					int i = 0;
					while (i < word.length()) {
						String c = String.valueOf(word.charAt(i)).toUpperCase();
						if (alphabets.contains(c)) {
							sb.append(MORSE[alphabets.indexOf(c)] + " ");	
						} else {
			            	throw new Exception ("invalid character to MORSE" + c );
			            }
						i++;
					}
					wl++;
					if(!(wl == words.length))
						sb.append("/ ");
				}
			} 
		}
	
		Files.write(Paths.get(outputPath), sb.toString().getBytes());
		System.out.println("File saved to this location = " + outputPath);
		read.close();
    }

    public static void morseDecoding(String inputPath, String outputPath) throws Exception
    {
        System.out.println("File read from this location = " + inputPath);
    	
    	String line;
		FileReader fr = new FileReader(new File(inputPath));
		BufferedReader read = new BufferedReader(fr);
		
		StringBuffer sb = new StringBuffer();	
		
		while((line = read.readLine()) != null) {
			if (line.contains("/")) {
				String[] words = line.split("/ ");
				if (words.length > 0) {
					for (String w : words) {
						String[] chars = w.split(" ");
						handlingWordsinDecodingProcess(sb, chars);
						sb.append(" ");
					}
				}
			} else {
				String[] chars = line.split(" ");
				handlingWordsinDecodingProcess(sb, chars);
			}
		}
		
		Files.write(Paths.get(outputPath), sb.toString().getBytes());
		System.out.println("File saved to this location = " + outputPath);
        read.close();
    }
    
    
    private static void handlingWordsinDecodingProcess(StringBuffer sb, String[] chars) throws Exception {
    	for (String morseCode : chars) {		
    		List<String> morse = Arrays.asList(MORSE);	
    		if(morse.contains(morseCode)) {
    			sb.append(ALPHABET[morse.indexOf(morseCode)]);
    		} else {
    			throw new Exception ("there is a non valid morse code " + morseCode );
    		}	
		}
    } 

}
