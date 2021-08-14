package edu.upenn.cit594.processor;




import edu.upenn.cit594.datamanagement.DataFile;
import edu.upenn.cit594.logging.Logger;

public class Processor {
	DataFile data;
	Logger l;
	
	// All possible processor classes, uninitialized. 
	TotalPopulation tp;
	VaccinationsPerCapita vpc;
	ResidentialMarketValue rmv;
	Average avg;
	CityIndex ci;
	
	
	// Constructor
	public Processor(DataFile data) {
		this.data = data;
		l = Logger.getInstance();
	}
	
	// Calculate Data
	public String[] calculateData(String choice, String arg) {
		switch(choice) {
			case "1":
				// Initialize if uninitialized
				if(this.tp == null) {
					tp = new TotalPopulation(data);
				}
				return new String[] {tp.getPopulation()};
				
			case "2":
				if(this.vpc == null) {
					vpc = new VaccinationsPerCapita(data);
				}
				return vpc.getVaccinationsPerCapita(arg);
			
		
			case "3":
				if(avg == null) {
					avg = new Average(data);
				}
				return avg.getAvg(arg, new AvgMarketValue());// arg=19103,
				
			case "4":
				if(avg == null) {
					avg = new Average(data);
				}
				return avg.getAvg(arg, new AvgLivableArea());
				
			case "5":
				if(this.rmv == null) {
					rmv = new ResidentialMarketValue(data);
				}
				
				return rmv.getResidentialMarketValuePerCapita(arg);
				
				
			case "6":
				ci=new CityIndex(data);
				return ci.cityRank();
				
			default:
				return null;
		}
	}
	
	
		
}
