package edu.upenn.cit594.processor;

import java.util.ArrayList;
import edu.upenn.cit594.datamanagement.DataFileReader;


public class Population {
	
	public static int get_total_population(DataFileReader data) {
		ArrayList<Object> population_column=data.getData("population", "population");
		int total_population=0;
		for (Object zip_pop:population_column) {
			if(zip_pop!=null) {
				total_population+=(Integer) zip_pop;
			}
		}
		return total_population;
	}
	
	
	public static int getZipPopulation(DataFileReader data, String zipcode) {
		int zip_population=-1;
		ArrayList<Object> population_column=data.getData("population", "population");
		ArrayList<Object> zip_column=data.getData("population", "zip_code");
		for(int i=0;i<zip_column.size();i++) {
			String zip=(String)zip_column.get(i);
			if(zip.equals(zipcode)&&population_column.get(i)!=null) {
				zip_population= (int)population_column.get(i);
			}
		}		
		return zip_population;
	}
	
	
	
	
	
}
