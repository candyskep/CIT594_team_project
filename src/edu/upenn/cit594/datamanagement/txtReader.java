package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class txtReader {
	

	public static DataFrame readData(String filename,String[] headers) {
		DataFrame df=new DataFrame(headers);
		Object [] currRow=new Object[2];
		

		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Pattern p1 = Pattern.compile("^(\\d+)\\s(\\d+||)$");
		        Matcher m1 = p1.matcher(line);    
		        if (m1.find( )) {
		           currRow[0]=m1.group(1);
		           currRow[1]=m1.group(2);
		           
		           try{
		        	   currRow[1]=Integer.parseInt((String) currRow[1]);
		           } catch(NumberFormatException e) {
		        	   currRow[1]=null;           
		           }
		           df.addRow(currRow);
		        }
		    }
		    br.close();
		}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
		
		return df;
	}
}
