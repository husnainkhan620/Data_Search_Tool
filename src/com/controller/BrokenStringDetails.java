package com.controller;

public class BrokenStringDetails {

	private String stringValue;
	private int offset;
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public String toString() {
		
		return this.stringValue + " " + this.offset; 
	}
}
