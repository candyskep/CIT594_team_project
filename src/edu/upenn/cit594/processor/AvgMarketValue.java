package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import edu.upenn.cit594.datamanagement.propertyReader;

public class AvgMarketValue extends AverageProperty {

	public AvgMarketValue(propertyReader propertyreader, String zipcode) {
		super(propertyreader, zipcode);
	}

	@Override
	public double getAvg() {
		ArrayList<Object> zipcolumn=this.propertyReader.getZipcode();
		ArrayList<Object> market_value_column=this.propertyReader.getMarketvalue();
		double avgMV=0;
		int validcount=0;
		double validTotal=0;
		double temp=0;
		for (int i=0;i<zipcolumn.size();i++) {
			if(zipcolumn.get(i)!=null&&market_value_column.get(i)!=null&&zipcolumn.get(i).equals(zipcode)) {
				try{
					temp=Double.valueOf((String) market_value_column.get(i));
				}catch (NumberFormatException e) {
					continue;
				}
				validTotal+=temp;
				validcount+=1;
			}
		}
		avgMV=validTotal/validcount;
		BigDecimal bdDown=new BigDecimal(avgMV).setScale(2,RoundingMode.DOWN);
		avgMV=bdDown.doubleValue();
		return 0;
	}

}
