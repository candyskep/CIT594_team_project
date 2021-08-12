package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;

public class DataFrame {
	// Data storage and variables
	ArrayList<String> headers = new ArrayList<String>();
	ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
	
	
	
	int numCols = 0;
	int numRows = 0;
	
	// Constructor
	public DataFrame(String[] newHeaders){
		for(String i : newHeaders) {
			// Set Headers
			headers.add(i);
			
			// Initialize ArrayList of ArrayLists
			data.add(new ArrayList<Object>());
			
			// Set number of columns
			numCols++;
		}
	}
	
	
	public void setData(ArrayList<ArrayList<Object>> data) {
		this.data = data;
	}

	
	
	/* Public method to add a row to the DataFrame
	 * Assumes the array is sorted in the same order as headers. 
	 */
	public void addRow(Object[] newRow){
		// Null Object
		if(newRow == null) {
			return;
		}
		
		// Iterate and add objects
		int rowLength = newRow.length;
		for(int i = 0; i < numCols; i++) {
			if(i < rowLength) {
				ArrayList<Object> currentArray = data.get(i);
				currentArray.add(newRow[i]);
			}
			else {
				data.get(i).add(null);
			}
		}
		numRows++;
	}
	
	
	/* Public method to get column by header
	 * Returns an ArrayList corresponding to that header
	 * Returns null if header does not exist.  
	 */
	public ArrayList<Object> getCol(String header){
		// If header is null
		if(header == null) {
			return null;
		}
		
		// Check index and return appropriately
		int index = headers.indexOf(header);
		//System.out.println("|"+headers.get(1)+"|       input=|"+header+"|");
		if(index < 0) {
			return null;
		}
		else {
			return data.get(index);
		}
	}
	
	/* Public method to get the number of rows. 
	 * Returns an integer, numRows.  
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/* Checks if DataFrame has a header.
	 * Returns a boolean indicating whether the header does nor does not exist. 
	 */
	public boolean contains(String str) {
		// Check if str is null
		if(str == null) {
			return false;
		}
		return headers.contains(str);
	}
	
	
	/* Prints the DataFrame to system.out.
	 * Takes a boolean withHeader to indicate whether to print column and row counts and headers.
	 * Does not return anything. 
	 */
	public void print(boolean withHeader) {
		// Print Headers
		if(withHeader) {
			System.out.printf("%s %s    ","Cols:", numCols);
			System.out.printf("%s %s \n","Rows:", numRows);

			for(int j = 0; j < numCols; j++) {
				System.out.printf("%s\t", headers.get(j));
			}
			
			System.out.print("\n");
		}
		// Print Data
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				ArrayList<Object> currentArray = data.get(j);
				System.out.printf("%s\t", currentArray.get(i));
			}
			System.out.print("\n");
		}
	}
	
	/* Prints the DataFrame to a String[] 
	 * Takes a boolean withHeader to indicate whether to print column and row counts and headers.
	 * Returns the String[] 
	 */
	public String[] printStr(boolean withHeader) {
		// Initialize storage variables
		String[] df; 
		String currString;
		int currLine = 0;
		
		if(withHeader) {
			df = new String[numRows + 3];
		} else {
			df = new String[numRows];
		}
		
		// If withHeader is true
		if(withHeader) {
			// Print cols and rows
			currString = String.format("%s%d\t","Cols: ", numCols) + String.format("%s%d\n","Rows: ", numRows);
			df[currLine] = currString;
			currLine++;
			currString = "";
			
			// Print headers
			for(int j = 0; j < numCols; j++) {
				currString += String.format("%s\t", headers.get(j));
			}
			df[currLine] = currString;
			currLine++;
		}
		
		// Print Data
		for(int i = 0; i < numRows; i++) {
			currString = "";
			for(int j = 0; j < numCols; j++) {
				ArrayList<Object> currentArray = data.get(j);
				currString += String.format("%s\t", currentArray.get(i));
			}
			df[currLine] = currString;
			currLine++;
		}
		
		return df;
	}
	

}
