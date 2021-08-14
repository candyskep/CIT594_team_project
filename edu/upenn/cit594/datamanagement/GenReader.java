package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenReader implements MyReader {
	/* Public Static method to read data from a .csv or a .txt
	 * Takes the filename, an array of headers, and a token representing how the data is split. 
	 * Manually splits the data using a for loop. 
	 * If this is passed a null value for headers, we assume that the first line of the file contains headers.
	 * Returns a DataFrame containing the data from the file.
	 */
	public DataFrame readData(String filename, String[] headers, char token) {
		// Initialize stuff
		FileReader myFile;
		BufferedReader bufferedFile;
		String currentLine = null;
		String[] currRow = null;
		DataFrame df;
		
		try {
			// Open the file
			myFile = new FileReader(filename);
			
			// Wrap file in BufferedReader
			bufferedFile = new BufferedReader(myFile);
			
			// Get the first line from the bufferedReader 
			currentLine = bufferedFile.readLine();
			
			// If headers are null, split the first line as headers
			if(headers == null) {
				headers = manualParse(currentLine, token);
				currentLine = bufferedFile.readLine();
			}
			
			// Initialize DataFrame with new headers
			df = new DataFrame(headers);
			
			// Put lines from file into wordList	
			while(currentLine != null) {
				// Separate currentLine into comma-separated values
				currRow = manualParse(currentLine, token);

				// Add current row to dataframe
				df.addRow(currRow);
				
				// Get next line	
				currentLine = bufferedFile.readLine();
			}
			
			// Close file
			bufferedFile.close();
		} 
		
		// If an exception is thrown, print error and return myList
		catch (FileNotFoundException e) {
			System.out.println("File was not found!");
			return null;
		}
		
		catch (IOException e) {
			System.out.println("IOException!");
			return null;
		}
		
		return df;
	}
	
	/* Manually parses the entire string using a char token. 
	 * Uses a count of quotation marks to ensure tokens within quote marks are not processed.
	 * Returns a String[] of parsed strings. 
	 */
	private static String[] manualParse(String str, char token) {
		// Storage variables
		ArrayList<String> strArray = new ArrayList<String>();
		String currCell = "";
		int quoteCount = 0;
		char[] wholeStr = str.toCharArray();
		
		// Iterate across the wholeStr
		for(char x : wholeStr) {
			// If we encounter a valid separator, add current cell and reset. 
			if(x == token && quoteCount%2 == 0) {
				if(currCell.length() == 0) {
					currCell = null;
				}
				strArray.add(currCell);
				currCell = "";
			}
			// If we encounter quotation marks, we increment quote count
			else if(x == '\"') {
				quoteCount++;
			}
			// Else we just add the char into the current cell.
			else {
				currCell += x;
			}
		}
		
		// Add the last cell
		if(currCell.length() > 0) {
			strArray.add(currCell);
		} else {
			strArray.add(null);
		}

		return strArray.toArray(new String[0]);
	}
	
}
