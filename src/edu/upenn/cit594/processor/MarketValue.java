package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.DataFileReader;

public class MarketValue extends Property {


	public static double getAvgMarketValue(DataFileReader data, String zipcode) {
	       return getAvg(data,"market_value",zipcode);
		}
}
