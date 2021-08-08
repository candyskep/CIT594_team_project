package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;

public class covidReader {
	ArrayList<Object> zipcode=new ArrayList<>();
	ArrayList<Object> deaths=new ArrayList<>();
	ArrayList<Object> hospitalized=new ArrayList<>();
	ArrayList<Object> header=new ArrayList<>();
	public ArrayList<Object> getZipcode() {
		return zipcode;
	}
	public ArrayList<Object> getDeaths() {
		return deaths;
	}
	public ArrayList<Object> getHospitalized() {
		return hospitalized;
	}
	public ArrayList<Object> getHeader() {
		return header;
	}
	
	

}
