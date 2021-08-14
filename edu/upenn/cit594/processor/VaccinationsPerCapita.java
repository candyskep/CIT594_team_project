package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.upenn.cit594.datamanagement.DataFile;

public class VaccinationsPerCapita {
	DataFile data;
	ArrayList<String> covidZipcode;
	ArrayList<String> covidDates;
	ArrayList<String> covidPartialVaccinations;
	ArrayList<String> covidFullVaccinations;
	ArrayList<String> popZipcode;
	ArrayList<String> population;
	
	public VaccinationsPerCapita(DataFile data) {
		this.data = data;
		this.covidZipcode = data.getData("covid", "zip_code");
		this.covidDates = data.getData("covid", "etl_timestamp");
		this.popZipcode = data.getData("population", "zip_code");
		this.population = data.getData("population", "population");
	}
	
	public String[] getVaccinationsPerCapita(String arg) {
		ArrayList<String> vaccinations = null;
		// Initialize objects if they are null
		if(arg.equalsIgnoreCase("partial")) {
			if(this.covidPartialVaccinations == null) {
				this.covidPartialVaccinations = data.getData("covid", "partially_vaccinated");
			}
			vaccinations = this.covidPartialVaccinations;
		} else if (arg.equalsIgnoreCase("full")) {
			if(this.covidFullVaccinations == null) {
				this.covidFullVaccinations = data.getData("covid", "fully_vaccinated");
			}
			vaccinations = this.covidFullVaccinations;
		}
		
		ArrayList<String> output = new ArrayList<String>();
		for(int i = 0; i < popZipcode.size(); i++) {
			String currZip = popZipcode.get(i);
			Double currPop = Double.parseDouble(population.get(i));
			String currVacsStr = getVaccinationsByZipcode(vaccinations, currZip);
			
			// Ignore any zipcodes with no/null vaccinations
			if(currVacsStr != null) {
				Double currVacs = Double.parseDouble(currVacsStr) / currPop;
				// Truncate and format currVacs appropriately, then add to output
				BigDecimal bdDown = new BigDecimal(currVacs).setScale(4,RoundingMode.DOWN);
				currVacs = bdDown.doubleValue();
				currZip = currZip + " " + String.format("%.4f", currVacs);
				output.add(currZip);
			}
			
		}
		return output.toArray(new String[0]);
	}
	
	private String getVaccinationsByZipcode(ArrayList<String> vaccinations, String zipcode){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date mostRecent = null;
		// Initialize mostRecent
		try {
			mostRecent = format.parse("1900-01-01 01:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		String currVacs = null;
		
		for(int i = covidDates.size() - 1; i >= 0; i--) {
			String currZip = covidZipcode.get(i);
			if(vaccinations.get(i) != null && covidDates.get(i) != null && currZip != null) {
				if(currZip.equals(zipcode)) {
					Date currDate;
					try {
						currDate = format.parse(covidDates.get(i));
					} catch (ParseException e) {
						currDate = null;
					}
					if(currDate != null && currDate.after(mostRecent)) {
						mostRecent = currDate;
						currVacs = vaccinations.get(i);
					}
				}
			}
		}
		return currVacs;
	}
	

}
