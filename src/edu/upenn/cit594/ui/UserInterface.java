package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.DataFrame;
import edu.upenn.cit594.processor.DeathRate;
import edu.upenn.cit594.processor.Population;

public class UserInterface {
	protected Scanner input;
	Population population;
	
	public UserInterface(Population pop) { // adding more processors 
		this.population=pop;
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
				System.out.println(population.get_total_population());
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
				//input.nextLine();
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
	
	
	public String promptZIPinput() {
		int zipnum=0;
		String zipcode;   
		List<String> allZip=new ArrayList<String>();////read in the ZIP arraylist
		String []temp={"123","456","789"};////read in the ZIP arraylist
		allZip=Arrays.asList(temp);////read in the ZIP arraylist
		while(true) {
			System.out.println("\nPlease input a ZIP code: \n");
			try{
				zipnum=input.nextInt();
			}catch (InputMismatchException e) {
				System.out.println("Your input is wrong, please input a valid ZIP number\n");
				input.nextLine();
				continue;
			}
			zipcode=String.valueOf(zipnum);
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
		String zipcode=promptZIPinput();
		input.nextLine();
		//doCase 3 function////////////////////////////
		System.out.println("The average market value is:");	
	}
	
	public void doCase4() {
		String zipcode=promptZIPinput();
		input.nextLine();
		//doCase 4 function////////////////////////////
		System.out.println("The average total liavalbe area is:");	
	}
	
	public void doCase5() {
		String zipcode=promptZIPinput();
		input.nextLine();
		//doCase 5 function//////
		System.out.println("The total residential market value per capita is:");
	}
	
	public void doCase6() {
		String zipcode=promptZIPinput();
		//double deathrate=DeathRate.getDeathRate(cr, zipcode);//need covidreader as input
		System.out.println("The death rate is:");
		input.nextLine();
		//get hospital 
	}

}
