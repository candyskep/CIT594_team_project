package edu.upenn.cit594.processor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.DataFile;



public class CityIndex {
	
	/**
	 * City score= Rank of averageLivalbe(the more the higher) + Rank of possitive_test per capita (the less the higher), higher means smaller number, i.e. 1 is higher than 2
	 * @param data
	 * @return
	 */
	DataFile data;
	TotalPopulation tp;
	Map <String, ArrayList<Object>> totalRank;
	Map<String,Double>  std_Pos_test_per_capita;
	Map<String,Double>  std_livable_per_capita;
	public CityIndex(DataFile data) {
		this.data=data;
		this.tp=new TotalPopulation(data);
		this.totalRank=new TreeMap<>();
		
	}
	
	
	
	public String[] cityRank() {
		ArrayList<String>zipcodeAll=data.getData("population", "zip_code");
		Map<String,Double>sd_livable=livable_per_capita();
		Map<String,Double>sd_posRatio=getPos_test_per_capita();
		Map<Double,String> cityIndex=new TreeMap<>();
		Map<Integer,String> cityRank=new TreeMap<>();
		double score=0;
		for(String zip:zipcodeAll) {
			double l=sd_livable.get(zip);
			double p=sd_posRatio.get(zip);
			if(l==-1||p==-1) {
				score=-2;
			}else {
				score=l+p;
			}
			cityIndex.put(score, zip);
		}
		
		cityIndex.remove(-2.0);
		int rank=cityIndex.size();
		for(double d:cityIndex.keySet()) {
			cityRank.put(rank,cityIndex.get(d));
			rank--;
		}
		
		String[] ranking=new String[cityRank.size()];
		for(int i=0;i<cityRank.size();i++) {
			ranking[i]="NO."+(i+1)+" :"+cityRank.get(i+1);
		}

		

		
		return ranking;			
		}
		
		
	
	/**
	 * return latest number of positive test, if the given zip is not found, return -1, if Pos_Test =null, return 0,
	 * if Pos_Test column is an non-number, continue looking for the next valid number
	 * @param zipcode
	 * @return
	 */
	public int getZipPositive(String zipcode) {	
		if(zipcode==null) {
			return -1;
		}
		int zipPossitive=0;
		String zip=null;
		String postest=null;
		ArrayList<String> zipColumn=data.getData("covid", "zip_code");
		ArrayList<String> posTestColumn=data.getData("covid", "POS_tests");
		int size=zipColumn.size();
		// if the input zipcode is not in covid file, return -1
		if(!zipColumn.contains(zipcode)) {
			return -1;
		}
			

		for(int i=size-1;i>-1;i--) {
			zip=zipColumn.get(i);
			postest=posTestColumn.get(i);
			
			if(zip!=null&&zip.equals(zipcode)) {
				//if zipcode found, but possitive test number is missing, continue looking for the next posTest number 
				if (postest==null) {
					return 0;
				}else {
					try{
						zipPossitive=Integer.valueOf(postest);
						return zipPossitive;
					}catch(NumberFormatException e) {
						continue;
					}
				}				
			}
		}
		// if zipcode found, but no valid posTest data is found, return -1
		return -1;
	}
	
	
	
	/**
	 * step1:for zipcode that has invalid population or Pos_Test, return 9999,other wise return positive test per capita
	 * step2:standardize the number of positive test per capita.(x-x_bar)/sd
	 * @return
	 */
	public Map<String,Double>  getPos_test_per_capita() {
		ArrayList<String> zipcodeALL=data.getData("population", "zip_code");
		Map<String,Double> pos_test_per_capita=new TreeMap<>();
		
		int zipPopulation=0;
		int zipPossitive=-1;
		double posPerCapita=99;

		double sd;
		double average;
		//put zipcode as KEY
		
		for(String zip:zipcodeALL) {
			if(zip!=null) {
				zipPopulation=tp.getZipPopulation(zip); // zipPopulation could be zero
				zipPossitive=getZipPositive(zip);
				if(zipPopulation==-1||zipPossitive==-1) {
					posPerCapita=-1;
				}else {
					posPerCapita=(double)zipPossitive/zipPopulation;
				}
					pos_test_per_capita.put(zip, posPerCapita);
			}
		}

		
		sd=CityIndex.SD(pos_test_per_capita);
		average=CityIndex.AVG(pos_test_per_capita);
		for(String zip:pos_test_per_capita.keySet()) {
			double v=pos_test_per_capita.get(zip);
			if(v!=-1) {
				v=(v-average)/sd;
			}
			pos_test_per_capita.put(zip, v);
		}
		this.std_Pos_test_per_capita=pos_test_per_capita;

	    return pos_test_per_capita;
	}
	
	
	/**
	 * Helper function to get avearage number 
	 * @param map
	 * @return
	 */
	public static double AVG(Map<String,Double> map) {
		double SD=0.0;
		double total=0.0;
		double average=0.0;
		int count=0;
		ArrayList<Double> values=new ArrayList<Double>();
		for(String key:map.keySet()) {
			values.add(map.get(key));
		}
		
		
		for(double d:values) {
			if(d!=-1) {
				total+=d;
				count++;
			}
		}
		average=total/count;
		return average;
	}
	
	/**
	 * Helper funcion to get SD
	 * @param map
	 * @return
	 */
	public static double SD(Map<String,Double> map) {
		double SD=0.0;
		double total=0.0;
		double sqr_total=0.0;
		double average=0.0;
		int count=0;
		ArrayList<Double> values=new ArrayList<Double>();
		for(String key:map.keySet()) {
			values.add(map.get(key));
		}
		
		
		for(double d:values) {
			if(d!=-1) {
				total+=d;
				count++;
			}
		}
		average=total/count;
		
		for(double d:values) {
			if(d!=-1) {
				sqr_total+=Math.pow((d - average), 2);
			}
		}
		SD=Math.sqrt(sqr_total/(count));

		return SD;
	}
	
	
	
	
	
	/**
	 * 
	 * when calculate the average and sd for the standardized livable_per_capit,
	 * if zipPopulation or zipLivable area not exist, the zipLivable_per_capital=-1(as invalid) and will not count in the average and sd calculation
	 * 
	 * @return
	 */
	
	public Map<String,Double>  livable_per_capita() {
		ArrayList<String> zipcodeALL=data.getData("population", "zip_code");
		Map<String,Double> livable_per_capita=new TreeMap<>();
		
		double zipPopulation=0;
		double zipLivalble=0;
		double livable_ratio=99;

		double sd;
		double average;
		//put zipcode as KEY
		
		for(String zip:zipcodeALL) {
			if(zip!=null) {
				zipPopulation=tp.getZipPopulation(zip); // zipPopulation could be zero
				zipLivalble=AvgLivableArea.zipLivable(data, zip);
				if(zipPopulation==-1||zipLivalble==-1) {
					livable_ratio=-1;
				}else {
					livable_ratio=(double)zipLivalble/zipPopulation;
				}
					livable_per_capita.put(zip, livable_ratio);
				
			}
		}
		//System.out.println(livable_per_capita.keySet());
		//System.out.println(livable_per_capita.values());
		
		sd=CityIndex.SD(livable_per_capita);
		average=CityIndex.AVG(livable_per_capita);
		for(String zip:livable_per_capita.keySet()) {
			double v=livable_per_capita.get(zip);
			if(v!=-1) {
				v=(v-average)/sd;
			}
			
			livable_per_capita.put(zip, v);
		}
		//System.out.println(livable_per_capita.keySet());
		//System.out.println(livable_per_capita.values());
		this.std_livable_per_capita=livable_per_capita;

	    return livable_per_capita;
	}
	
	

}
