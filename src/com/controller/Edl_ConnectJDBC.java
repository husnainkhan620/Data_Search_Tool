package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Edl_ConnectJDBC {

	public static void main(String[] args) throws Exception {
		
		
		
		
		System.out.println("Connect to EDL warehouse");
		
		Class.forName("com.databricks.client.jdbc.Driver");
		
		String url = "jdbc:databricks://deere-edl.cloud.databricks.com:443;httpPath=/sql/1.0/warehouses/a11c5b83a9f2f69c;AuthMech=3;UID=token;PWD=dapida6d9df2ece0862f2409706ca5353161";
		
		try (Connection conn = DriverManager.getConnection(url)) {
			Statement stmt = conn.createStatement();
			
			String valueToCheck = "LV151N0";
			
			List<String> tablesToCheck = new ArrayList<String>();
		//	tablesToCheck.add("edl_current.sales_rebates");
		//	tablesToCheck.add("edl_current.order_and_retail_incentives_table");
		//	tablesToCheck.add("edl_current.zowgdlrsel_consumer");
		//	tablesToCheck.add("edl_current.irm_ipcrasp_consumable");
		//	tablesToCheck.add("edl_current.pwg_incentives_irm_gprs0031");
		//	tablesToCheck.add("edl_current.funds_daily_transactions");
			tablesToCheck.add("edl_current.of_sme_sales");
			//tablesToCheck.add("edl_current.core_product_master_ephexternal");
			
		/*	 try (ResultSet rs = stmt.executeQuery("show databases")) {
				  	while(rs.next())
				  		System.out.println(rs.getString(1));
				  		System.out.println();
				  	
			        }
			*/
			/*
			 try (ResultSet rs = stmt.executeQuery("show tables in edl_current")) {
				 
				  	while(rs.next())
				  		System.out.println(rs.getString(2)+" "+rs.getString(3));
				  	
				  		System.out.println();
				  	
			        }
			 
			
			System.exit(0);
			*/
			for(String tableToCheck : tablesToCheck) {
				System.out.println("Checkiing this table --> "+tableToCheck );
					 try (ResultSet rs = stmt.executeQuery("select * from "+tableToCheck+" limit 1")) {
						    ResultSetMetaData md = rs.getMetaData();
					        String[] columns = new String[md.getColumnCount()];
					        for (int i = 0; i < columns.length; i++) {
					          columns[i] = md.getColumnName(i + 1);
					          //System.out.println(columns[i]);
					        }
					        
					       /* while (rs.next()) {
					            System.out.print("Row " + rs.getRow() + "=[");
					            for (int i = 0; i < columns.length; i++) {
					              if (i != 0) {
					                System.out.print(", ");
					              }
					              System.out.print(columns[i] + "='" + rs.getObject(i + 1) + "'");
					            }
					            System.out.println(")]");
					          }
					        */
					        
					        for (int j = 0; j < columns.length; j++) {
					        	System.out.println("Checking for column "+columns[j]);
						         // check value against individual columns
							        try (ResultSet subrs1 = stmt.executeQuery("select * from "+tableToCheck+ " where "+columns[j]+" == '"+valueToCheck+"'")) {
							        
							        	while(subrs1.next()) {
							        	 System.out.println("Found for column --> "+columns[j]);
						        		 System.out.print("Row " + subrs1.getRow() + "=[");
								            for (int i = 0; i < columns.length; i++) {
								              if (i != 0) {
								                System.out.print(", ");
								              }
								              System.out.print(columns[i] + "='" + subrs1.getObject(i + 1) + "'");
								            }
								            System.out.println(")]");
								            break;
						        	   }
						            }
					        }
					 }
				}
		}
	}

}
