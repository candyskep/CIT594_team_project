package edu.upenn.cit594.processor;

import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.DataFile;

public class TotalPopulation {
	DataFile data;
	ArrayList<String> population;
	ArrayList<String> zip_code;
	
	public TotalPopulation(DataFile data) {
		this.data = data;
		this.population = data.getData("population", "population");
		this.zip_code = data.getData("population", "zip_code");
	}
	
	public String getPopulation() {
		Integer pop = 0;
		for(String str : population) {
			Integer currPop = Integer.valueOf(str);
			pop += currPop;
		}
		return pop.toString();
	}
	
	
	/**
	 * if input zipcode is not foudn or the populication data of the given zipcode is null, return -1
	 * @param zipcode
	 * @return
	 */
	public int getZipPopulation(String zipcode) {
		int zip_population=-1;
		ArrayList<String> population_column=this.population;
		ArrayList<String> zip_column=this.zip_code;
		if(!zip_column.contains(zipcode)) {
			return -1;
		}
		
		
		for(int i=0;i<zip_column.size();i++) {
			String zip=zip_column.get(i);
			if(zip.equals(zipcode)&&population_column.get(i)!=null) {
				zip_population=Integer.valueOf(population_column.get(i));
			}
		}		
		return zip_population;
	}

}
