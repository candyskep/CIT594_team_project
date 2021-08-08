package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.propertyReader;

public class AvgLivableArea extends AverageProperty {

	public AvgLivableArea(propertyReader propertyreader, String zipcode) {
		super(propertyreader, zipcode);
	}
	
	public double getAvgLivableArea() {
		return getAvg(this.zipcolumn,this.livable_area_column,this.zipcode);
	}



}
