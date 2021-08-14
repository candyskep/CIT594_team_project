package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.DataFile;

public class Average {
	private DataFile data;
	private ArrayList<String> propertyZipcode;

	
	public Average(DataFile data) {
		this.data = data;
		this.propertyZipcode = data.getData("property", "zip_code");
		
	}
	
	
	public ArrayList<String> getPropertyZipcode() {
		return propertyZipcode;
	}

    
	
	
	
	
	
	
	public String[] getAvg(String zipcode, AvgStrategy strategy) {
		Double avg = 0.0;
		int validCount = 0;
		Double validTotal = 0.0;
		Double temp = 0.0;
		
		// Strategy object gets target data
		ArrayList<String> propertyTarget = strategy.getData(this.data);

		
		// Calculate average
		for (int i = 0; i < propertyZipcode.size(); i++) {	
			if(propertyZipcode.get(i) != null) {
				String currZipcode = propertyZipcode.get(i).substring(0, 5);
				if(propertyTarget.get(i) != null && currZipcode.equals(zipcode)) {
					try{
						temp=Double.valueOf(propertyTarget.get(i));
					}catch (NumberFormatException e) {
						continue;
					}
					validTotal += temp;
					validCount += 1;
				}
			}
		}
		
		if(validTotal == 0 || validCount == 0) {
			avg = 0.0;
		} else {
			avg = validTotal/validCount;
		}
		
		// Truncate avg appropriately and return
		BigDecimal bdDown = new BigDecimal(avg).setScale(2,RoundingMode.DOWN);
		avg = bdDown.doubleValue();
		
		return new String[] {avg.toString()+"   valideCount="+String.valueOf(validCount)};
	}
}
