package edu.upenn.cit594.processor;

import java.util.ArrayList;


import edu.upenn.cit594.datamanagement.DataFrame;
import edu.upenn.cit594.datamanagement.txtReader;


public class Population {
	protected DataFrame populationData;
	public Population(String filename) {
		this.populationData=txtReader.readPopulation(filename);
	}
	
	public int get_total_population() {
		ArrayList<Object> populationColumn=populationData.getCol("population");
		int total_population=0;
		for (Object zip_pop:populationColumn) {
			if(zip_pop!=null) {
				total_population+=(Integer) zip_pop;
			}
		}
		
		return total_population;
	}
}
