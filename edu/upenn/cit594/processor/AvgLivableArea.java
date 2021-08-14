package edu.upenn.cit594.processor;


import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.DataFile;

public class AvgLivableArea implements AvgStrategy {
	
	@Override
	public ArrayList<String> getData(DataFile data) {
		return data.getData("property", "total_livable_area");
	}
	
	
	
	/**
	 * Given the zip, return total livable area in that zipcode, if the input zipcode is not fount return -1
	 * @param data
	 * @param zipcode
	 * @return
	 */
	public static double zipLivable(DataFile data, String zipcode) {

		Double validTotal = 0.0;
		Double temp = 0.0;
		String temps=null;
		// Strategy object gets target data
		ArrayList<String> propertyTarget = data.getData("property", "total_livable_area");
		ArrayList<String> propertyZipcode = data.getData("property", "zip_code");
		for(int i =0;i<propertyZipcode.size();i++) {
			if(propertyZipcode.get(i)==null)continue;
			temps=propertyZipcode.get(i);
			temps=propertyZipcode.get(i).substring(0, 5);
			propertyZipcode.set(i, temps);
		}
		
		
		if(!propertyZipcode.contains(zipcode)) {
			return -1;
		}
	
		// Calculate average
		for (int i = 0; i < propertyZipcode.size(); i++) {	
			if(propertyZipcode.get(i) != null) {
				String currZipcode = propertyZipcode.get(i);//String currZipcode = propertyZipcode.get(i).substring(0, 5);
				if(propertyTarget.get(i) != null && currZipcode.equals(zipcode)) {
					try{
						
						temp=Double.valueOf(propertyTarget.get(i));
		
					}catch (NumberFormatException e) {
						continue;
					}
					validTotal += temp;
					//System.out.println(validTotal);
				}
			}
		}
		return validTotal;
	}
	
}
