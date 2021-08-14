package edu.upenn.cit594.processor;

import java.util.ArrayList;

import edu.upenn.cit594.datamanagement.DataFile;

public interface AvgStrategy {
	ArrayList<String> getData(DataFile data);
}
