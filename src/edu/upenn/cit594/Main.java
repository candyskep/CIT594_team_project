package edu.upenn.cit594;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.popReader;
import edu.upenn.cit594.processor.Population;
import edu.upenn.cit594.ui.UserInterface;


public class Main {
	
	
	public static void main(String[] args) {
		String filename="population.txt";
		popReader popreader = new popReader(filename);
		Population population=new Population(popreader);
		UserInterface ui=new UserInterface(population);
		//ui.start();
		String s="  23 ";
		try{
			double temp=Double.valueOf(s);
			System.out.println(temp);
		}catch (NumberFormatException e) {
			System.out.println("wrong string");
		}
		
	

	}
}
