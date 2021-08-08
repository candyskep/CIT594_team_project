package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import edu.upenn.cit594.datamanagement.propertyReader;

public class AvgLivableArea extends AverageProperty {

	public AvgLivableArea(propertyReader popreader, String zipcode) {
		super(popreader, zipcode);
	}

	@Override
	public double getAvg() {
		ArrayList<Object> zipcolumn=this.propertyReader.getZipcode();
		ArrayList<Object> livablecolumn=this.propertyReader.getLivablearea();
		double avgLivable=0;
		int validcount=0;
		double validTotal=0;
		double temp=0;
		for (int i=0;i<zipcolumn.size();i++) {
			if(zipcolumn.get(i)!=null&&livablecolumn.get(i)!=null&&zipcolumn.get(i).equals(zipcode)) {
				try{
					temp=Double.valueOf((String) livablecolumn.get(i));
				}catch (NumberFormatException e) {
					continue;
				}
				validTotal+=temp;
				validcount+=1;
			}
		}
		avgLivable=validTotal/validcount;
		BigDecimal bdDown=new BigDecimal(avgLivable).setScale(2,RoundingMode.DOWN);
		avgLivable=bdDown.doubleValue();
		return 0;
	}

}
