package edu.upenn.cit594.processor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.DataFileReader;


public class CityIndex {
	
	/**
	 * City score= Rank of averageLivalbe(the more the higher) + Rank of possitive_test per capita (the less the higher), higher means smaller number, i.e. 1 is higher than 2
	 * @param data
	 * @return
	 */
	//
	public static Map<Integer,String> getCityRank(DataFileReader data) {
		ArrayList<Object>zipcodeAll=data.getData("population", "zip_code");
		ArrayList<Integer> avgLivableRank=CityIndex.getAvgLivableRank(data, zipcodeAll);
		ArrayList<Integer> pos_test_per_capital_Rank=CityIndex.getPos_test_per_capita_Rank(data, zipcodeAll);
		ArrayList<Integer> cityScore=new ArrayList<>();
		Map<Integer,String> cityRank=new TreeMap<>();
		String zip=null;
		
		int score=9999999;
		for(int i=0;i<zipcodeAll.size();i++) {
			score=avgLivableRank.get(i)+pos_test_per_capital_Rank.get(i);
			zip=(String)zipcodeAll.get(i);
			cityRank.put(score, zip);
		}

		return cityRank;			
		}
		
		
	
	
	public static int getZipPossitive(DataFileReader data, String zipcode) {	
		int zipPossitive=0;
		String zip=null;
		String postest=null;
		ArrayList<Object> zipColumn=data.getData("covid", "zip_code");
		ArrayList<Object> posTestColumn=data.getData("covid", "POS_tests");
		int size=zipColumn.size();


		for(int i=size-1;i>-1;i--) {
			zip=(String)zipColumn.get(i);
			postest=(String) posTestColumn.get(i);
			
			if(zip!=null&&zip.equals(zipcode)) {
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
		return -1;
	}
	
	
	
	public static ArrayList<Integer>  getAvgLivableRank(DataFileReader data,ArrayList<Object> zipcodeALL) {
		Map<Double,String> avgLivableRank=new TreeMap<>();
		ArrayList<Integer> livableRank = new ArrayList<Integer>(Collections.nCopies(zipcodeALL.size(), 9999));
				
        String zip=null;
        int index;

		int rank=zipcodeALL.size();
		for(Object o :zipcodeALL) {
			zip=(String)o;
			double avgLivableArea=LivableArea.getAvgLivableArea(data, zip);// if the zipcode doesn't exist in property.csv, return 0
			avgLivableRank.put(avgLivableArea, zip);
		}
		for (String z:avgLivableRank.values()) {
			index=zipcodeALL.indexOf(z);
			livableRank.set(index, rank);
			rank--;
		}
	    return livableRank;
	}
	
	public static ArrayList<Integer>  getPos_test_per_capita_Rank(DataFileReader data,ArrayList<Object> zipcodeALL) {
		Map<Double,String> pos_test_per_capita_Rank=new TreeMap<>();
		ArrayList<Integer> posRank = new ArrayList<Integer>(Collections.nCopies(zipcodeALL.size(), 9999));
		String zip=null;
		int zipPopulation=0;
		int zipPossitive=-1;
		double pos_test_per_capita=99;
		int rank=zipcodeALL.size();
		int index;
		//put zipcode as KEY
		
		for(Object o:zipcodeALL) {
			if(o!=null) {
				zip=(String)o;
				zipPopulation=Population.getZipPopulation(data, zip); // zipPopulation could be zero
				zipPossitive=CityIndex.getZipPossitive(data, zip);
				if(zipPopulation==0||zipPossitive==-1) continue;
				pos_test_per_capita=zipPossitive/zipPopulation;
				pos_test_per_capita_Rank.put(pos_test_per_capita, zip);
			}
		}
		
		
		for (String z:pos_test_per_capita_Rank.values()) {
			index=zipcodeALL.indexOf(z);
			posRank.set(index, rank);
			rank--;
		}
		
		
		
	    return posRank;
	}
	

}
