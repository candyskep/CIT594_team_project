package edu.upenn.cit594;


import edu.upenn.cit594.datamanagement.DataFile;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInterface;

public class Main {

	public static void main(String[] args) {
		// Check that arguments are valid
		if(!checkArgs(args)) {
			return;
		}

		// Get Logger and set log file
		Logger l = Logger.getInstance();
		l.setLogfile(args[3]);
		
		// Log args to log file
		l.log(String.format("%s %s %s %s", args[0], args[1], args[2], args[3]));
		
		// Initialize DataFileReader and pass it filenames
		DataFile data = new DataFile(args[0], args[1], args[2]);
		
		// Initialize Processor and pass it the DataFileReader
		Processor processor = new Processor(data);
		
		// Initialize UI and pass it the Processor
		UserInterface ui = new UserInterface(processor);
		
		// UI start
		ui.start();
		
		// Close log file
		l.closeLogFile();
	}
	
	/* Checks arguments to ensure validity
	 * Throws an exception if there are too few arguments or null arguments
	 */
	private static boolean checkArgs(String[] args) {
		String error = "Invalid args! Usage: edu.upenn.cit594.Main [covid filename] [property filename] [population filename] [log filename]";
		if(args.length < 4) {
			throw new IllegalArgumentException(error);
		}
		
		for(String arg : args) {
			if(arg == null) {
				throw new IllegalArgumentException(error);
			}
		}
		return true;
	}
}

