package edu.upenn.cit594;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.DataFileReader;
import edu.upenn.cit594.datamanagement.DataFrame;
import edu.upenn.cit594.datamanagement.txtReader;
import edu.upenn.cit594.processor.CityIndex;
import edu.upenn.cit594.processor.LivableArea;
import edu.upenn.cit594.processor.Population;
import edu.upenn.cit594.ui.UserInterface;


public class Main {
	
	
	public static void main(String[] args) {
		DataFileReader data=new DataFileReader("covid_data.csv", "properties.csv", "population.txt");
		UserInterface ui=new UserInterface(data);
		DataFrame df_population=data.getPopulationData();
		DataFrame df_property=data.getPropertyData();
		DataFrame df_covid=data.getCovidData();
		
		
		Scanner input=new Scanner(System.in);
		String zipcode;
		//while(true) {
			//zipcode=ui.promptZIPinput(df_covid);
			//System.out.println(CityIndex.getZipPossitive(data, zipcode));
			
			ArrayList<Object> zipcodes=data.getData("population", "zip_code");
			System.out.println(CityIndex.getPos_test_per_capita_Rank(data, zipcodes));
			
			
		//}
		
		
		
		
		
		
		
		
		//UserInterface ui=new UserInterface(data1);
		//ui.start();
		
	
		
		
		
	}
}
