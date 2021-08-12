package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.DataFileReader;
import edu.upenn.cit594.datamanagement.DataFrame;
import edu.upenn.cit594.processor.CityIndex;
import edu.upenn.cit594.processor.LivableArea;
import edu.upenn.cit594.processor.MarketValue;
import edu.upenn.cit594.processor.Population;

public class UserInterface {
	protected Scanner input;
	DataFileReader data;
	DataFrame df_population;
	DataFrame df_property;
	DataFrame df_covid;
	
	public UserInterface(DataFileReader data) { // adding more processors 
		this.data=data;
		df_population=data.getPopulationData();
		df_property=data.getPropertyData();
		df_covid=data.getCovidData();
		input = new Scanner(System.in);
	}
	
	public void start() {
		
		while(true) {
			String choice=null;
			 
			System.out.println("\nPress the number for result: \n"
					+ "0: Exit Program\n"+"1: get total population\n"+"2:get total vaccinations per capita for each ZIP Code\n"
					+"3:get average market value for a ZIP Code\n"+"4:get the average total liavalbe area for a ZIP Code\n"
					+"5:get the total residential market value per capita for a ZIP code\n"+"6.get the death rate for a ZIP Code\n");
		    System.out.print(">");
			System.out.flush();
			choice=input.nextLine();
			Pattern p=Pattern.compile("^([0-6])\s*$");
			Matcher m=p.matcher(choice);
			if(!m.find()) {
				System.out.println("Wrong input, try again!");
				//input.nextLine();
				continue;
			}else {choice=m.group(1);}
			

			switch(choice) {
			case "0": 
				System.out.println("Thanks for using!");
				System.exit(0);
			case "1":
				System.out.println(Population.get_total_population(data));
				continue;
			case "2":
				doCase2();
				continue;
			case "3":
				doCase3();
				continue;
			case "4":
				doCase4();
				
			case "5":
				doCase5();
				
			case "6":
				doCase6();
				
				
			default:
				System.out.println("Wrong input, please input an integer between 0-6. Try again:");
			}
		}
	}
	
	
	public void doCase2() {
		String choice=null;
		while(true) {
			System.out.println("\nPartial or Full? Press: [1]Partial     [2]Full \n");
			choice=input.nextLine();
			Pattern p=Pattern.compile("^([1-2])\s*$");
			Matcher m=p.matcher(choice);
			if(!m.find()) {
				System.out.println("Wrong input, try again!");
				continue;
			}else {
				choice=m.group(1);
				break;
				}
			
		}
		
		if(choice.equals("1")) {
			//process.partial calcualtion
			System.out.println("\nCHOICE 1");//         Partial calculation
		}
		else {
			//process.full calcualtion
			System.out.println("\nCHOICE 2"); //        Full calculation
		}	
	}
	
	/**
	 * Because there are more zipcodes in property.csv than in population.txt, I think when we want to check if the zip keyed in by user exists in the relevant files, 
	 * we need to pass the relevant dataframe to the prompt function, such that the function knows which zipcode column it should check against. 
	 * @param df
	 * @return
	 */
	public String promptZIPinput(DataFrame df) {
	
		String zipcode;   
		ArrayList<Object> allZip=df.getCol("zip_code");////read in the ZIP arraylist
		while(true) {
			System.out.println("\nPlease input a ZIP code: \n");
			//try{
				zipcode=input.nextLine();
				Pattern p=Pattern.compile("^([0-9]+)\\s*$");
				Matcher m=p.matcher(zipcode);
				if(m.find()) {
					zipcode=m.group(1);
				}else {
					System.out.println("Your input is wrong, please input a valid ZIP number\n");
					//input.nextLine();
					continue;
				}
			//}catch (InputMismatchException e) {
			//	System.out.println("Your input is wrong, please input a valid ZIP number\n");
			//	input.nextLine();
			//	continue;
			//}
			
			if(allZip.contains(zipcode)) {
				break;
			}else {
				System.out.println("Your input is not found, please input a valide ZIP code:\n");
				continue;
			}
		}
		return zipcode;
	}
		
		
		
	public void doCase3() {
		String zipcode=promptZIPinput(df_property);
		input.nextLine();
		double mv=MarketValue.getAvgMarketValue(data, zipcode);
		System.out.println("The average market value is:"+mv);	
	}
	
	public void doCase4() {
		String zipcode=promptZIPinput(df_property);
		input.nextLine();
		double avgLivableArea=LivableArea.getAvgLivableArea(data, zipcode);
		System.out.println("The average total liavalbe area is:"+avgLivableArea);	
	}
	
	public void doCase5() {
		String zipcode=promptZIPinput(df_population);
		input.nextLine();
		//doCase 5 function//////
		System.out.println("The total residential market value per capita is:");
	}
	
	public void doCase6() {
		Map<Integer,String> cityRank=CityIndex.getCityRank(data);
		System.out.println("\tCity Index\t\tZipCode");
		for(int i:cityRank.keySet()) {
			for(String zip:cityRank.values()) {
				System.out.println("\t"+i+"\t\t"+zip);
			}
		}
	}

}
