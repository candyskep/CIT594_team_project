package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.DataFileReader;

public class LivableArea extends Property {

	public static double getAvgLivableArea(DataFileReader data, String zipcode) {
       return getAvg(data,"total_livable_area",zipcode);
	}

}
