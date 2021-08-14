package edu.upenn.cit594.datamanagement;

public interface MyReader {
	public DataFrame readData(String filename, String[] headers, char token);
}
