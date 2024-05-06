package com.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SAP_HANA_JDBC {

	
	JLabel jlSapConnect;JLabel jlSapconnectpta;JLabel jlSapConnectqta;JLabel jlSapUserId;JLabel jlSapPassword;
	ButtonGroup jrbg;JRadioButton jr1;JRadioButton jr2;JButton jBtnsapConnect;
	JTextField jtfuserName; JPasswordField jfuserPassword;
	EDL_UI edl_UI;
	
	private String connectingTo;
	
	Connection connection;
	Statement stmt;
	
	public SAP_HANA_JDBC(final EDL_UI edl_ui) throws ClassNotFoundException {

		Class.forName("com.sap.db.jdbc.Driver");
		
		this.edl_UI = edl_ui;
		
		final Properties p = new Properties();
		//p.setProperty( "user", "a903926" );
		//p.setProperty( "password", "axapabo8" );
		//p.setProperty( "user", "zbxnbmm" );
		//p.setProperty( "password", "Ham16eedakh!" );
		p.setProperty( "latency", "0" );
		p.setProperty( "communicationtimeout", "0" );
		p.setProperty("encrypt", "true");
		p.setProperty("trustStoreType","jks");
		p.setProperty("trustStore","C:\\hanacerts\\saphanabackfeed.jks"); 
		p.setProperty("trustStorePassword","changeit");
		
		new Thread(new Runnable() {	
			@Override
			public void run() {
				jlSapConnect=new JLabel("Connect To Hana ?");  
				jlSapConnect.setBounds(100,85, 250,30); 
				jr1 = new JRadioButton("qta");
				jr1.setBounds(200, 85, 50, 30);
				jr1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.print("qta connection selected");
						edl_ui.textArea.append("qta connection selected \n");
						edl_ui.textArea.append("Enter User Id and Password \n");
						connectingTo = "qta";
					}
				});
				jr2 = new JRadioButton("pta");
				jr2.setBounds(270, 85, 50, 30);
				jr2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.print("pta Connection selected \n");
						edl_ui.textArea.append("pta connection selected \n");
						edl_ui.textArea.append("Enter User Id and Password \n");
						connectingTo = "pta";
					}
				});
				jrbg = new ButtonGroup();
				jrbg.add(jr1);jrbg.add(jr2); 
			
				jlSapUserId=new JLabel("User Id");  
				jlSapUserId.setBounds(350,85,50,30); 
				
				jtfuserName = new JTextField();
				jtfuserName.setBounds(410,85,100,30);
				
				jlSapPassword=new JLabel("Passw..");  
				jlSapPassword.setBounds(520,85,50,30); 
				jfuserPassword = new JPasswordField();
				jfuserPassword.setBounds(580,85,100,30);
				
				jBtnsapConnect=new JButton("Connect");
				jBtnsapConnect.setBounds(700, 85, 100, 30);
				jBtnsapConnect.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Connecting to "+connectingTo);
						edl_ui.textArea.append("Connecting to "+connectingTo + "\n");
						
						System.out.println(" User Id is " + jtfuserName.getText().trim());
						System.out.println(" Password is " + jfuserPassword.getText().trim());
						
						p.setProperty( "user", jtfuserName.getText().trim() );
						p.setProperty( "password", jfuserPassword.getText().trim() );
						
						  connection = null;
					      try {       
					    	  if(connectingTo.equalsIgnoreCase("qta")) {
					    		  connection = DriverManager.getConnection(
					    				  "jdbc:sap://qtahdb.dx.deere.com:30015",p);
					    	  }
					    	  else if(connectingTo.equalsIgnoreCase("pta")) {
					    		  connection = DriverManager.getConnection(
					    				  "jdbc:sap://ptahdb.dx.deere.com:30015",p);
					    	  }
					      } catch (SQLException ex) {
					         System.err.println("Connection Failed:");
					         edl_ui.textArea.append("Connection Failed:\n");
					         System.err.println(ex);
					         return;
					      }
					      
					      if (connection != null) {
					          try {
					        	  Boolean hasPrevilages;
					             System.out.println("Connection to "+connectingTo +" HANA successful!");
					             edl_ui.textArea.append("Connection to "+connectingTo +" HANA successful!\n");
					             stmt = connection.createStatement();
					             ResultSet resultSet = stmt.executeQuery("Select * from schemas");
					             while(resultSet.next())
					             {
					            	 
					            	  hasPrevilages = false;
					            	  String hello = resultSet.getString(1);
					            	  hasPrevilages = resultSet.getBoolean(3);
					            	 if(hasPrevilages) {
					            	 	 edl_ui.mySelectedList1.add(resultSet.getString(1));
					            	 	 edl_ui.mySelectedSapSchemas.add(resultSet.getString(1));
					            	 }
					            	// System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3));
					             	 }
					             String myListStringValues[] = new String[edl_ui.mySelectedList1.size()];
								 myListStringValues = edl_ui.mySelectedList1.toArray(myListStringValues); 
								 System.out.println(myListStringValues);
								 
								 edl_ui.jlist1.setListData(myListStringValues);
								 edl_ui.textArea.append("Done \n");
								 jBtnsapConnect.setText("Connected");
								// jBtnsapConnect.setBackground(Color.green);
								 jBtnsapConnect.setEnabled(false);
								 try { Thread.sleep(10);}catch(Exception ex) {ex.printStackTrace();}
					        } catch (SQLException ex) {
					           System.err.println("Query failed!");
					           edl_ui.textArea.append("Query failed!");
					          
					        }
					      }			
					}
				});
				
				edl_ui.jf.add(jr1);edl_ui.jf.add(jr2);edl_ui.jf.add(jlSapConnect);edl_ui.jf.add(jBtnsapConnect);
				edl_ui.jf.add(jtfuserName);edl_ui.jf.add(jfuserPassword);edl_ui.jf.add(jlSapUserId);edl_ui.jf.add(jlSapPassword);
				
		     	try { Thread.sleep(10);}catch(Exception ex) {ex.printStackTrace();}
			}
		}).start();
	
		}
	
	public void executeSAPQuery(String tableName) {
		System.out.println("SAP table to query"+tableName);

		edl_UI.defaultTableModel.setRowCount(0);
		edl_UI.defaultTableModel.setColumnCount(0);
		
		System.out.println("this is a SAP table");
		System.out.println(edl_UI.textAreaQueryEditer.getText() + " limit 20");
		 try (ResultSet rs = stmt.executeQuery(edl_UI.textAreaQueryEditer.getText() + " limit 20")) {
			    ResultSetMetaData md = rs.getMetaData();
		        String[] columnNames = new String[md.getColumnCount()];
		        Integer[] columnTypes = new Integer[md.getColumnCount()];
		       
		        for (int i = 0; i < columnNames.length; i++) {
		        	columnNames[i] = md.getColumnName(i + 1);
		        	columnTypes[i] = md.getColumnType(i+1);	        	
		            //System.out.println(columns[i]);
		        	edl_UI.defaultTableModel.addColumn(md.getColumnName(i + 1));
		        }
		        
		        while (rs.next()) {
		        	String[] columnValues = new String[md.getColumnCount()];
		        	for (int i = 0; i < columnNames.length; i++) {
		        		
		        		columnValues[i] =  rs.getString(i + 1); 
		        		
		        	}
		        	edl_UI.defaultTableModel.addRow(columnValues);
		        	
		        	/*
		            System.out.print("Row " + rs.getRow() + "=[");
		            for (int i = 0; i < columnNames.length; i++) {
		              if (i != 0) {
		                System.out.print(", ");
		              }
		              System.out.print(columnNames[i] + "='" + rs.getObject(i + 1) + "'");
		            }
		            System.out.println(")]");
		             */	
		          }
		        		      
		        
		 } catch (SQLException e1) {
			 edl_UI.textArea.append("Something went wrong");
			 edl_UI.textArea.append("Something went wrong");
			e1.printStackTrace();
		}
	
		
	}
	
	public void checkSAPtable(String tableToCheck,String valueToCheck) {

		System.out.println("Checkiing this SAP table --> "+tableToCheck );
		edl_UI.textArea.append("Checkiing this SAP table --> "+tableToCheck+"\n" );
		
		tableToCheck = tableToCheck.replaceFirst("\\.", "\".\"");
			 try (ResultSet rs = stmt.executeQuery("select * from "+ "\""+tableToCheck+"\" limit 1")) {
				    ResultSetMetaData md = rs.getMetaData();
			        String[] columnNames = new String[md.getColumnCount()];
			        Integer[] columnTypes = new Integer[md.getColumnCount()];
			        for (int i = 0; i < columnNames.length; i++) {
			        	columnNames[i] = md.getColumnName(i + 1);
			        	columnTypes[i] = md.getColumnType(i+1);	        	
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
			        
			        for (int j = 0; j < columnNames.length; j++) {
			        	//System.out.println("Column Type --> " +columnTypes[j]+" "+getColumnType(columnTypes[j])); 
			        	System.out.println("Checking for column "+columnNames[j]+" "+edl_UI.getColumnType(columnTypes[j]));
			        	edl_UI.textArea.append("Checking for column "+columnNames[j]+"\n");
			        	try { Thread.sleep(100);}catch(Exception e) {e.printStackTrace();}
				         // check value against individual columns
			        		System.out.println("select * from \""+tableToCheck+ "\" where "+columnNames[j]+" = '"+valueToCheck+"'");
					        try (ResultSet subrs1 = stmt.executeQuery("select * from \""+tableToCheck+ "\" where "+columnNames[j]+" = '"+valueToCheck+"'")) {
					        
					        	while(subrs1.next()) {
					        	 System.out.println("Found for column --> "+columnNames[j]);
				        		 System.out.print("Row " + subrs1.getRow() + "=[");
				        		 edl_UI.textArea.append("Found for column --> "+columnNames[j]+"\n");
				        		 edl_UI.textArea.append("Row " + subrs1.getRow() + "=[");
						            for (int i = 0; i < columnNames.length; i++) {
						              if (i != 0) {
						                System.out.print(", ");
						                edl_UI.textArea.append(", ");
						              }
						              System.out.print(columnNames[i] + "='" + subrs1.getObject(i + 1) + "'");
						              edl_UI.textArea.append(columnNames[i] + "='" + subrs1.getObject(i + 1) + "'");
						            }
						            System.out.println(")]");
						            edl_UI.textArea.append(")]\n");
						            break;
				        	   }
				            }
			        }
			 } catch (SQLException e1) {
				 edl_UI.textArea.append("Something went wrong");
				 edl_UI.textArea.append("Something went wrong");
				e1.printStackTrace();
			}
		
	}
	
	public void loadSAPTablesData(String schemaName) {
		
		edl_UI.textArea.append("Loading tables... \n");
		System.out.println("Loading tables...");
		try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
		try {
				ResultSet rs = stmt.executeQuery("Select * from TABLES where SCHEMA_NAME = '"+schemaName+"'");
			  	while(rs.next()) {
			  		System.out.println(rs.getString(1)+"."+rs.getString(2));
			  		edl_UI.mySelectedList2.add(rs.getString(1)+"."+rs.getString(2));
			  		edl_UI.mySelectedSapTables.add(rs.getString(1)+"."+rs.getString(2));
			  	}
			  	
		}catch (Exception ex) {	
			  ex.printStackTrace();
		}
		
		edl_UI.textArea.append("Loading views... \n");
		System.out.println("Loading views...");
		try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
		try {
				ResultSet rs = stmt.executeQuery("Select * from VIEWS where SCHEMA_NAME = '"+schemaName+"'");
			  	while(rs.next()) {
			  		System.out.println(rs.getString(1)+"."+rs.getString(2));
			  		edl_UI.mySelectedList2.add(rs.getString(1)+"."+rs.getString(2));
			  		edl_UI.mySelectedSapTables.add(rs.getString(1)+"."+rs.getString(2));
			  	}
			  	
		}catch (Exception ex) {	
			  ex.printStackTrace();
		}
		
		String myListStringValues[] = new String[edl_UI.mySelectedList2.size()];
		myListStringValues = edl_UI.mySelectedList2.toArray(myListStringValues); 
		System.out.println(myListStringValues);
		 
		edl_UI.jlist2.setListData(myListStringValues);
		edl_UI.textArea.append("Done \n");
	}
	
	public boolean isSAPTableName(String tableName) {
		
		System.out.println(edl_UI.mySelectedSapTables.contains(tableName));
		
		if(edl_UI.mySelectedSapTables.contains(tableName)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isSAPSchema(String schemaName) {
		
		if(connection == null) {
			System.out.println("connection is null");
			edl_UI.textAreaQueryEditerpopupCnsole.append("connection is null\n");
			try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
		}
		
		System.out.println(edl_UI.mySelectedSapSchemas.contains(schemaName));
		
		if(edl_UI.mySelectedSapSchemas.contains(schemaName)) {
			return true;
		}
		return false;
	}
	

	
	
	public boolean isSAPDataBase(String schemaName) {
	
		
		if(edl_UI.mySelectedSapSchemas.contains(schemaName)) {
			return true;
		}
		
		return false;
		
	}
	public static void main(String[] args) throws Exception{
		

	}

}
