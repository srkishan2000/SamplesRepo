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
	
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,?/@1234567890";
	
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
		
		StringBuffer sb = new StringBuffer();
		
		while((line = read.readLine()) != null) {
			// Let's assume that every character is separated from each other with a space
			String[] words = line.split(" ");
			if (words.length > 0) { int findLastWord = 0;
				for (String w : words) {
					int i = 0;
					while (i < w.length()) {
						for( int j = 0; j < ALPHABET.length(); j++ )
			            {	
			                if (ALPHABET.charAt(j) == w.charAt(i)) 
			                {	
			                    sb.append(MORSE[j] +" ");
			                    break;
			                }
			                if (j == (ALPHABET.length() - 1)) {
			                	
			                	throw new Exception ("It is an invalid character " + w + " to Morse");
			                }
			            }
						i++;
						if (!(findLastWord < w.length()))
							sb.append(" ");
						findLastWord++;
					}
					if (findLastWord < w.length())
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
				String[] words = line.split("/");
				if (words.length > 0) {
					for (String w : words) {
						String[] chars = w.split(" ");
						handlingWordsinDecodingProcess(sb, chars);
						System.out.print(" ");
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
    		
    		if ((!morse.contains(morseCode)) && (morseCode != "") ) {
    			throw new Exception ("there is a non valid morse code " + morseCode );
            }
    		
    		for( int j = 0; j < MORSE.length; j++ ) {
				if ( MORSE[j].equals(morseCode)) {
                    sb.append(ALPHABET.charAt(j));
                	break;
                }

            } 
		}
    } 

}
