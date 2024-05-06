package com.controller;

import java.util.ArrayList;

public class StringBreakdownwithOffset {

	public static void main(String[] args) {
		
		String query = "Select * from edl  select pain join heat";
		String words[] = query.split(" ");
		
		ArrayList<BrokenStringDetails> brokenStringDetailsList = new ArrayList<BrokenStringDetails>();
		int offsetValue = 0;
		for(int i=0;i<words.length;i++) {
			
			BrokenStringDetails brokenStringDetails = new BrokenStringDetails();
			brokenStringDetails.setStringValue(words[i]);
			brokenStringDetails.setOffset(offsetValue);
			brokenStringDetailsList.add(brokenStringDetails);
			offsetValue = offsetValue + words[i].length()+1;
		}
		
		for(BrokenStringDetails brokenStringDetails  :brokenStringDetailsList) {
			//System.out.println(brokenStringDetails.toString());
			System.out.println(query.substring(brokenStringDetails.getOffset(),brokenStringDetails.getOffset() + brokenStringDetails.getStringValue().length()));
		}
		
		/*
		 * System.out.println(query.substring(0, 6));
		 * System.out.println(query.substring(7, 1+7));
		 * System.out.println(query.substring(9, 9+4));
		 */
	}

}
