package edu.upenn.cit594;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.DataFrame;
import edu.upenn.cit594.datamanagement.txtReader;
import edu.upenn.cit594.processor.Population;
import edu.upenn.cit594.ui.UserInterface;


public class Main {
	
	
	public static void main(String[] args) {
		String filename="population.txt";
		Population population=new Population(filename);
		UserInterface ui=new UserInterface(population);
		ui.start();

		
	

	}
}
