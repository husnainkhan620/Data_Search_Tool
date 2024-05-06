package com.controller;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		String tableToCheck = "_SYS_BIC.deere.of.datalake.model/CA_DL_MACH_BASE_WG";
		
	
		tableToCheck = tableToCheck.replaceFirst("\\.", "\".\"");
		tableToCheck = "\"" + tableToCheck + "\"";
		System.out.println(tableToCheck);
		*/
		//String query = "select * from \"_SYS_BIC\".\"deere.of.datalake.model/CA_DL_VHCLE_LAST_CHANGE\" ";
		//System.out.println(query);
		String query = "select * from edl_current.zowgdlrsel_consumer";
		String queryArray[] = query.split(" ");
		String queryString ="";
		int foundIndex = -1;
		for(int i=0;i<queryArray.length;i++) {
			
			queryString = queryArray[i].trim();
			if(queryString.equalsIgnoreCase("from")) {
				System.out.println("Table name after from  ->" +queryArray[i+1].trim());
				foundIndex = i+1;
			}
		}
		
		String schemaName[] = queryArray[foundIndex].split("\\.");
		System.out.println(schemaName[0].replace("\"", ""));
		
		
		 String[] colName = new String[] { "Product Name" ,"Price" };
	        Object[][] products = new Object[][] { 
	                { "Galleta" ,"$80" },
	                { "Malta" ,"$40" },
	                { "Nestea" ,"$120" },
	                { "Tolta" ,"$140" } 
	            };

	        JTable table = new JTable( products, colName );

	        JFrame frame = new JFrame( "Simple Table Example" );

	        // create scroll pane for wrapping the table and add
	        // it to the frame
	        frame.add( new JScrollPane( table ) );
	        frame.pack();
	        frame.setVisible( true );
	}

}
