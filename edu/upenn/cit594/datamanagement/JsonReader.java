package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader implements MyReader {
	/* Public Static method to read data from a .json
	 * Takes the filename and an array of headers
	 * If headers is null, automatically generates headers from the JSON file. 
	 * Returns a DataFrame containing the data from the file.
	 */
	public DataFrame readData(String filename, String[] headers, char token) {
		// Initialize stuff
		FileReader myFile;
		BufferedReader bufferedFile;
		DataFrame df;
		ArrayList<String> currList = null;
		JSONParser parser = new JSONParser();
		String currString;
		
		try {
			// Open the file
			myFile = new FileReader(filename);
			
			// Wrap file in BufferedReader
			bufferedFile = new BufferedReader(myFile);
			
			// Read the file as a jsonArray 
			JSONArray jsonArray = (JSONArray) parser.parse(bufferedFile);
			
			// Get headers from jsonArray if headers is null
			if(headers == null) {
				Set<String> getHeaders = new HashSet<String>();
				for(int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = (JSONObject) jsonArray.get(i);
					for(Object key : json.keySet()) {
						getHeaders.add(key.toString());
					}
				}
				headers = getHeaders.toArray(new String[0]);
			}
			
			df = new DataFrame(headers);
			
			// Use an iterator to store the JSONArray
			Iterator<JSONObject> iterator = jsonArray.iterator();
			
			while(iterator.hasNext()) {
				// Get the line
				currList = new ArrayList<String>();
				JSONObject jsonObject = iterator.next();
				
				// Get each header from the line
				for(int i = 0; i < headers.length; i++) {
					if(jsonObject.get(headers[i]) != null) {
						currString = jsonObject.get(headers[i]).toString();
					}
					else {
						currString = null;
					}
					currList.add(currString);
				}
				// Add to our dataframe
				df.addRow(currList.toArray(new String[0]));
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
		
		catch (ParseException e) {
			System.out.println("ParseException!");
			return null;
		}
		return df;
	}
}
