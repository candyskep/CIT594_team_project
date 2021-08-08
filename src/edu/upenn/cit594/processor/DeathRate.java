package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.upenn.cit594.datamanagement.covidReader;

public class DeathRate {
	
	public static double getDeathRate(covidReader cr, String zipcode) {
		double deathrate=0;
		String zip=null;
		String death=null;
		String hospitalized=null;
		int totalDeath=0;
		int totalHos=0;
		for(int i=0;i<cr.getZipcode().size();i++) {
			zip=(String) cr.getZipcode().get(i);
			death=(String) cr.getDeaths().get(i);
			hospitalized=(String) cr.getHospitalized().get(i);
			if(zip!=null&&death!=null&&hospitalized!=null&&zip.equals(zipcode)) {
				try{
					totalDeath+=Integer.valueOf(death);
					totalHos+=Integer.valueOf(hospitalized);
				}catch(NumberFormatException e) {
					continue;
				}
				
			}
		}
		deathrate=totalDeath/totalHos;
		BigDecimal bdDown=new BigDecimal(deathrate).setScale(2,RoundingMode.DOWN);
		deathrate=bdDown.doubleValue();
		return deathrate;
	}
	

}
