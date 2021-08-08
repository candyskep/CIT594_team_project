package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.propertyReader;

public class AvgMarketValue extends AverageProperty {

	public AvgMarketValue(propertyReader propertyreader, String zipcode) {
		super(propertyreader, zipcode);
	}

	public double getAvgMarketValue() {
		return getAvg(this.zipcolumn,this.market_value_column,this.zipcode);
	}
}
