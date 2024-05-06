package com.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class EDL_UI {

	// dapida6d9df2ece0862f2409706ca5353161
	
	JFrame jf;JFrame jfQueryPopup;
	JTextField jtf;JTextField jtf1;JTextArea textArea; JLabel jl;JLabel jl1;JButton jb1; 
	JLabel jlInvalidToken;JLabel jlSearchSchemas;JLabel jlSearchTables;
	JTextArea jTextarea;JPanel jpanel1;JPanel jpanel2;JPanel jpanel3;JPanel jpanel4;
	JTextField jtfSearchSchemas; JTextField jtfSearchTables;
	JButton jb2;
	JLabel jlclearList2;JLabel jlclearList3;
	JLabel jlqueyEditer;
	DefaultTableModel defaultTableModel;DefaultTableModel defaultTableModelpopup;;JTable jtQueryResult;JTextArea  textAreaQueryEditer;
	JTextArea textAreaQueryEditerpopupCnsole;
	Connection conn;  //https://docs.databricks.com/en/integrations/jdbc/authentication.html
	Statement stmt;
	String url;
	
	Set<String> mySelectedEdlTables = new TreeSet<String>();
	Set<String> mySelectedSapTables = new TreeSet<String>();
	
	Set<String> mySelectedEdlSchemas = new TreeSet<String>();
	Set<String> mySelectedSapSchemas = new TreeSet<String>();
	
	List<String> mySelectedList1 = new ArrayList<String>();
	Set<String> mySelectedList2 = new TreeSet<String>();
	Set<String> mySelectedList3 = new TreeSet<String>();
	JList<String> jlist1;JList<String> jlist2;JList<String> jlist3;
	
	SAP_HANA_JDBC  sap_HANA_JDBC;
	
	public EDL_UI() {
		
		ActionListener jb1ActionListenr = getjb1ActionListenr();
		ActionListener jb2ActionListenr = getjb2ActionListner();
		MouseListener mouseListener1 =  getjList1MouseListener();
		MouseListener mouseListener2 =  getjList2MouseListener();
		MouseListener mouseListner3 =  getjList3MouseListener();
		//ListSelectionListener listSelectionListener = getListSelectionListener();
		//ListSelectionListener listSelectionListener1 = getListSelectionListener1();
		
		jf = new JFrame("EDL Data Search");
		//jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.addWindowListener(windowcloseListener());
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/JD.png"));
		jf.setIconImage(imageIcon.getImage());
		
		
		jlInvalidToken=new JLabel("Invalid DataBricks Token");  
		jlInvalidToken.setBounds(300,20, 250,30); 
		//jlInvalidToken.setBackground(new Color(255,0,0));
		jlInvalidToken.setVisible(false);
		
	    jl=new JLabel("Connect to DataBricks : Enter the Token");  
	    jl.setBounds(100,50, 250,30); 
	    
		//jtf=new JTextField("dapida6d9df2ece0862f2409706ca5353161");  
	    jtf.setBounds(340,50, 330,30);  
	    
	    jb1=new JButton("Connect");  
	    jb1.setBounds(700,50,95,30);  
	    jb1.addActionListener(jb1ActionListenr);
	    
	    jlSearchSchemas = new JLabel("Search");
	    jlSearchSchemas.setBounds(100, 160, 50, 30);
	    jtfSearchSchemas=new JTextField();  
	    jtfSearchSchemas.setBounds(150,160, 150,30);  
	    jtfSearchSchemas.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("Released -- "+jtfSearchSchemas.getText());
				
				ArrayList<String> filteredSchemas = new ArrayList<String>();
				for (String mySelectedList1Item : mySelectedList1) {
					if(mySelectedList1Item.contains(jtfSearchSchemas.getText())) {
						filteredSchemas.add(mySelectedList1Item);
					}
				}
				String myListStringValues[] = new String[filteredSchemas.size()];
				myListStringValues = filteredSchemas.toArray(myListStringValues); 
				jlist1.setListData(myListStringValues);
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
	    
	    jpanel1 = new JPanel(new BorderLayout());
	    jlist1 = new JList<String>(mySelectedList1.toArray(new String[mySelectedList1.size()]));
	    jlist1.addMouseListener(mouseListener1);
	    JScrollPane scrollPane1 = new JScrollPane();
	    scrollPane1.setViewportView(jlist1);
	    jlist1.setLayoutOrientation(JList.VERTICAL);
	    //jlist1.setFont(new Font("Arial",Font.PLAIN,14));
	    jpanel1.setBounds(100, 200, 250, 400);
	    jpanel1.add(scrollPane1);
	    jpanel1.setVisible(true);
	    
	    
	    jlSearchTables = new JLabel("Search");
	    jlSearchTables.setBounds(400, 160, 50, 30);
	    jtfSearchTables=new JTextField();  
	    jtfSearchTables.setBounds(450,160, 150,30); 
	    jtfSearchTables.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("Released -- "+jtfSearchTables.getText());
				
				ArrayList<String> filteredTables = new ArrayList<String>();
				
				for (String mySelectedList1Item : mySelectedList2) {
					if(mySelectedList1Item.contains(jtfSearchTables.getText())) {
						filteredTables.add(mySelectedList1Item);
					}
				}
				String myListStringValues[] = new String[filteredTables.size()];
				myListStringValues = filteredTables.toArray(myListStringValues); 
				jlist2.setListData(myListStringValues);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
	    
	    jlclearList2 = new JLabel("Clear All");
	    jlclearList2.setBounds(680,173, 50,30);
	    jlclearList2.setForeground(Color.BLUE.darker());
	    jlclearList2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    jlclearList2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clear the list2 of tables");
				mySelectedList2.clear();
				
				String myListStringValues[] = new String[mySelectedList2.size()];
				myListStringValues = mySelectedList2.toArray(myListStringValues); 
				System.out.println(myListStringValues);
				 
				jlist2.setListData(myListStringValues);
			}
		});
	    
	    jpanel2 = new JPanel(new BorderLayout());
	    jlist2 = new JList<String>(mySelectedList2.toArray(new String[mySelectedList2.size()]));
		jlist2.addMouseListener(mouseListener2);
	    JScrollPane scrollPane2 = new JScrollPane();
	    scrollPane2.setViewportView(jlist2);
	    jlist2.setLayoutOrientation(JList.VERTICAL);
	    jpanel2.setBounds(400, 200, 350, 400);
	    jpanel2.add(scrollPane2);
	    jpanel2.setVisible(true);
	    
	    jlclearList3 = new JLabel("Clear All");
	    jlclearList3.setBounds(1080, 175, 50, 30);
	    jlclearList3.setForeground(Color.BLUE.darker());
	    jlclearList3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    jlclearList3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clear the list2 of tables");
				mySelectedList3.clear();
				
				String myListStringValues[] = new String[mySelectedList3.size()];
				myListStringValues = mySelectedList3.toArray(myListStringValues); 
				System.out.println(myListStringValues);
				 
				jlist3.setListData(myListStringValues);
			}
		});
	    jpanel3 = new JPanel(new BorderLayout());
	    jlist3 = new JList<String>(mySelectedList3.toArray(new String[mySelectedList3.size()]));
	    jlist3.addMouseListener(mouseListner3);
	    JScrollPane scrollPane3 = new JScrollPane();
	    scrollPane3.setViewportView(jlist3);
	    jlist3.setLayoutOrientation(JList.VERTICAL);
	    jpanel3.setBounds(800, 200, 350, 400);
	    jpanel3.add(scrollPane3);
	    jpanel3.setVisible(true);
	    
	    jlqueyEditer =new JLabel("Query Editer");  
	    jlqueyEditer.setBounds(1200,175, 100,30); 
	    JButton jbqueryRun = new JButton("Run");
	    jbqueryRun.setBounds(1300,177,70,20);
	    jbqueryRun.setBackground(Color.green);
	   
	    jfQueryPopup = new JFrame();
	    jfQueryPopup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	  
	    List<String> programmingConstantsSQL = new ArrayList<String>();
	    programmingConstantsSQL.add("SELECT");
	    programmingConstantsSQL.add("FROM");
	    programmingConstantsSQL.add("JOIN");
	    programmingConstantsSQL.add("INNER");
	    programmingConstantsSQL.add("LEFT");
	    programmingConstantsSQL.add("RIGHT");
	    programmingConstantsSQL.add("WHERE");
	    programmingConstantsSQL.add("LIKE");
	    
	    JButton jbqueryPopup = new JButton("Editer popup");
	    jbqueryPopup.setBounds(1400,177,150,20);
	    jbqueryPopup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel jfQueryPopupjLabel = new JLabel("Query Editer");
				jfQueryPopupjLabel.setBounds(50,50,150,20);
					    
				JButton jbquerypopupRun = new JButton("Run");
				jbquerypopupRun.setBounds(150,50,100,20);
				jbquerypopupRun.setBackground(Color.green);
				
				// Query tab pane
				JTabbedPane tabbedPane = new JTabbedPane();
				JTextPane textPaneQueryEditerPopup = new JTextPane(new DefaultStyledDocument() {
					
					private static final long serialVersionUID = 1L;
					final StyleContext cont = StyleContext.getDefaultStyleContext();
			        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
			        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
			        
			       
			       
			        
					public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
						 super.insertString(offset, str, a);
						 
						// System.out.println("Inserting string "+str+" at offset "+offset);
						 String query = getText(0, getLength());
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
								if(programmingConstantsSQL.contains(brokenStringDetails.getStringValue().trim().toUpperCase())) {
								 setCharacterAttributes(brokenStringDetails.getOffset(), brokenStringDetails.getOffset() + brokenStringDetails.getStringValue().length(), attr, true);
								}else {
									setCharacterAttributes(brokenStringDetails.getOffset(), brokenStringDetails.getOffset() + brokenStringDetails.getStringValue().length(), attrBlack, true);
								}
							}
							 
					 }
					
					 public void remove (int offs, int len) throws BadLocationException {
			                super.remove(offs, len);
			                
			                String text = getText(0, getLength());
			                System.out.println("Removing string "+text);

					 }
					
				}); 
				textPaneQueryEditerPopup.setFont(new Font("Arial",Font.PLAIN,22));
				
				DefaultCaret caret1popup = (DefaultCaret)textPaneQueryEditerPopup.getCaret(); //Making a JScrollPane automatically scroll all the way down
				caret1popup.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				JScrollPane scrollPane5popup = new JScrollPane();
				scrollPane5popup.setViewportView(textPaneQueryEditerPopup);
				scrollPane5popup.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
				scrollPane5popup.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
				JPanel panel1 = new JPanel(false);
				panel1.setLayout(new BorderLayout());
				panel1.add(scrollPane5popup);
				tabbedPane.addTab("New Tab 1", null, panel1,"Tab 1 tooltip");
				tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
				 
				/*
				JTextArea textAreaQueryEditerPopup1 = new JTextArea(""); 
				DefaultCaret caret1popup1 = (DefaultCaret)textAreaQueryEditerPopup1.getCaret(); //Making a JScrollPane automatically scroll all the way down
				caret1popup1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				JScrollPane scrollPane5popup1 = new JScrollPane();
				scrollPane5popup1.setViewportView(textAreaQueryEditerPopup1);
				scrollPane5popup1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
				scrollPane5popup1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
				JPanel panel2 = new JPanel(false);
				panel2.setLayout(new BorderLayout());
				panel2.add(scrollPane5popup1);
				tabbedPane.addTab("New Tab 2", null, panel2,"Tab 2 tooltip");
				tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);     
				*/      
				
				JPanel jpanelpopup = new JPanel(new BorderLayout());
				jpanelpopup.setBounds(50, 100, 1700, 300);
				jpanelpopup.add(tabbedPane,BorderLayout.CENTER);
					
				// Query console
				JLabel jfQueryPopupConsoleLabel = new JLabel("Console");
				jfQueryPopupConsoleLabel.setBounds(50,400,150,20);
				JPanel jpanelqueryConsole = new JPanel(new BorderLayout());
				jpanelqueryConsole.setBounds(50, 420, 1700, 100);
				textAreaQueryEditerpopupCnsole = new JTextArea(""); 
				textAreaQueryEditerpopupCnsole.setFont(new Font("Arial", Font.PLAIN, 16));
				DefaultCaret caret1 = (DefaultCaret)textAreaQueryEditerpopupCnsole.getCaret(); //Making a JScrollPane automatically scroll all the way down
				caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				JScrollPane scrollPane6popup = new JScrollPane();
				scrollPane6popup.setViewportView(textAreaQueryEditerpopupCnsole);
				scrollPane6popup.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
				scrollPane6popup.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
				jpanelqueryConsole.add(scrollPane6popup);
				
				
				// Query Result Table
				JPanel jpanel6popup = new JPanel(new BorderLayout());   
			    JTable jtQueryResultpopup = new JTable();
			    jtQueryResultpopup.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			   
			    JScrollPane scrollPane7popup = new JScrollPane(jtQueryResultpopup);
			    scrollPane7popup.setViewportView(jtQueryResultpopup);
			    scrollPane7popup.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
			    scrollPane7popup.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
			    
			    
			    defaultTableModelpopup =  (DefaultTableModel) jtQueryResultpopup.getModel();
			   // for(int i=0;i<15;i++) {
			   // 	 defaultTableModelpopup.addColumn("asdasd"+i);
			   // }
			    //defaultTableModelpopup.addRow(new String[][]());
			    
			    jpanel6popup.setBounds(50, 550, 1700, 400);
			    jpanel6popup.add(scrollPane7popup);
				
				
				jfQueryPopup.add(jfQueryPopupjLabel);
				jfQueryPopup.add(jfQueryPopupConsoleLabel);
				jfQueryPopup.add(jbquerypopupRun);
				jfQueryPopup.add(jpanelpopup);
				jfQueryPopup.add(jpanelqueryConsole);
				jfQueryPopup.add(jpanel6popup);
				
				jfQueryPopup.setSize(600, 600);
				jfQueryPopup.setBounds(500, 200, 600, 600);
				jfQueryPopup.setLayout(null);
				jfQueryPopup.setVisible(true);
				
			    
			    
			    jbquerypopupRun.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {

						System.out.println(textPaneQueryEditerPopup.getText());
						textAreaQueryEditerpopupCnsole.append(textPaneQueryEditerPopup.getText()+"\n");
						try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
						// Extract the table name from the query
						String query = textPaneQueryEditerPopup.getText();
						String queryArray[] = query.split(" ");
						String queryString ="";
						String tableName = "";
						int foundIndex =-1;
						for(int i=0;i<queryArray.length;i++) {
							
							queryString = queryArray[i].trim();
							if(queryString.equalsIgnoreCase("from")) {
								System.out.println("Table name after from  ->" +queryArray[i+1].trim());
								textAreaQueryEditerpopupCnsole.append("Table name after from  ->" +queryArray[i+1].trim()+"\n");
								try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
								foundIndex = i+1;
							}
						}
						String schemaName[] = queryArray[foundIndex].split("\\.");
						System.out.println(schemaName[0].replace("\"", ""));
						
						if(conn != null && mySelectedEdlSchemas.contains(schemaName[0].replace("\"", ""))) {
							defaultTableModelpopup.setRowCount(0);
							defaultTableModelpopup.setColumnCount(0);
							try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
							
							
							System.out.println("this is a EDL table");
							textAreaQueryEditerpopupCnsole.append("this is a EDL table \n");
							System.out.println(textPaneQueryEditerPopup.getText() + " limit 100");
							textAreaQueryEditerpopupCnsole.append(textPaneQueryEditerPopup.getText() + " limit 100 \n");
							try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
							 try (ResultSet rs = stmt.executeQuery(textPaneQueryEditerPopup.getText() + " limit 100")) {
								    ResultSetMetaData md = rs.getMetaData();
							        String[] columnNames = new String[md.getColumnCount()];
							        Integer[] columnTypes = new Integer[md.getColumnCount()];
							       
							        for (int i = 0; i < columnNames.length; i++) {
							        	columnNames[i] = md.getColumnName(i + 1);
							        	columnTypes[i] = md.getColumnType(i+1);	        	
							            //System.out.println(columns[i]);
							        	defaultTableModelpopup.addColumn(md.getColumnName(i + 1));
							        	
							        }							        
							        while (rs.next()) {
							        	String[] columnValues = new String[md.getColumnCount()];
							        	for (int i = 0; i < columnNames.length; i++) {
							        		
							        		columnValues[i] =  rs.getString(i + 1); 
							        		
							        	}
							        	defaultTableModelpopup.addRow(columnValues);						        								        
							          }	
							        
							        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
							        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
							        for(int i=0;i<jtQueryResultpopup.getColumnCount();i++) {
							        	jtQueryResultpopup.getColumnModel().getColumn(i).setMinWidth(150);
							        	jtQueryResultpopup.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
							        }
								   
							       
							 } catch (SQLException e1) {
								 textArea.append("Something went wrong");
								 textAreaQueryEditerpopupCnsole.append("Something went wrong\n");
								 try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
								e1.printStackTrace();
							}
						}
						else if(conn == null) {
							if(conn == null) {
								System.out.println("connection is null");
								textAreaQueryEditerpopupCnsole.append("connection is null\n");
							}
						}
						else if(sap_HANA_JDBC.isSAPSchema(schemaName[0].replace("\"", ""))) {
							System.out.println("this is a SAP table");
							textAreaQueryEditerpopupCnsole.append("this is a SAP table\n");
							sap_HANA_JDBC.executeSAPQuery(queryArray[foundIndex]);
							  
						}
						
							
					}
				});
			   
 jtQueryResultpopup.getTableHeader().addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println("Heade selected");
						JTableHeader tableheader = (JTableHeader) e.getComponent();
						System.out.print( e.getPoint()+ " ");
			            int col = tableheader.columnAtPoint(e.getPoint());
			            
			            defaultTableModelpopup.getColumnName(col);
			            System.out.print("col "+defaultTableModelpopup.getColumnName(col));
					}
				});
			    
			    jtQueryResultpopup.addMouseListener(new MouseAdapter() {
				
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
						JTable table = (JTable) e.getComponent();
						System.out.print( e.getPoint()+ " ");
			            int col = table.columnAtPoint(e.getPoint());
			            int row = table.rowAtPoint(e.getPoint());
			           
			            table.getValueAt(row, 0);
			            
			            System.out.print("col "+col);
			            System.out.println("row "+row);
			            
			           
					}
				});
			    
				
				
				
			}
		});
	    
	
	    
	    
	    JPanel jpanel5 = new JPanel(new BorderLayout());
	    JTextPane  textAreaQueryEditer = new JTextPane( new  DefaultStyledDocument() {
			
			private static final long serialVersionUID = 1L;
			final StyleContext cont = StyleContext.getDefaultStyleContext();
	        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.gray);
	        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.black);
	       
	        
			public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
				 super.insertString(offset, str, a);
				 
				// System.out.println("Inserting string "+str+" at offset "+offset);
				 String query = getText(0, getLength());
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
						if(programmingConstantsSQL.contains(brokenStringDetails.getStringValue().trim().toUpperCase())) {
						 setCharacterAttributes(brokenStringDetails.getOffset(), brokenStringDetails.getOffset() + brokenStringDetails.getStringValue().length(), attr, true);
						}else {
							setCharacterAttributes(brokenStringDetails.getOffset(), brokenStringDetails.getOffset() + brokenStringDetails.getStringValue().length(), attrBlack, true);
						}
					}
					 
			 }
			
			 public void remove (int offs, int len) throws BadLocationException {
	                super.remove(offs, len);
	                
	                String text = getText(0, getLength());
	                System.out.println("Removing string "+text);

			 }
			
		});  
	    textAreaQueryEditer.setFont(new Font("Areal", Font.PLAIN, 22));
	    DefaultCaret caret1 = (DefaultCaret)textAreaQueryEditer.getCaret(); //Making a JScrollPane automatically scroll all the way down
	    caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scrollPane5 = new JScrollPane();
	    scrollPane5.setViewportView(textAreaQueryEditer);
	    scrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	    scrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	    jpanel5.setBounds(1200, 200, 700, 200);
	    jbqueryRun.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println(textAreaQueryEditer.getText());
				// Extract the table name from the query
				String query = textAreaQueryEditer.getText();
				String queryArray[] = query.split(" ");
				String queryString ="";
				String tableName = "";
				int foundIndex =-1;
				for(int i=0;i<queryArray.length;i++) {
					
					queryString = queryArray[i].trim();
					if(queryString.equalsIgnoreCase("from")) {
						System.out.println("Table name after from  ->" +queryArray[i+1].trim());
						 textArea.append("Table name after from  ->" +queryArray[i+1].trim()+"\n");
						foundIndex = i+1;
					}
				}
				String schemaName[] = queryArray[foundIndex].split("\\.");
				System.out.println(schemaName[0].replace("\"", ""));
				
				if(conn != null && mySelectedEdlSchemas.contains(schemaName[0].replace("\"", ""))) {
					defaultTableModel.setRowCount(0);
					defaultTableModel.setColumnCount(0);
					
					System.out.println("this is a EDL table");
					textArea.append("this is a EDL table\n");
					System.out.println(textAreaQueryEditer.getText() + " limit 100");
					textArea.append(textAreaQueryEditer.getText() + " limit 100 \n");
					 try (ResultSet rs = stmt.executeQuery(textAreaQueryEditer.getText() + " limit 100")) {
						    ResultSetMetaData md = rs.getMetaData();
					        String[] columnNames = new String[md.getColumnCount()];
					        Integer[] columnTypes = new Integer[md.getColumnCount()];
					       
					        for (int i = 0; i < columnNames.length; i++) {
					        	columnNames[i] = md.getColumnName(i + 1);
					        	columnTypes[i] = md.getColumnType(i+1);	        	
					            //System.out.println(columns[i]);
					        	defaultTableModel.addColumn(md.getColumnName(i + 1));
					        }
					        
					        while (rs.next()) {
					        	String[] columnValues = new String[md.getColumnCount()];
					        	for (int i = 0; i < columnNames.length; i++) {
					        		
					        		columnValues[i] =  rs.getString(i + 1); 
					        		
					        	}
					        	defaultTableModel.addRow(columnValues);
					        	
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
						 textArea.append("Something went wrong");
						 textArea.append("Something went wrong");
						e1.printStackTrace();
					}
				}else if(conn == null) {
					System.out.println("connection is null");
					textArea.append("connection is null\n");
				}
				else if(sap_HANA_JDBC.isSAPSchema(schemaName[0].replace("\"", ""))) {
					System.out.println("this is a SAP table");
					sap_HANA_JDBC.executeSAPQuery(queryArray[foundIndex]);
					  
				}
					
			}
		});
 
	    jpanel5.add(scrollPane5);
	    jpanel5.setVisible(true);
	    
	    JPanel jpanel6 = new JPanel(new BorderLayout());   
	    jtQueryResult = new JTable();
	    jtQueryResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
	    
	    JScrollPane scrollPane6 = new JScrollPane(jtQueryResult);
	   // scrollPane6.setViewportView(jtQueryResult);
	    scrollPane6.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
	    scrollPane6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
	    
	    
	    defaultTableModel =  (DefaultTableModel) jtQueryResult.getModel();
	   // defaultTableModel.addColumn(column[0]);
	   // defaultTableModel.addColumn(column[1]);
	   // defaultTableModel.addColumn(column[2]);
	   // defaultTableModel.addRow(data[0]);
	    
	    jpanel6.setBounds(1200, 400, 700, 200);
	    jpanel6.add(scrollPane6);
	    jpanel6.setVisible(true);
	    
	    jl1=new JLabel("Enter the Value the Search");  
	    jl1.setBounds(630,650, 250,30); 
	    
	    jtf1=new JTextField();  
	    jtf1.setBounds(800,650,265,30);  
	    
	    jb2=new JButton("Search");  
	    jb2.setBounds(1070,650,95,30);  
	    jb2.addActionListener(jb2ActionListenr);
	    jb2.setVisible(true);
        
	    jpanel4 = new JPanel(new BorderLayout());
	    textArea = new JTextArea(""); 
	    DefaultCaret caret = (DefaultCaret)textArea.getCaret(); //Making a JScrollPane automatically scroll all the way down
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scrollPane4 = new JScrollPane();
	    scrollPane4.setViewportView(textArea);
	    scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	    scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	    textArea.setFont(new Font("Arial",Font.PLAIN,16));
	    
	    jpanel4.setBounds(100, 700, 1070, 200);
	    jpanel4.add(scrollPane4);
	    jpanel4.setVisible(true);
	    
	    
		jf.add(jtf);jf.add(jl);jf.add(jb1); jf.add(jlInvalidToken);jf.add(jpanel1);jf.add(jpanel2);jf.add(jpanel4);
		jf.add(jb2);jf.add(jpanel3);jf.add(jtf1);jf.add(jl1);
		jf.add(jlSearchSchemas);jf.add(jlSearchTables);
		jf.add(jtfSearchSchemas);jf.add(jtfSearchTables);
		jf.add(jlclearList3);jf.add(jlclearList2);
		jf.add(jlqueyEditer);jf.add(jbqueryRun) ;jf.add(jpanel5);jf.add(jpanel6);
		jf.add(jbqueryPopup);
		
		jf.setSize(1200, 1200);   //1200 1200  //1980 1700
		jf.setLayout(null);
		jf.setVisible(true);
		
		try {
			sap_HANA_JDBC = new SAP_HANA_JDBC(this);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private WindowListener windowcloseListener() {
		return new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if(conn != null) {
					try {
						System.out.println("Closing the EDL connections");
						if(stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if(sap_HANA_JDBC.connection != null) {
					try {
						System.out.println("Closing the SAP connections");
						if(stmt != null)
							sap_HANA_JDBC.stmt.close();
						sap_HANA_JDBC.connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		};
	}

	private MouseListener getjList1MouseListener() {
		MouseListener mouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
		
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							System.out.println("Selected DataBase - "+jlist1.getSelectedValuesList().toString());
							textArea.append("Selected DataBase - "+jlist1.getSelectedValuesList().toString()+"\n");
							
							// check if the data base is of SAP HANA if so call its query method and append as below
							if(sap_HANA_JDBC!= null) {
								 
								 if(sap_HANA_JDBC.isSAPDataBase(jlist1.getSelectedValuesList().get(0))) {
									 sap_HANA_JDBC.loadSAPTablesData(jlist1.getSelectedValuesList().get(0)); 
									 return ;
								 }
								
							}
							
							String selectedSchemas[] = new String[jlist1.getSelectedValuesList().size()];
							selectedSchemas = jlist1.getSelectedValuesList().toArray(selectedSchemas);
							
							textArea.append("Loading tables... \n");
							try {Thread.sleep(100);}catch(Exception ex) {ex.printStackTrace();}
							 try {
									 ResultSet rs = stmt.executeQuery("show tables in "+selectedSchemas[0]);
								 
								  	while(rs.next()) {
								  		System.out.println(rs.getString(1)+"."+rs.getString(2));
								  		mySelectedList2.add(rs.getString(1)+"."+rs.getString(2));
								  	}
								  	
							  }catch (Exception ex) {
								
								  ex.printStackTrace();
							}
							
							String myListStringValues[] = new String[mySelectedList2.size()];
							myListStringValues = mySelectedList2.toArray(myListStringValues); 
							System.out.println(myListStringValues);
							 
							jlist2.setListData(myListStringValues);
							textArea.append("Done \n");

						}
					}).start();
				}
			}
		};
		return mouseListener;
	}


	private MouseListener getjList2MouseListener() {
		MouseListener mouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
			
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					
					String mySelected2[] = new String[jlist2.getSelectedValuesList().size()];
					jlist2.getSelectedValuesList().toArray(mySelected2);
				
					for(String toAddValue : mySelected2) {
						mySelectedList3.add(toAddValue);
						mySelectedEdlTables.add(toAddValue);
					}
					
					System.out.println("Selected Tables are ---> "+mySelectedList3.toString());
					textArea.append("Selected Tables are  ---> "+mySelectedList3.toString()+"\n");
				
					String mySelected2new[] = new String[mySelectedList3.size()];
					jlist3.setListData(mySelectedList3.toArray(mySelected2new));
				}
			}
		};
		return mouseListener;
	}

	private MouseListener getjList3MouseListener() {
		MouseListener mouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
			
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					
					String mySelected2[] = new String[jlist3.getSelectedValuesList().size()];
					jlist3.getSelectedValuesList().toArray(mySelected2);
				
					for(String toAddValue : mySelected2) {
						mySelectedList3.remove(toAddValue);
						//mySelectedEdlTables.remove(toAddValue);
						//mySelectedSapTables.remove(toAddValue);
					}
					
					System.out.println("Selected Tables are ---> "+mySelectedList3.toString());
					textArea.append("Selected Tables are  ---> "+mySelectedList3.toString()+"\n");
				
					String mySelected2new[] = new String[mySelectedList3.size()];
					jlist3.setListData(mySelectedList3.toArray(mySelected2new));
				}
			}
		};
		return mouseListener;
	}
	

	private ActionListener getjb2ActionListner() {
		ActionListener jb2ActionListenr = new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
							System.out.println("Tables to search In  ---> "+mySelectedList3.toString());
							textArea.append("Tables to search In  ---> "+mySelectedList3.toString()+"\n");
							String valueToSearch = jtf1.getText();
							System.out.println("Value to search is "+valueToSearch);
							textArea.append("Value to search is "+valueToSearch+"\n");
							String valueToCheck = valueToSearch;
							
							List<String> tablesToCheck = new ArrayList<String>();
							for(String tableToCheck : mySelectedList3) {
								tablesToCheck.add(tableToCheck);
							}	
		
							for(String tableToCheck : tablesToCheck) {
								
								if(sap_HANA_JDBC.isSAPTableName(tableToCheck)) {
									System.out.println("This is a SAP table");
									textArea.append("This is a SAP table \n");
									sap_HANA_JDBC.checkSAPtable(tableToCheck, valueToCheck);
								}
								else {
								
									System.out.println("Checkiing this EDL table --> "+tableToCheck );
									textArea.append("Checkiing this EDL table --> \n"+tableToCheck );
										 try (ResultSet rs = stmt.executeQuery("select * from "+tableToCheck+" limit 1")) {
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
										        	System.out.println("Checking for column "+columnNames[j]+" "+getColumnType(columnTypes[j]));
										        	textArea.append("Checking for column "+columnNames[j]+"\n");
										        	try { Thread.sleep(100);}catch(Exception e) {e.printStackTrace();}
											         // check value against individual columns
												        try (ResultSet subrs1 = stmt.executeQuery("select * from "+tableToCheck+ " where "+columnNames[j]+" == '"+valueToCheck+"'")) {
												        
												        	while(subrs1.next()) {
												        	 System.out.println("Found for column --> "+columnNames[j]);
											        		 System.out.print("Row " + subrs1.getRow() + "=[");
											        		 textArea.append("Found for column --> "+columnNames[j]+"\n");
											        		 textArea.append("Row " + subrs1.getRow() + "=[");
													            for (int i = 0; i < columnNames.length; i++) {
													              if (i != 0) {
													                System.out.print(", ");
													                textArea.append(", ");
													              }
													              System.out.print(columnNames[i] + "='" + subrs1.getObject(i + 1) + "'");
													              textArea.append(columnNames[i] + "='" + subrs1.getObject(i + 1) + "'");
													            }
													            System.out.println(")]");
													            textArea.append(")]\n");
													            break;
											        	   }
											            }
										        }
										 } catch (SQLException e1) {
											 textArea.append("Something went wrong");
											 textArea.append("Something went wrong");
											e1.printStackTrace();
										}
									}
								}							
							System.out.println("<-- End of Searching -->");
							textArea.append("<-- End of Searching -->");		
						}	
					
				}).start();
			}
		};
		return jb2ActionListenr;
	}
	
	public String getColumnType(Integer columnType) {
		
		 
	        if(java.sql.Types.BIT == columnType) { return "BIT";}
	        if(java.sql.Types.TINYINT == columnType) { return "TINYINT";}
	        if(java.sql.Types.SMALLINT == columnType) {return "SMALLINT";}
	        if(java.sql.Types.INTEGER == columnType) {return "INTEGER";}
	        if(java.sql.Types.BIGINT == columnType) {return "BIGINT";}
	        if(java.sql.Types.FLOAT == columnType) {return "FLOAT";}
	        if(java.sql.Types.REAL == columnType) {return "REAL";}
	        if(java.sql.Types.DOUBLE == columnType) {return "DOUBLE";}
	        if(java.sql.Types.NUMERIC == columnType) {return "NUMERIC";}
	        if(java.sql.Types.DECIMAL == columnType) {return "DECIMAL";}
	        if(java.sql.Types.CHAR == columnType) {return "CHAR";}
	        if(java.sql.Types.VARCHAR == columnType) {return "VARCHAR";}
	        if(java.sql.Types.LONGVARCHAR == columnType) {return "LONGVARCHAR";}
	        if(java.sql.Types.DATE == columnType) {return "DATE";}
	        if(java.sql.Types.TIME == columnType) {return "TIME";}
	        if(java.sql.Types.TIMESTAMP == columnType) {return "TIMESTAMP";}
	        if(java.sql.Types.BINARY == columnType) {return "BINARY";}
	        if(java.sql.Types.VARBINARY == columnType) {return "VARBINARY";}
	        if(java.sql.Types.LONGVARBINARY == columnType) {return "LONGVARBINARY";}
	        if(java.sql.Types.NULL == columnType) {return "NULL";}
	        if(java.sql.Types.OTHER == columnType) {return "OTHER";}
	        if(java.sql.Types.JAVA_OBJECT == columnType) {return "JAVA_OBJECT";}
	        if(java.sql.Types.DISTINCT == columnType) {return "DISTINCT";}
	        if(java.sql.Types.STRUCT == columnType) {return "STRUCT";}
	        if(java.sql.Types.ARRAY == columnType) {return "ARRAY";}
	        if(java.sql.Types.BLOB == columnType) {return "BLOB";}
	        if(java.sql.Types.CLOB == columnType) {return "CLOB";}
	        if(java.sql.Types.REF == columnType) {return "REF";}
	        if(java.sql.Types.DATALINK == columnType) {return "DATALINK";}
	        if(java.sql.Types.BOOLEAN == columnType) {return "BOOLEAN";}
	        if(java.sql.Types.ROWID == columnType) {return "ROWID";}
	        if(java.sql.Types.NCHAR == columnType) {return "NCHAR";}
	        if(java.sql.Types.NVARCHAR == columnType) {return "NVARCHAR";}
	        if(java.sql.Types.LONGNVARCHAR == columnType) {return "LONGNVARCHAR";}
	        if(java.sql.Types.NCLOB == columnType) {return "NCLOB";}
	        if(java.sql.Types.SQLXML == columnType) {return "SQLXML";}
	        if(java.sql.Types.REF_CURSOR == columnType) {return "REF_CURSOR";}
	        if(java.sql.Types.TIME_WITH_TIMEZONE == columnType) {return "TIME_WITH_TIMEZONE";}
	        if(java.sql.Types.TIMESTAMP_WITH_TIMEZONE == columnType) {return "TIMESTAMP_WITH_TIMEZONE";}
	        
	        return "NO_TYPE";
	}

		private ActionListener getjb1ActionListenr(){
		ActionListener jb1ActionListenr = new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String token = jtf.getText();
						System.out.println("Token entered is "+token);
						textArea.append("Token entered is **** \n");
						textArea.append("Loading Databases... \n");
						try {Thread.sleep(100);}catch(Exception e) {e.printStackTrace();}
						url = "jdbc:databricks://deere-edl.cloud.databricks.com:443;httpPath=/sql/1.0/warehouses/a11c5b83a9f2f69c;AuthMech=3;UID=token;PWD="+token ; // "dapida6d9df2ece0862f2409706ca5353161";
						try { 
							 conn = DriverManager.getConnection(url) ;
							 stmt = conn.createStatement();
							
							 try (ResultSet rs = stmt.executeQuery("show databases")) {
							  	while(rs.next()) {
							  		if(rs.getString(1).startsWith("edl") || true) {
							  			//System.out.println(rs.getString(1));
							  			mySelectedList1.add(rs.getString(1));
							  			mySelectedEdlSchemas.add(rs.getString(1));
							  		}
							  	}
						      }							 
							 String myListStringValues[] = new String[mySelectedList1.size()];
							 myListStringValues = mySelectedList1.toArray(myListStringValues); 
							 System.out.println(myListStringValues);
							 
							 jlist1.setListData(myListStringValues);
							 textArea.append("Done \n");
							 jb1.setText("Connected");
							 //jb1.setBackground(Color.green);
							 jb1.setEnabled(false);
							
						} catch (SQLException e1) {
							jlInvalidToken.setVisible(true);
							e1.printStackTrace();
							return;
						}	
						
					}
				}).start();
				
						}
		};
		return jb1ActionListenr;
	}

	 
	public static void main(String[] args) throws Exception{
		
		Class.forName("com.databricks.client.jdbc.Driver");
		EDL_UI edl_ui = new EDL_UI();
		
		
	}

}
