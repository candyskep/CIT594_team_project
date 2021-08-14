package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import java.util.Scanner;
import java.util.InputMismatchException;


public class UserInterface {
	protected Scanner input;
	Processor processor;
	Logger l;
	
	// User Interface Constructor
	public UserInterface(Processor processor) {
		this.processor = processor;
		this.input = new Scanner(System.in);
		this.l = Logger.getInstance();
	}

	/* Starts the UI. 
	 * Gets output from processor, prints, then returns.
	 */
	public void start() {
		boolean running = true;
		while(running) {
			// Storage variable for user input
			String userInput = null;
			String[] systemOutput = null;
			
			// Check first input
			userInput = getUserFirstInput(input);
			
			// If first prompt is valid, process accordingly
			if(userInput != null) {
				switch(userInput) {
					case "0": 
						System.out.println("Thanks for using!");
						running = false;
						break;
						
					case "1":
						systemOutput = processor.calculateData("1", null);
						break;

					case "2":
						userInput = getUserPartialOrFull(input);
						systemOutput = processor.calculateData("2", userInput);
						break;

					case "3":
						userInput = getUserZipCode(input);
						systemOutput = processor.calculateData("3", userInput);
						break;
						
					case "4":
						userInput = getUserZipCode(input);
						systemOutput = processor.calculateData("4", userInput);
						break;
						
					case "5":
						userInput = getUserZipCode(input);
						systemOutput = processor.calculateData("5", userInput);
						break;
						
					case "6":
						systemOutput = processor.calculateData("6", null);
						break;
						
					default:
						break;
				}
				
				// Print the output
				if(systemOutput != null) {
					System.out.println(" ");
					System.out.println("BEGIN OUTPUT");
					for(String str : systemOutput) {
						System.out.println(str);
					}
					System.out.println("END OUTPUT");
				} 
			}
		}
		input.close();
	}
	
	public String getUserPartialOrFull(Scanner input) {
		String userInput = null; 
		boolean isValid = false;
		while(!isValid) {
			System.out.println("Please type: Partial or Full");
			System.out.print("> ");
			System.out.flush();
			userInput = input.nextLine();
				if(userInput.equalsIgnoreCase("Partial")) {
					isValid = true;
				} else if(userInput.equalsIgnoreCase("Full"))  {
					isValid = true;
				} else {
					System.out.println("I'm sorry, that is not an acceptable input. Please try again!");
				}
		}
		l.log(userInput);
		return userInput;
	}
	
	private String getUserFirstInput(Scanner input) {
		String userInput = null;
		// "Please input the number (0-6) of the function you want.";
		String prompt = "\nPress the number for result: \n"
				+ "0: Exit Program\n"+"1: Get total population\n"+"2: Get total vaccinations per capita for each ZIP Code\n"
				+"3: Get average market value for a ZIP Code\n"+"4: Get the average total livable area for a ZIP Code\n"
				+"5: Get the total residential market value per capita for a ZIP code\n"+"6: Get the death rate for a ZIP Code\n";
		
		// Do initial prompt
		System.out.println(prompt);
	    System.out.print("> ");
		System.out.flush();
		
		// Get user input			
		userInput = input.nextLine();
		
		// Ensure that user input is a single integer from 0 - 6
		if(userInput.length() != 1 || userInput.charAt(0) > 54 || userInput.charAt(0) < 47) {
			System.out.println("Wrong input, please input an integer between 0-6. Try again:");
			return null;
		} else {
			l.log(userInput);
			return userInput;
		}
	}
	
	private String getUserZipCode(Scanner input) {
		Integer userInput = 0; 
		boolean isValid = false;
		while(!isValid) {
			System.out.println("Please input a ZIP code:");
			System.out.print("> ");
			System.out.flush();
			try {
				userInput = input.nextInt();
				if(userInput > 99999 || userInput < 10000) {
					System.out.println("Error: Please input a valid 5 digit ZIP code.");
					input.nextLine();
				} else {
					isValid = true;
				}
			}catch (InputMismatchException e) {
				System.out.println("Error: Please input a valid 5 digit ZIP code.");
				input.nextLine();
				continue;
			}
		}
		// Clear the scanner's buffer and return
		input.nextLine();
		l.log(userInput.toString());
		return userInput.toString();
	}
	
	
}		
		
		

