package autoseller;

import cars.*;
import autoseller.Login;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * Auto Seller - AutoSellerMain Class
 * 
 * Author: John Malcolm Anderson
 * Github: https://github.com/johnmalcolm101/Auto-Seller
 * Version: 1.0
 * Tags: Cars, Buy & Sell, Javax.Swing, SQLite, PayPal
 * 
 * This is the main class for buying and selling cars 
 * and also updating your account information and managing your listings.
 * 
 * Like all classes in the auto seller application it relies upon users login 
 * information so will not function correctly without first logging into an account.
 * 
 * The method uses the tabbed panel layout for the different sections - buy, sell, account.
 * The program also uses many scroll panes for displaying data from different database tables.
 * 
 * There is some error handling implemented but due to time constraints not as many as needed 
 * - improved error handling and utilization of Exception class is in progress.
 * 
 * The class is also very large - dividing into three classes "buy now", "sell car" and "account" is in progress.
 * Because the class contains so much code the three tabs - buy, sell and account are defined below using the following 
 * commenting syntax:
 * 
 * 
 * 			// __ Account panel below  __ //
 * 	
 * 				code for account section	
 * 
 * 			// __ END OF Account panel __ //
 * 
 * 
 * Please comment any changes thoroughly! Thanks :)
 */
public class AutoSellerMain extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoSellerMain frame = new AutoSellerMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Variables
		private String currentDatabase;
		private String currentSeller;
		private static String currentPayPal;
		private static String currentCar;
		private static String currentPrice;
		private static String eID = null;
		private int lastColumn;
		private int currentlySelling = 0;
		private String accountCarType;
		
		// Initializes Connection object "connection"
		static Connection connection = null;
		
		// JFrame Elements
		private JPanel contentPane;
		private JTable table;
		public static JTable buyNowTable;
		private JTextField textField1;
		private JTextField textField2;
		private JTextField textField3;
		private JTextField textField4;
		private JTextField textField7;
		private JTextField textField5;
		private JTextField textField8;
		private JTextField textField6;
		private JTextField textField9;
		private JTable manageListingsTable;
		private JTable soldCarsTable;
		private JTextField firstnameTxt;
		private JTextField surnameTxt;
		private JTextField streettxt1;
		private JTextField streettxt2;
		private JTextField townText;
		private JTextField payPalTxt;
		private JPasswordField passwordField;
	
	// Methods	
	/**
	 * Fill Table Method
	 * Fills "Buy Now" scroll pane table with data from a table in the AutoSeller database.
	 * The database table it uses to populate the scroll pane table is dependent on the -
	 * @param	string variable. 
	 */
	public static void fillTable(String query){
		try {			
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			// DBUtils is class inside rs2.xml.jar imported class
			AutoSellerMain.buyNowTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close(); 
			rs.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
			}
		}
	
	/**
	 * Sell RV Method.
	 * Sends query to database to add car info that user entered into text fields
	 * into the RV table of the database.
	 */
	private void sellRV(int currentlySelling) {
		try{
			String query = "insert into Recreational (Brand, Model, Year, Price, Location, Rooms, Bathrooms, DoubleDecker, RVClass, Seller) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textField1.getText());
			pst.setString(2, textField2.getText());
			pst.setString(3, textField3.getText());
			pst.setString(4, textField4.getText());
			pst.setString(5, textField5.getText());					
			pst.setString(6, textField6.getText());
			pst.setString(7, textField7.getText());
			pst.setString(8, textField8.getText());
			pst.setString(9, textField8.getText());
			pst.setString(10, Login.currentUser);
			
			// Checks to see if user has filled at least one text field in (just some basic error handling for the form)
			String checkUserInput = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText() + textField7.getText();
				
				if (checkUserInput.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!");	
				} else {
					pst.execute();
					JOptionPane.showMessageDialog(null, "Recreational Vehicle Uploaded. Happy Selling!");
					pst.close();
					clearTextFields();		
				}
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}		
	}

	/**
	 * Sell Sports Car Method.
	 * Sends query to database to add car info that user entered into text fields
	 * into the SportsCar table of the database.
	 */
	private void sellSportsCar(int currentlySelling) {
		try{
			String query = "insert into SportsCar (Brand, Model, Year, Price, Location, ZeroToSixty, Horsepower, Turbocharger, Seller) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textField1.getText());
			pst.setString(2, textField2.getText());
			pst.setString(3, textField3.getText());
			pst.setString(4, textField4.getText());
			pst.setString(5, textField5.getText());					
			pst.setString(6, textField6.getText());
			pst.setString(7, textField7.getText());
			pst.setString(8, textField8.getText());
			pst.setString(9, Login.currentUser);
			
			// Checks to see if user has filled at least one text field in (just some basic error handling for the form)
			String checkUserInput = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText() + textField7.getText();
											
				if (checkUserInput.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!");	
				} else {
					pst.execute();
					JOptionPane.showMessageDialog(null, "Sports Car Uploaded. Happy Selling!");
					pst.close();
					clearTextFields();		
				}
						
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
	}

	/**
	 * Sell SUV Car Method.
	 * Sends query to database to add car info that user entered into text fields
	 * into the SUV table of the database.
	 */
	private void sellSUV(int currentlySelling) {
		try{
			String query = "insert into SUV (Brand, Model, Year, Price, Location, TowingCapacity, FourWheelDrive, MiniSuv, SnowTyres, Seller) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textField1.getText());
			pst.setString(2, textField2.getText());
			pst.setString(3, textField3.getText());
			pst.setString(4, textField4.getText());
			pst.setString(5, textField5.getText());					
			pst.setString(6, textField6.getText());
			pst.setString(7, textField7.getText());
			pst.setString(8, textField8.getText());
			pst.setString(9, textField9.getText());
			pst.setString(10, Login.currentUser);						
						
			// Checks to see if user has filled at least one text field in (just some basic error handling for the form)
			String checkUserInput = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText() + textField7.getText();
				
				if (checkUserInput.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!");	
				} else {
					pst.execute();
					JOptionPane.showMessageDialog(null, "SUV Uploaded. Happy Selling!");
					pst.close();
					clearTextFields();		
				}			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Sell Electric Car Method.
	 * Sends query to database to add car info that user entered into text fields
	 * into the ElectricCar table of the database.
	 */
	private void sellElectricCar(int currentlySelling) {
		try{
			String query = "insert into ElectricCar (Brand, Model, Year, Price, Location, BatteryLife, ChargingTime, MilesPerCharge, Seller) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textField1.getText());
			pst.setString(2, textField2.getText());
			pst.setString(3, textField3.getText());
			pst.setString(4, textField4.getText());
			pst.setString(5, textField5.getText());					
			pst.setString(6, textField6.getText());
			pst.setString(7, textField7.getText());
			pst.setString(8, textField8.getText());
			pst.setString(9, Login.currentUser);				
			
			// Checks to see if user has filled at least one text field in (just some basic error handling for the form)
			String checkUserInput = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText() + textField7.getText();
				
				if (checkUserInput.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!");	
				} else {
					pst.execute();
					JOptionPane.showMessageDialog(null, "Electric Car Uploaded. Happy Selling!");
					pst.close();
					clearTextFields();		
				}						
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Sell Regular Car Method.
	 * Sends query to database to add car info that user entered into text fields
	 * into the RegularCar table of the database.
	 */
	private void sellRegularCar(int currentlySelling) {
		try{
			String query = "insert into RegularCar (Brand, Model, Year, Price, Location, Seller, Color, Doors, Seats) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textField1.getText());
			pst.setString(2, textField2.getText());
			pst.setString(3, textField3.getText());
			pst.setString(4, textField4.getText());
			pst.setString(5, textField5.getText());
			pst.setString(6, Login.currentUser);
			pst.setString(7, textField6.getText());
			pst.setString(8, textField7.getText());
			pst.setString(9, textField8.getText());
			
			// Checks to see if user has filled at least one text field in (just some basic error handling for the form)
			String checkUserInput = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText() + textField7.getText();
				
				if (checkUserInput.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill in all fields!");	
				} else {
					pst.execute();
					JOptionPane.showMessageDialog(null, "Car Uploaded. Happy Selling!");
					pst.close();
					clearTextFields();		
				}				
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
	}

	/**
	 * Clear Text Fields Method
	 * Clears the Text Fields in the sell car pane.
	 */
	private void clearTextFields() {
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		textField4.setText("");
		textField5.setText("");
		textField6.setText("");
		textField7.setText("");
		textField8.setText("");
		textField9.setText("");
		
	}
	
	/**
	 * Refresh Table Method
	 * Fills the "Account" - manage your listings - scroll pane table with data from a table in the AutoSeller database.
	 * Uses current user variable and the selected JComboBox option to populate table with appropriate information.
	 */
	public void refreshTable() 
	{
		try {
			String query = "select ID, Brand, Model, Year, Price from '"+accountCarType+"' where Seller = '"+Login.currentUser+"'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();	
			
			// DBUtils is class inside rs2.xml.jar imported class
			manageListingsTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Refresh Sold Table Method
	 * Fills the "Account" - sold cars - scroll pane table with data from a table in the AutoSeller database.
	 * Uses current user and to populate table with appropriate information.
	 */	
	public void refreshSoldTable() 
	{
		try {
			String query = "select ID, Car, Price, Buyer from SoldCars where Seller = '"+Login.currentUser+"'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();	
			
			// DBUtils is class inside rs2.xml.jar imported class
			soldCarsTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Buy Car Method
	 * Run when user selects a car in the "buy now" scroll pane table and clicks "buy now"
	 * Removes car from database. Hides current frame and displays Pay Now frame.
	 */	
	public void buyCar() {
		
		// Sets index for last column for use in SQLite queries
		switch(currentDatabase){
			case "RegularCar":
				lastColumn = 8;
			break;
				
			case "SUV":
				lastColumn = 9;
			break;
			
			case "ElectricCar":
				lastColumn = 8;
			break;
			
			case "Recreational":
				lastColumn = 9;
			break;
			
			case "SportsCar":
				lastColumn = 8;
			break;		
		}
		
		try {
			int row = buyNowTable.getSelectedRow();
			eID = (buyNowTable.getModel().getValueAt(row,lastColumn).toString());
			
			// Selects Car from Database that user selected
			String query = "select * from '"+currentDatabase+"' where ID = '"+eID+"' ";
			PreparedStatement pst = connection.prepareStatement(query);
			
			ResultSet rsSeller = pst.executeQuery();
			
			// Stores Brand & Model, Price and Seller in local Variables
			currentCar = rsSeller.getString("Brand") + " " + rsSeller.getString("Model");
			currentPrice = rsSeller.getString("Price");
			currentSeller = rsSeller.getString("Seller");
											
			pst.close();
			rsSeller.close();
								
		} catch (Exception e3) {
			JOptionPane.showMessageDialog(null, "Please Select a Car!", "Auto Seller", 3, null);
		}
		
		try {
			// Selects the PayPal link from the Car Seller
			String query = "select PayPal from Users where Username = '"+currentSeller+"' ";
			PreparedStatement pst = connection.prepareStatement(query);
			
			ResultSet rsPayPal = pst.executeQuery();
			
			currentPayPal = rsPayPal.getString("PayPal");
									
			pst.close();
			rsPayPal.close();
			
			// Assigns values to public PayNow class member variables for use in creating a PayPal URL string 
			// and for deleting the sold car from the current database, and adding it to the SoldCars database.
			PayNow.payPalLink = currentPayPal;
			PayNow.payCar = currentCar;
			PayNow.payPrice = currentPrice;
			PayNow.payCurrentDatabase = currentDatabase;
			PayNow.payEID = eID;
			PayNow.currentSeller = currentSeller; 
					
			dispose();
			PayNow newPayPage = new PayNow();
			newPayPage.setVisible(true);
			
			
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}
		
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public AutoSellerMain() {
		// Connects to Database
		connection = SqlliteConnection.dbConnector();
		
		// Window Title
		setTitle("Auto Seller | Your Vehicle Marketplace");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane mainPane = new JTabbedPane(JTabbedPane.TOP);
		mainPane.setBounds(5, 5, 790, 566);
		contentPane.add(mainPane);
		
		JScrollPane buyNowScrollPane = new JScrollPane();
		mainPane.addTab("Buy Now", null, buyNowScrollPane, null);
		
		// __ Buy Now panel below __ //
		// Car Table
		buyNowTable = new JTable();
		buyNowTable.setFillsViewportHeight(true);
		buyNowScrollPane.setViewportView(buyNowTable);
		
		JPanel catagoriesPanel = new JPanel();
		buyNowScrollPane.setRowHeaderView(catagoriesPanel);
		catagoriesPanel.setLayout(new BoxLayout(catagoriesPanel, BoxLayout.Y_AXIS));
		
		// Regular Car button
		JButton regCarBtn = new JButton("Regular Car");
		regCarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL QUERY inside string named query
				String query = "select Brand, Model, Color, Seats, Doors, Year, Location, Price, ID from RegularCar";
				fillTable(query);
				currentDatabase = "RegularCar";
			}
		});
		catagoriesPanel.add(regCarBtn);
		
		// SUV button
		JButton sellPanel = new JButton("SUV");
		sellPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL QUERY inside string named query
				String query = "select Brand, Model, TowingCapacity, FourWheelDrive, MiniSuv, SnowTyres, Year, Location, Price, ID from SUV";
				fillTable(query);
				currentDatabase = "SUV";
			}
		});
		catagoriesPanel.add(sellPanel);
		
		// Electric Car button
		JButton electricBtn = new JButton("Electric");
		electricBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL QUERY inside string named query
				String query = "select Brand, Model, BatteryLife, ChargingTime, MilesPerCharge, Year, Location, Price, ID from ElectricCar";
				fillTable(query);
				currentDatabase = "ElectricCar";
			}
		});
		catagoriesPanel.add(electricBtn);
		
		// RV button
		JButton rvBtn = new JButton("RV");
		rvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL QUERY inside string named query
				String query = "select Brand, Model, Rooms, Bathrooms, DoubleDecker, RVClass, Year, Location, Price, ID from Recreational";
				fillTable(query);
				currentDatabase = "Recreational";
			}
		});
		catagoriesPanel.add(rvBtn);
		
		// Sports Car button
		JButton sportsBtn = new JButton("Sports");
		sportsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL QUERY inside string named query
				String query = "select Brand, Model, ZeroToSixty, Horsepower, Turbocharger, Year, Location, Price, ID from SportsCar";
				fillTable(query);
				currentDatabase = "SportsCar";
			}
		});
		catagoriesPanel.add(sportsBtn);
		
		// Buy Now button
		JButton btnBuyNow = new JButton("Buy Now!");
		btnBuyNow.setBackground(Color.WHITE);
		btnBuyNow.setForeground(Color.BLACK);
		btnBuyNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buyCar();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Please Select a Car!", "Auto Seller", 3, null);
				}
					
			}
		});
		catagoriesPanel.add(btnBuyNow);
		
		// __ END OF Buy Now panel  __ //
		
		// __ Sell Car panel below  __ //
		// Sell Car Tab
		JPanel selPanel = new JPanel();
		mainPane.addTab("Sell Car", null, selPanel, null);
		selPanel.setLayout(null);
		
		// Type of Car Being Sold Label
		final JLabel lblCarDetails = new JLabel("");
		lblCarDetails.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblCarDetails.setBounds(381, 20, 284, 36);
		selPanel.add(lblCarDetails);
		
		// Brand Text Field (named textField1 for semantic reasons)
		textField1 = new JTextField();
		textField1.setBounds(381, 97, 157, 28);
		selPanel.add(textField1);
		textField1.setColumns(10);
		
		// Brand Label
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setVerticalAlignment(SwingConstants.TOP);
		lblBrand.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblBrand.setBounds(381, 71, 157, 28);
		selPanel.add(lblBrand);
		
		// Model Text Field 
		textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setBounds(582, 97, 157, 28);
		selPanel.add(textField2);
		
		// Model Label
		JLabel lblModel = new JLabel("Model");
		lblModel.setVerticalAlignment(SwingConstants.TOP);
		lblModel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblModel.setBounds(582, 71, 157, 28);
		selPanel.add(lblModel);
		
		// Year Text Field 
		textField3 = new JTextField();
		textField3.setColumns(10);
		textField3.setBounds(381, 176, 157, 28);
		selPanel.add(textField3);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setVerticalAlignment(SwingConstants.TOP);
		lblYear.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblYear.setBounds(381, 148, 157, 28);
		selPanel.add(lblYear);
		
		// Price Text Field 
		textField4 = new JTextField();
		textField4.setColumns(10);
		textField4.setBounds(582, 176, 157, 28);
		selPanel.add(textField4);
	
		// Price Label
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setVerticalAlignment(SwingConstants.TOP);
		lblPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblPrice.setBounds(582, 148, 157, 28);
		selPanel.add(lblPrice);
		
		// Location Text Field 
		textField5 = new JTextField();
		textField5.setColumns(10);
		textField5.setBounds(381, 252, 157, 28);
		selPanel.add(textField5);
		
		// Location Label
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setVerticalAlignment(SwingConstants.TOP);
		lblLocation.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblLocation.setBounds(381, 226, 157, 28);
		selPanel.add(lblLocation);
		
		// Custom Text Field 1 (value of use depending on type of car being sold)
		textField6 = new JTextField();
		textField6.setToolTipText("");
		textField6.setColumns(10);
		textField6.setBounds(582, 252, 157, 28);
		selPanel.add(textField6);
		
		// Custom Label 1 (text depends on type of car being sold)
		final JLabel lblCarCustom1 = new JLabel("");
		lblCarCustom1.setVerticalAlignment(SwingConstants.TOP);
		lblCarCustom1.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblCarCustom1.setBounds(582, 226, 157, 28);
		selPanel.add(lblCarCustom1);
	
		// Custom Text Field 2 (value of use depending on type of car being sold)
		textField7 = new JTextField();
		textField7.setColumns(10);
		textField7.setBounds(381, 331, 157, 28);
		selPanel.add(textField7);
		
		// Custom Label 2(text depends on type of car being sold)
		final JLabel lblCarCustom2 = new JLabel("");
		lblCarCustom2.setVerticalAlignment(SwingConstants.TOP);
		lblCarCustom2.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblCarCustom2.setBounds(381, 303, 157, 28);
		selPanel.add(lblCarCustom2);
		
		// Custom Text Field 3 (value of use depending on type of car being sold)
		textField8 = new JTextField();
		textField8.setColumns(10);
		textField8.setBounds(582, 331, 157, 28);
		selPanel.add(textField8);
		
		// Custom Label 3 (text depends on type of car being sold)
		final JLabel lblCarCustom3 = new JLabel("");
		lblCarCustom3.setVerticalAlignment(SwingConstants.TOP);
		lblCarCustom3.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblCarCustom3.setBounds(582, 303, 157, 28);
		selPanel.add(lblCarCustom3);
		
		// Custom Text Field 4 (value of use depending on type of car being sold)
		textField9 = new JTextField();
		textField9.setColumns(10);
		textField9.setBounds(381, 410, 157, 28);
		selPanel.add(textField9);
		
		// Custom Label 4 (text depends on type of car being sold)	
		final JLabel lblCarCustom4 = new JLabel("");
		lblCarCustom4.setVerticalAlignment(SwingConstants.TOP);
		lblCarCustom4.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblCarCustom4.setBounds(381, 382, 157, 28);
		selPanel.add(lblCarCustom4);
		
		// Sell Car button
		JButton btnSellCar = new JButton("Sell Car!");
		btnSellCar.addActionListener(new ActionListener() {
			
			/*
			 * Action Performed event on Sell Car button.
			 * This method is activated when the user clicks the buy now button.
			 * The switch statement will run a different sell car method for the car type being sold.
			 */
			public void actionPerformed(ActionEvent e) {
				switch(currentlySelling){
				case 1:
					sellRegularCar(currentlySelling);
				break;
				
				case 2:
					sellElectricCar(currentlySelling);
				break;
				
				case 3:
					sellSUV(currentlySelling);
				break;
				
				case 4:
					sellSportsCar(currentlySelling);			
				break;
				
				case 5:
					sellRV(currentlySelling);
				break;
				
				default:
					JOptionPane.showMessageDialog(null, "Please pick the type of car your selling.");
				break;
	
			}
		}
			
		});
		btnSellCar.setBounds(582, 406, 157, 29);
		selPanel.add(btnSellCar);
		
		// Sports Car button
		JButton btnSportsCar = new JButton("Sports Car");
		
		/**
		 * Action performed event handler.
		 * changes text boxes and labels in the pay now tab 
		 * to values appropriate for the type of car being sold
		 */
		btnSportsCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets Labels and text fields for selling a Sports Car
				lblCarDetails.setText("Sports Car");
				lblCarCustom1.setText("Zero To Sixty (secs)");
				lblCarCustom2.setText("Horsepower");
				lblCarCustom3.setText("Turbocharger (0 or 1)");
				lblCarCustom4.setVisible(false);
				textField9.setVisible(false);
				
				currentlySelling = 4;
			}
		});
		
		btnSportsCar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnSportsCar.setBounds(29, 323, 165, 36);
		selPanel.add(btnSportsCar);
		
		// Regular Car button
		JButton btnRegularCar = new JButton("Regular Car");
		
		/**
		 * Action performed event handler.
		 * changes text boxes and labels in the pay now tab 
		 * to values appropriate for the type of car being sold
		 */
		btnRegularCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					// Sets Labels and text fields for selling a Regular Car
					lblCarDetails.setText("Regular Car");
					lblCarCustom1.setText("Color");
					lblCarCustom2.setText("Doors");
					lblCarCustom3.setText("Seats");
					lblCarCustom4.setVisible(false);
					textField9.setVisible(false);
					
					currentlySelling = 1;
	
			}
			
		});
		
		btnRegularCar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnRegularCar.setBounds(29, 89, 165, 36);
		selPanel.add(btnRegularCar);
		
		// SUV button
		JButton btnSportsUtilityVehicle = new JButton("Sports Utility Vehicle");
		
		/**
		 * Action performed event handler.
		 * changes text boxes and labels in the pay now tab 
		 * to values appropriate for the type of car being sold
		 */
		btnSportsUtilityVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets Labels and text fields for selling a Sports Utility Vehicles
				lblCarDetails.setText("Sports Utility Vehicle");
				lblCarCustom1.setText("Towing Capacity (lbs)");
				lblCarCustom2.setText("Four Wheel Drive (0 or 1)");
				lblCarCustom3.setText("Mini SUV (0 or 1)");
				lblCarCustom4.setVisible(true);
				lblCarCustom4.setText("Snow Tyres (0 or 1)");
				textField9.setVisible(true);
				
				currentlySelling = 3;
			}
		});
		
		btnSportsUtilityVehicle.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnSportsUtilityVehicle.setBounds(29, 244, 165, 36);
		selPanel.add(btnSportsUtilityVehicle);
		
		// RV button
		JButton btnRecreationalVehicle = new JButton("Recreational Vehicle");
		
		/**
		 * Action performed event handler.
		 * changes text boxes and labels in the pay now tab 
		 * to values appropriate for the type of car being sold
		 */
		btnRecreationalVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets Labels and text fields for selling a Recreational Vehicle
				lblCarDetails.setText("Recreational Vehicle");
				lblCarCustom1.setText("Rooms");
				lblCarCustom2.setText("Bathrooms");
				lblCarCustom3.setText("Double Decker (0 or 1)");
				lblCarCustom4.setVisible(true);
				lblCarCustom4.setText("RV Class (A, B or C)");
				textField9.setVisible(true);
				
				currentlySelling = 5;
			}
		});
		
		btnRecreationalVehicle.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnRecreationalVehicle.setBounds(29, 402, 165, 36);
		selPanel.add(btnRecreationalVehicle);
		
		// Electric Car button
		JButton btnElectricCar = new JButton("Electric Car");
		
		/**
		 * Action performed event handler.
		 * changes text boxes and labels in the pay now tab 
		 * to values appropriate for the type of car being sold
		 */
		btnElectricCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets Labels and text fields for selling a Electric Car
				lblCarDetails.setText("Electric Car");
				lblCarCustom1.setText("Battery Life (hrs)");
				lblCarCustom2.setText("Charging Time (hrs)");
				lblCarCustom3.setText("Miles per Charge");
				lblCarCustom4.setVisible(false);
				textField9.setVisible(false);
				
				currentlySelling = 2;
			}
		});
		
		btnElectricCar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnElectricCar.setBounds(29, 168, 165, 36);
		selPanel.add(btnElectricCar);
		
		// Select the type of car your selling label
		JLabel lblSelectTheType = new JLabel("Select the type of vehicle your selling:");
		lblSelectTheType.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblSelectTheType.setBounds(29, 20, 284, 36);
		selPanel.add(lblSelectTheType);
		
		// __ END OF Sell Car panel  __ //
		
		// __ Account panel below  __ //
		// Account panel
		JPanel accountPanel = new JPanel();
		mainPane.addTab("Account", null, accountPanel, null);
		accountPanel.setLayout(null);
		
		// Hi + current users fullname label
		JLabel lblHiThere = new JLabel("Hi " + Login.currentName + ". Welcome back!");
		lblHiThere.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblHiThere.setBounds(22, 20, 718, 24);
		accountPanel.add(lblHiThere);
		
		// Here you can manage your listings and update your info label
		JLabel lblWelcomeBack = new JLabel("Here you can manage your listings and update your info.");
		lblWelcomeBack.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblWelcomeBack.setBounds(22, 56, 718, 24);
		accountPanel.add(lblWelcomeBack);
		
		// ScrollPane for managing listings JTable
		JScrollPane manageListingsPane = new JScrollPane();
		manageListingsPane.setBounds(22, 125, 356, 182);
		accountPanel.add(manageListingsPane);
		
		// JTable for managing listings
		manageListingsTable = new JTable();
		manageListingsPane.setViewportView(manageListingsTable);
		
		// View JButton
		JButton btnViewLisings = new JButton("View");
		btnViewLisings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select ID, Brand, Model, Year, Price from '"+accountCarType+"' where Seller = '"+Login.currentUser+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();	
					
					// DBUtils is class inside rs2.xml.jar imported class
					manageListingsTable.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnViewLisings.setBounds(218, 91, 85, 29);
		accountPanel.add(btnViewLisings);
		
		// Select Car Type JComboBox
		final JComboBox carType = new JComboBox();
		carType.setModel(new DefaultComboBoxModel(new String[] {"Regular Cars", "Electric Cars", "Sports Cars", "Sports Utility Vehicles", "Recreational Vehicles"}));
		carType.addActionListener(new ActionListener() {
			
			/**
			 * Action performed method 
			 * Activated when user selects Car Type from ComboBox
			 * Sets the accountCarType variable to the selected ComboBox item
			 */
			public void actionPerformed(ActionEvent e) {
				switch(carType.getSelectedIndex()){
					case 0:
						accountCarType = "RegularCar";
					break;
					
					case 1:
						accountCarType = "ElectricCar";
					break;
					
					case 2:
						accountCarType = "SportsCar";
					break;
					
					case 3:
						accountCarType = "SUV";
					break;
					
					case 4:
						accountCarType = "Recreational";
					break;
					
					default:
						accountCarType = "RegularCar";
					break;
				}
			}
		});
		
		carType.setBounds(22, 92, 184, 27);
		accountPanel.add(carType);
		
		// Scroll Pane for Sold Cars JTable
		JScrollPane soldCarsPane = new JScrollPane();
		soldCarsPane.setBounds(22, 363, 356, 137);
		accountPanel.add(soldCarsPane);
		
		// Sold Cars JTable
		soldCarsTable = new JTable();
		soldCarsPane.setViewportView(soldCarsTable);
		
		// Sold Cars Label
		JLabel lblSoldCars = new JLabel("Sold Cars");
		lblSoldCars.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSoldCars.setBounds(22, 335, 102, 16);
		accountPanel.add(lblSoldCars);
		
		// Delete Button
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = manageListingsTable.getSelectedRow();
					int eID = (int) (manageListingsTable.getModel().getValueAt(row,0));				
					
					String query = "delete from '"+accountCarType+"' where ID = '"+eID+"' ";
					
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Listing Deleted");
					
					pst.close();
										
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				refreshTable();
			}
		
			
		});
		
		btnDelete.setBounds(299, 91, 84, 29);
		accountPanel.add(btnDelete);
		
		// View Button
		JButton viewSoldCars = new JButton("View");
		viewSoldCars.addActionListener(new ActionListener() {
			
			/**
			 * Action performed method
			 * Activated when user clicks the view button
			 * Shows the Sold Cars for the current user
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select ID, Car, Price, Buyer from SoldCars where Seller = '"+Login.currentUser+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();	
					
					// DBUtils is class inside rs2.xml.jar imported class
					soldCarsTable.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		viewSoldCars.setBounds(293, 330, 85, 29);
		accountPanel.add(viewSoldCars);
		
		// Manage Account Label
		JLabel lblManageAccount = new JLabel("Manage Account");
		lblManageAccount.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblManageAccount.setBounds(429, 90, 155, 28);
		accountPanel.add(lblManageAccount);
		
		// Firstname Text Box
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(429, 149, 147, 28);
		accountPanel.add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		// Firstname Label
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(429, 126, 85, 16);
		accountPanel.add(lblFirstname);
		
		// Surname Text Box
		surnameTxt = new JTextField();
		surnameTxt.setColumns(10);
		surnameTxt.setBounds(593, 148, 147, 28);
		accountPanel.add(surnameTxt);
		
		// Surname Label
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(593, 125, 85, 16);
		accountPanel.add(lblSurname);
		
		// Street1 Text Box
		streettxt1 = new JTextField();
		streettxt1.setColumns(10);
		streettxt1.setBounds(429, 222, 147, 28);
		accountPanel.add(streettxt1);
		
		// Street Label
		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(429, 199, 85, 16);
		accountPanel.add(lblStreet);
		
		// Street2 Text Box
		streettxt2 = new JTextField();
		streettxt2.setColumns(10);
		streettxt2.setBounds(593, 221, 147, 28);
		accountPanel.add(streettxt2);
		
		// Street (line2) Label
		JLabel lblStreetline = new JLabel("Street (line 2)");
		lblStreetline.setBounds(593, 198, 85, 16);
		accountPanel.add(lblStreetline);
		
		// Town Text Box
		townText = new JTextField();
		townText.setColumns(10);
		townText.setBounds(429, 299, 147, 28);
		accountPanel.add(townText);
		
		// Town Label
		JLabel lblTown = new JLabel("Town");
		lblTown.setBounds(429, 276, 85, 16);
		accountPanel.add(lblTown);
		
		// PayPal Text Box
		payPalTxt = new JTextField();
		payPalTxt.setColumns(10);
		payPalTxt.setBounds(593, 298, 147, 28);
		accountPanel.add(payPalTxt);
		
		// PayPal Label
		JLabel lblPayPalEmail = new JLabel("Pay Pal Email");
		lblPayPalEmail.setBounds(593, 275, 85, 16);
		accountPanel.add(lblPayPalEmail);
		
		// Password Password Field
		passwordField = new JPasswordField();
		passwordField.setBounds(429, 370, 147, 28);
		accountPanel.add(passwordField);
		
		// New Password Label
		JLabel lblNewLabel = new JLabel("New Password");
		lblNewLabel.setBounds(429, 349, 125, 16);
		accountPanel.add(lblNewLabel);
		
		// Update Account Button
		JButton btnNewButton = new JButton("Update Account");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * Action performed event
			 * Activated when Update Account is clicked
			 * Updates account information for the current user
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "update Users set Firstname = '"+firstnameTxt.getText()+"', Surname = '"+surnameTxt.getText()+"', Street = '"+streettxt1.getText()+"', Street2 = '"+streettxt2.getText()
							+"', Town = '"+townText.getText()+"', PayPal = '"+payPalTxt.getText()+"', Password = '"+passwordField.getText()+"' where Username='"+Login.currentUser+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();	
					JOptionPane.showMessageDialog(null, "Account Updated");
					
					pst.close();
										
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		btnNewButton.setBounds(593, 371, 147, 29);
		accountPanel.add(btnNewButton);
		
		// Load info Button
		JButton loadAccInfo = new JButton("Load Info");
		loadAccInfo.addActionListener(new ActionListener() {
			
			/**
			 * Action performed event handler
			 * Activated when Load Info button is Clicked
			 * Loads account info for current user
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from Users where Username = '"+Login.currentUser+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while (rs.next()){
						firstnameTxt.setText(rs.getString("Firstname"));
						surnameTxt.setText(rs.getString("Surname"));
						streettxt1.setText(rs.getString("Street"));
						streettxt2.setText(rs.getString("Street2"));
						townText.setText(rs.getString("Town"));
						payPalTxt.setText(rs.getString("PayPal"));
						passwordField.setText(rs.getString("Password"));
					}
									
					pst.close();
										
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		
		loadAccInfo.setBounds(623, 92, 117, 29);
		accountPanel.add(loadAccInfo);
		
		// Refreshes Account Tables
		refreshTable();
		refreshSoldTable(); 
		
		// __ END OF Account panel __ //
	}
}
