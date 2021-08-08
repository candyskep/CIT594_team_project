package edu.upenn.cit594.processor;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.propertyReader;

public class AverageProperty {
	propertyReader propertyReader;
	String zipcode;
	ArrayList<Object> zipcolumn;
	ArrayList<Object> market_value_column;
	ArrayList<Object> livable_area_column;
	public AverageProperty(propertyReader propertyreader, String zipcode) {
		this.propertyReader=propertyreader;
		this.zipcode=zipcode;
		this.zipcolumn=propertyReader.getZipcode();
		this.market_value_column=propertyReader.getMarketvalue();
		this.livable_area_column=propertyReader.getLivablearea();
	}
	
	public double getAvg(ArrayList<Object> zipcolumn,ArrayList<Object> targetcolumn,String zipcode) {
		double avg=0;
		int validcount=0;
		double validTotal=0;
		double temp=0;
		for (int i=0;i<zipcolumn.size();i++) {
			if(zipcolumn.get(i)!=null&&targetcolumn.get(i)!=null&&zipcolumn.get(i).equals(zipcode)) {
				try{
					temp=Double.valueOf((String) targetcolumn.get(i));
				}catch (NumberFormatException e) {
					continue;
				}
				validTotal+=temp;
				validcount+=1;
			}
		}
		avg=validTotal/validcount;
		BigDecimal bdDown=new BigDecimal(avg).setScale(2,RoundingMode.DOWN);
		avg=bdDown.doubleValue();
		return avg;
	}

}
