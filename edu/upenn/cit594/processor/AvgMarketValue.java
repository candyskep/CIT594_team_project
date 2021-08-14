package edu.upenn.cit594.processor;


import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.DataFile;

public class AvgMarketValue implements AvgStrategy {
	
	@Override
	public ArrayList<String> getData(DataFile data) {
		return data.getData("property", "market_value");
	}
	
	
}
