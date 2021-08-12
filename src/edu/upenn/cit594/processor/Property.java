package edu.upenn.cit594.processor;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.DataFileReader;

public abstract class Property {	
	public static double getAvg(DataFileReader data,String target,String zipcode) {
		ArrayList<Object> zipcolumn=data.getData("property", "zip_code");
		ArrayList<Object> targetcolumn=data.getData("property", target);
		String tempZIP=null;
		double avg=0;
		int validcount=0;
		double validTotal=0;
		double tempTarget=0;
		for (int i=0;i<zipcolumn.size();i++) {
			tempZIP=(String) zipcolumn.get(i);		
			if(zipcolumn.get(i)!="null"&&targetcolumn.get(i)!="null"&&tempZIP.equals(zipcode)) {
				try{
					tempTarget=Double.valueOf((String) targetcolumn.get(i));
					
				}catch (NumberFormatException e) {
					continue;
				}
				validTotal+=tempTarget;
				validcount+=1;
			}
		}
		avg=validTotal/validcount;
		BigDecimal bdDown=new BigDecimal(avg).setScale(2,RoundingMode.DOWN);
		avg=bdDown.doubleValue();
		return avg;
	}
}
