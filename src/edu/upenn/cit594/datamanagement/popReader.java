package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class popReader {
	protected String filename;
	public popReader(String filename) {
		this.filename=filename;
	}
	
	public List<List<Object>> getpopulation() {
		List<List<Object>> populationlist=new ArrayList<>();
		Object population;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Pattern p1 = Pattern.compile("(\\d+)\\s(.*)");
		        Matcher m1 = p1.matcher(line);    
		        if (m1.find( )) {
		           String zipcode=m1.group(1);
		           String str_pop=m1.group(2);
		           List<Object> zip_pop=new ArrayList<>();
		           zip_pop.add(zipcode);
		           try{
		        	   population=Integer.parseInt(str_pop);
		           } catch(NumberFormatException e) {
		        	   population=null;           
		           }
		           zip_pop.add(population);
		           populationlist.add(zip_pop);
		        }
		    } 
		}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
		    
		return populationlist;
		
	}

}
