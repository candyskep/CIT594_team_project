package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;

import edu.upenn.cit594.logging.Logger;

public class DataFile {
	
	private DataFrame covidData;
	private DataFrame propertyData;
	private DataFrame populationData;
	private Logger l;
	private MyReader reader;
	
	private String[] populationHeaders = {"zip_code", "population"};
	
	// Constructor
	public DataFile(String covidFile, String propertyFile, String populationFile) {
		// Initialize logger
		l = Logger.getInstance();
		
		// Read data
		this.covidData = readData(covidFile, null);
		this.propertyData = readData(propertyFile, null);
		this.populationData = readData(populationFile, populationHeaders);
		
		// Check that data was read correctly; throw an exception otherwise. 
		if(covidData == null) {
			throw new IllegalArgumentException("Error: Covid File is not valid");
		}
		
		if(propertyData == null) {
			throw new IllegalArgumentException("Error: Property File is not valid");
		}
		
		if(populationData == null) {
			throw new IllegalArgumentException("Error: Population File is not valid");
		}
	}
	
	/* Method to check if the filename is .json or .csv and run the appropriate reader
	 * Takes in a String filename and a String array of headers. 
	 * Returns a DataFrame containing the data from the file.
	 */
	private DataFrame readData(String filename, String[] headers) {
		// Split the filename
		String[] fileSplit = filename.split("\\.", 2);
		
		// Check that there is an extension
		if(fileSplit.length <= 1) {
			throw new IllegalArgumentException(String.format("%s %s", "Error: Invalid File Extension:", filename));
		}
		
		// Log the filename
		l.log(filename);
		
		// Process extension
		String extension = fileSplit[1].toLowerCase();
		switch(extension) {
			case "txt":
				reader = new GenReader();
				return reader.readData(filename, headers, ' ');
			case "csv":
				reader = new GenReader();
				return reader.readData(filename, headers, ',');
			case "json":
				reader = new JsonReader();
				return reader.readData(filename, headers, ' ');
			default:
				System.out.println("Error: Invalid File Extension");
				return null;
		}
	}
	
	/* Public method to get data
	 * Takes in a String source and String col representing the dataframe and column needed. 
	 * Returns an ArrayList corresponding to that header. 
	 */

	public ArrayList<String> getData(String source, String col) {
		DataFrame df = null;
		switch(source) {
			case "covid":
				df = this.covidData;
				break;
			case "population":
				df = this.populationData;
				break;
			case "property":
				df = this.propertyData;
				break;
		}
		
		if(df == null) {
			return null;
		}
		return df.getCol(col);
	}

}
