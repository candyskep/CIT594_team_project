package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.datamanagement.popReader;

public class Population {
	protected popReader pop_reader;
	public Population(popReader p) {
		pop_reader=p;
	}
	
	public int get_total_population() {
		List<List<Object>> populationlist=pop_reader.getpopulation();
		int total_population=0;
		for (List<Object> zip_pop:populationlist) {
			String zip=(String) zip_pop.get(0);
			if(zip_pop.get(1)!=null) {
				int pop=(Integer) zip_pop.get(1);
				total_population+=pop;
			}
		}
		
		return total_population;
	}
}
