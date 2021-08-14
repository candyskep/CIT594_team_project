package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.DataFile;

public class ResidentialMarketValue {
	DataFile data;
	ArrayList<String> propertyMarketValue;
	ArrayList<String> propertyZipcode;
	ArrayList<String> population;
	ArrayList<String> populationZipcode;
	Map<String, Double> allMarketValues = null;
	
	public ResidentialMarketValue(DataFile data) {
		this.data = data;
		this.propertyMarketValue = data.getData("property", "market_value");
		this.propertyZipcode = data.getData("property", "zip_code");
		this.population = data.getData("population", "population");
		this.populationZipcode = data.getData("population", "zip_code");
	}
	
	public String[] getResidentialMarketValuePerCapita(String arg) {
		// Run sumMarketValues if it hasn't been run yet
		if(this.allMarketValues == null) {
			this.allMarketValues = sumMarketValues();
		}
		
		Double currRMV = allMarketValues.get(arg);
		
		// If zipcode does not exist in allMarketValues
		if(currRMV == null) {
			return new String[] {"0"};
		} else {
			Integer index = populationZipcode.indexOf((Object) arg);
			
			// If zipcode does not exist population file
			if(index < 0 || population.get(index) == null) {
				return new String[] {"0"};
			}
			
			Double currPop = Double.parseDouble(population.get(index));

			if(currPop == 0) {
				return new String[] {"0"};
			} else {
				;
				return new String[] {String.format("%.4f", currRMV / currPop)};
			}
		}
	}
	
	private Map<String, Double> sumMarketValues(){
		Map<String, Double> allMarketValues = new TreeMap<String, Double>();
		for(int i = 0; i < propertyZipcode.size(); i++) {
			// Ensure no nulls
			if(propertyMarketValue.get(i) != null && propertyZipcode.get(i) != null) {
				//Parse the property zipcode and market value, and add them to allMarketValues storage variable.
				String currZipcode = propertyZipcode.get(i).substring(0, 5);
				Double currMarketValue = Double.valueOf(propertyMarketValue.get(i));
				if(allMarketValues.containsKey(currZipcode)) {
					currMarketValue += allMarketValues.get(currZipcode);
				}
				allMarketValues.put(currZipcode, currMarketValue);
			}
		}
		return allMarketValues;
	}

}
