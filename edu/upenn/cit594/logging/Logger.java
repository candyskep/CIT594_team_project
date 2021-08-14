package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Logger {
	
	private PrintWriter out;
	
	// Private constructor
	private Logger() { }
	
	// Singleton instance
	private static Logger instance = new Logger();
	
	
	/* Public method to get logger
	 * Returns this instance of logger. 
	 */
	public static Logger getInstance() {
		return instance;
	}
	
	/* Public method to set filename
	 * Closes existing log file if there is one open.
	 * Opens filename for logging; creates a new file if file does not exist. 
	 */
	public void setLogfile(String filename) {
		try { 
			if(out != null) {
				out.close();
				out = null;
			}
			out = new PrintWriter(new FileWriter(new File(filename), true)); 
		}
		catch (Exception e) { 
			System.out.println("Error loading file to logger");
		}
	}
	
	/* Public method to write to log
	 * Writes a string to the initialized log file. 
	 * Prints an error if log file is not set. 
	 */
	public void log(String msg) {
		// null check
		if(msg == null) {
			System.out.println("Error: cannot log null message");
			return;
		}
		
		// log to out if out is not null
		if(out == null) {
			System.out.println("Error: logfile not set");
		}
		else {
			// Format String
			String logString = String.format("%tF %tT ", System.currentTimeMillis(), System.currentTimeMillis());
			logString += msg;
			out.println(logString);
			out.flush();
		}
		return;
	}
	
	/* Public method to close log file
	 * If there is a log file open, closes the log file. 
	 */
	public void closeLogFile() {
		if(out != null) {
			out.close();
			out = null;
		}
	}
	
}
