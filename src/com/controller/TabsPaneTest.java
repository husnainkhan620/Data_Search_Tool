package com.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabsPaneTest {
   public static void main(String[] args) {
      createWindow();
   }

   private static void createWindow() {    
      JFrame frame = new JFrame("Swing Tester");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      createUI(frame);
      frame.setSize(560, 200);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);
   }

   private static void createUI(final JFrame frame){  
     
	   JTabbedPane tabbedPane = new JTabbedPane();
      
      JPanel panel1 = new JPanel(false);
      JLabel filler = new JLabel("Tab 1");
      filler.setHorizontalAlignment(JLabel.CENTER);
      panel1.setLayout(new BorderLayout());
      panel1.add(filler);
      tabbedPane.addTab("Tab 1", null, panel1,"Tab 1 tooltip");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
      
      JPanel panel2 = new JPanel(false);
      JLabel filler2 = new JLabel("Tab 2");
      filler2.setHorizontalAlignment(JLabel.CENTER);
      panel2.setLayout(new GridLayout(1, 1));
      panel2.add(filler2);
      tabbedPane.addTab("Tab 2", null, panel2,"Tab 2 tooltip");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);     
      
      //frame.setLayout(null);
      frame.add(tabbedPane, BorderLayout.CENTER);    
   }
}