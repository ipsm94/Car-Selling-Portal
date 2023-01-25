package autoseller;

import java.awt.Desktop;
import java.awt.BorderLayout;

import autoseller.AutoSellerMain;
import autoseller.Login;

import java.awt.EventQueue;

import autoseller.AutoSellerMain;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Auto Seller - PayNow Class
 * This PayNow JFrame is displayed when the user selects a car and clicks the buy now button.
 * The class provides a button that allows the user to pay for the car using PayPal.
 * The button will open PayPal up in the users default web browser.
 * 
 * PayPal will have the name of the car that the user selected in the order details.
 * PayPal will also have the price of the car that the user selected in the price details.
 * The PayPal account that the money will be sent to if the user finalizes the process 
 * is that of the car seller (i.e - the PayPal account of the user that sold the car).
 */
public class PayNow extends JFrame {

	// Variables
	private JPanel contentPane;
	public static String payPalLink;
	public static String payCar;
	public static String payPrice;
	public static String payCurrentDatabase;
	public static String payEID;
	protected static String currentSeller;
	
	// Initializes Connection object "connection"
	static Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayNow frame = new PayNow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Methods
	/**
	 * Pay With Pay Pal Method
	 * Creates a custom payment URL for PayPal (see - https://developer.paypal.com/docs/integration/web/)
	 * 
	 * PayPal will have the name of the car that the user selected in the order details.
	 * PayPal will also have the price of the car that the user selected in the price details.
	 * The PayPal account that the money will be sent to if the user finalizes the process 
	 * is that of the car seller (i.e - the PayPal account of the user that sold the car).
	 */
	public void payWithPayPal(){
		URI payPalURI = null;
        try {
        	// Creates PayPal URI for the Car being bought that includes the price and the sellers PayPal handle
        	payPalURI = new URI(("https://www.paypal.com/cgi-bin/webscr?business=" +payPalLink+ "&cmd=_xclick&currency_code=EUR&amount=" +payPrice+ "&item_name=" +payCar).replaceAll("\\s+","%20"));
            Desktop.getDesktop().browse(payPalURI);
        } catch(IOException ioe) {
            System.out.println("The system cannot find the PayPal user " + payPalURI);
            //ioe.printStackTrace();
        } catch(URISyntaxException use) {
            System.out.println("Illegal character in path");
            System.out.println(payPalURI);
            //use.printStackTrace();
        }
	}
	
	/**
	 * Pay With Car Sold Method
	 * Removes car from its current database and inserts car into SoldCars database
	 */
	public void carSold() {
		try {
			// Inserts sold car into the SoldCars database
			String query = "insert into SoldCars (ID, Car, Price, Seller, Buyer) values (?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, payEID);
			pst.setString(2, payCar);
			pst.setString(3, payPrice);
			pst.setString(4, currentSeller);
			pst.setString(5, Login.currentUser);
			
			pst.execute();	
			pst.close();
			
								
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		try {
			// Deletes sold car from its current database
			String query = "delete from " +payCurrentDatabase+ " where ID = '"+payEID+"' ";
			PreparedStatement pst = connection.prepareStatement(query);
			
			pst.execute();					
			pst.close();
								
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public PayNow() {
		// Connects to Database
		connection = SqlliteConnection.dbConnector();
		
		// Pay Now Title
		setTitle("Auto Seller | Pay Now");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 290);
		
		// Pay Now Panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// You purchaes a... label
		JLabel payPalHeading = new JLabel("You purchased a " + payCar + " for " + payPrice + "!");
		payPalHeading.setHorizontalAlignment(SwingConstants.CENTER);
		payPalHeading.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		payPalHeading.setBounds(6, 18, 438, 39);
		contentPane.add(payPalHeading);
		
		// Click on the button below to pay online with PayPal label
		JLabel lblPayPal = new JLabel("Click on the button below to pay online with PayPal");
		lblPayPal.setBounds(60, 69, 344, 42);
		contentPane.add(lblPayPal);
		
		// Pay Now Button
		JButton payNowBtn = new JButton("Pay Now");
		payNowBtn.addActionListener(new ActionListener() {
			/**
			 * Action Performed.
			 * Activated when user click Pay Now button
			 * Runs payWithPayPal() & carSold() methods
			 */
			public void actionPerformed(ActionEvent e) {
				payWithPayPal();
				carSold();
			}
		});
		
		payNowBtn.setBounds(50, 107, 117, 29);
		contentPane.add(payNowBtn);
		
		// Finished Paying? Click below to continue shopping... label
		JLabel lblFinishedPayingClick = new JLabel("Finished Paying? Click below to continue shopping...");
		lblFinishedPayingClick.setBounds(60, 164, 338, 16);
		contentPane.add(lblFinishedPayingClick);
		
		// Continue Shopping Button
		JButton continueShoppingBtn = new JButton("Continue Shopping");
		continueShoppingBtn.addActionListener(new ActionListener() {
			
			/**
			 * Action performed
			 * Activated when user clicks continue shopping button
			 * Disposes current frame and loads AutoSellerMain frame
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
				AutoSellerMain newAutoSellerMain = new AutoSellerMain();
				newAutoSellerMain.setVisible(true);
			}
			
		});
		
		continueShoppingBtn.setBounds(50, 192, 155, 29);
		contentPane.add(continueShoppingBtn);
	}

}
