package autoseller;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Auto Seller - Sign Up Class
 * This class is used to create a new user account.
 * The accounts are stored in the Users table of the AutoSeller.sqlite database.
 * The class displays the Login frame once the user clicks the Sign Up button.
 */
public class SignUp extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Variables	
	private JTextField usernameTxt;
	private JTextField firstnameTxt;
	private JTextField surnameTxt;
	private JTextField street1Txt;
	private JTextField street2Txt;
	private JTextField cityTownTxt;
	private JTextField payPalTxt;
	private JPasswordField passwordField;
	
	// Initializes Connection object "connection"
	static Connection connection = null;

	/**
	 * Create the frame.
	 */
	public SignUp() {
		// Connects to Database
		connection = SqlliteConnection.dbConnector();
		
		// Window Title
		setTitle("Auto Seller | Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 480);
		getContentPane().setLayout(null);
		
		// Window Heading
		JLabel lblAutoSeller = new JLabel("Auto Seller | sign up");
		lblAutoSeller.setVerticalAlignment(SwingConstants.TOP);
		lblAutoSeller.setHorizontalAlignment(SwingConstants.LEFT);
		lblAutoSeller.setFont(new Font("Lucida Grande", Font.BOLD, 19));
		lblAutoSeller.setBounds(29, 21, 285, 28);
		getContentPane().add(lblAutoSeller);
		
		// Username Label
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblUsername.setBounds(29, 64, 101, 16);
		getContentPane().add(lblUsername);
		
		// Password Label
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblPassword.setBounds(180, 61, 101, 16);
		getContentPane().add(lblPassword);
		
		// Password Text
		passwordField = new JPasswordField();
		passwordField.setBounds(180, 92, 134, 28);
		getContentPane().add(passwordField);
		
		// Username Text
		usernameTxt = new JTextField();
		usernameTxt.setBounds(29, 92, 134, 28);
		getContentPane().add(usernameTxt);
		usernameTxt.setColumns(10);
		
		// Firstname Text
		firstnameTxt = new JTextField();
		firstnameTxt.setBounds(29, 176, 134, 28);
		getContentPane().add(firstnameTxt);
		firstnameTxt.setColumns(10);
		
		// Surname Text
		surnameTxt = new JTextField();
		surnameTxt.setBounds(180, 176, 134, 28);
		getContentPane().add(surnameTxt);
		surnameTxt.setColumns(10);
		
		// Street Line 1 Text
		street1Txt = new JTextField();
		street1Txt.setBounds(29, 253, 134, 28);
		getContentPane().add(street1Txt);
		street1Txt.setColumns(10);
		
		// Firstname Label
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(29, 148, 86, 16);
		getContentPane().add(lblFirstname);
		
		// Street Line 2 Text
		street2Txt = new JTextField();
		street2Txt.setBounds(180, 253, 134, 28);
		getContentPane().add(street2Txt);
		street2Txt.setColumns(10);
		
		// Surname Label
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(180, 148, 61, 16);
		getContentPane().add(lblSurname);
		
		// Street Line 1 Label
		JLabel lblStreet1 = new JLabel("Street ");
		lblStreet1.setBounds(29, 228, 61, 16);
		getContentPane().add(lblStreet1);
		
		// Street Line 2 Label
		JLabel lblStreet2 = new JLabel("Street Line 2 (optional)");
		lblStreet2.setBounds(180, 228, 159, 16);
		getContentPane().add(lblStreet2);
		
		// City/ Town Label
		JLabel lblCityTown = new JLabel("City/ Town");
		lblCityTown.setBounds(29, 308, 101, 16);
		getContentPane().add(lblCityTown);
		
		// City/ Town Text
		cityTownTxt = new JTextField();
		cityTownTxt.setBounds(29, 339, 134, 28);
		getContentPane().add(cityTownTxt);
		cityTownTxt.setColumns(10);
		
		// PayPal Label
		JLabel lblPaypal = new JLabel("PayPal Email Address");
		lblPaypal.setBounds(180, 311, 134, 16);
		getContentPane().add(lblPaypal);
		
		// PayPal Text
		payPalTxt = new JTextField();
		payPalTxt.setBounds(180, 339, 134, 28);
		getContentPane().add(payPalTxt);
		payPalTxt.setColumns(10);
		
		// Sign Up Button
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			
			/**
			 * Sign Up Method
			 * Adds data from textboxes to Users table in the database.
			 * Disposes current Sign Up frame and shows Login frame.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					// Create SQL query in "query" string
					String query = "insert into Users (Username, Password, Firstname, Surname, Street, Street2, Town, Paypal) values (?,?,?,?,?,?,?,?)";
					
					// Create Prepared Statement "pst"
					PreparedStatement pst = connection.prepareStatement(query);
					
					// Gets user input from Text Fields and puts in pst
					pst.setString(1, usernameTxt.getText());
					pst.setString(2, passwordField.getText());
					pst.setString(3, firstnameTxt.getText());
					pst.setString(4, surnameTxt.getText());
					pst.setString(5, street1Txt.getText());
					pst.setString(6, street2Txt.getText());
					pst.setString(7, cityTownTxt.getText());
					pst.setString(8, payPalTxt.getText());
					
					pst.execute();
					
					// Shows "Account created" message in Dialogue box
					JOptionPane.showMessageDialog(null, "Account Created");
					
					pst.close();
					
					// Disposes current JFrame and Displays the main AutoSeller JFrame
					dispose();
					Login newLogIn = new Login();
					newLogIn.frmAutoSeller.setVisible(true);
					
										
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		btnSignUp.setBounds(29, 399, 117, 29);
		getContentPane().add(btnSignUp);
		
		// Already have an account? Label
		JLabel lblAlreadyHaveAn = new JLabel("Already have an account?");
		lblAlreadyHaveAn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblAlreadyHaveAn.setBounds(180, 399, 144, 16);
		getContentPane().add(lblAlreadyHaveAn);
		
		// Sign in here Label
		JLabel lblNewLabel = new JLabel("Sign in here.");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			
			/**
			 * Sign in Method
			 * Redirects user back to Login page.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// Disposes Sign Up frame and displays the main login Frame
				dispose();
				Login newLogIn = new Login();
				newLogIn.frmAutoSeller.setVisible(true);
			}
		});
		
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel.setBounds(180, 412, 86, 16);
		getContentPane().add(lblNewLabel);

	}
}
