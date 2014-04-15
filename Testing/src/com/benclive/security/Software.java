package com.benclive.security;
import org.json.simple.JSONObject;

public class Software implements Comparable<Software> {
	private String name;
	private String versionString;
	private String publisher;
	
	public Software(String name, String versionString, String publisher) {
		this.name = name;
		this.versionString = versionString;
		this.publisher = publisher;
	}

	public Software() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getVersionString() {
		return versionString;
	}
	
	public String toString() {
		return "{ \"name\" : \"" + JSONObject.escape(name) + "\", \"versionString\" : \"" + JSONObject.escape(versionString) + "\", \"publisher\" : \"" + JSONObject.escape(publisher) + "\" }";
	}

	@Override
	public int compareTo(Software other) {
		return this.name.toLowerCase().compareTo(
				other.getName().toLowerCase());
	}
}
