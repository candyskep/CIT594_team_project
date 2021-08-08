package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.propertyReader;

public abstract class AverageProperty {
	propertyReader propertyReader;
	String zipcode;
	public AverageProperty(propertyReader propertyreader, String zipcode) {
		this.propertyReader=propertyreader;
		this.zipcode=zipcode;
	}
	public abstract double getAvg();

}
