package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class csvReader {
	/* Public Static method to read data from a .csv or a .txt
	 * Takes the filename, an array of headers, and a token representing how the data is split. 
	 * If this is passed a null value for headers, we assume that the first line of the file contains headers.
	 * Returns a DataFrame containing the data from the file.
	 */
	public static DataFrame readData(String filename) {
		String line;
		int num_of_columns=0;
		BufferedReader br=null;
		String []headers=null;

		try {
		br = new BufferedReader(new FileReader(filename));
		   line=br.readLine();
		   headers=line.split(",");
		   num_of_columns=headers.length;
		}catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		    }
		
		
		
		DataFrame df=new DataFrame(headers);  
		ArrayList<ArrayList<Object>> data=new ArrayList<ArrayList<Object>>(num_of_columns);
		for(int i=0;i<num_of_columns;i++) {
		data.add(new ArrayList<Object>());
		}
		   
		   
		   try {
		while ((line = br.readLine()) != null) {
		Pattern p1 = Pattern.compile("(^||.+?)[,||\\n]");
		   Matcher m1 = p1.matcher(line);
		   int i=0;
		   while (m1.find()&&i<num_of_columns) {
		     
		    data.get(i).add(m1.group(1));
		      i++;
		   }
		}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		df.setData(data);
		
		
		return df; 
	}
}
