package autoseller;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
 * Auto Seller - Login Class
 * The Login class is the entry point to the Auto Seller Application.
 * The class allows users to sign in by comparing details entered with Users in the database.
 * If it is the first time a user is using they can click on a link to sign up and then login once completed.
 * The program also implements the AutoSellerException class in one of the methods.
 * 
 * Some of the public variables such as currentUser and CurrentName are accessed in other classes
 * or are used to assign values to other the member variables of other classes in the autoseller package.
 */
public class Login {

	public JFrame frmAutoSeller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmAutoSeller.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Variables
	private JTextField txtUsername;
	private JPasswordField passwordField;
	public static String currentUser;
	public static String currentName;
	
	// Initializes Connection object "connection"
	static Connection connection = null;

	/**
	 * Create the application.
	 */
	public Login() {
		
		initialize();
		
		// Connects to Database
		connection = SqlliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// JFrame
		frmAutoSeller = new JFrame();
		frmAutoSeller.setTitle("Auto Seller | Login");
		frmAutoSeller.setResizable(false);
		frmAutoSeller.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		frmAutoSeller.setBounds(100, 100, 280, 335);
		frmAutoSeller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutoSeller.getContentPane().setLayout(null);
		
		// Window Heading
		JLabel lblAutoSeller = new JLabel("Auto Seller | login");
		lblAutoSeller.setVerticalAlignment(SwingConstants.TOP);
		lblAutoSeller.setHorizontalAlignment(SwingConstants.LEFT);
		lblAutoSeller.setFont(new Font("Lucida Grande", Font.BOLD, 19));
		lblAutoSeller.setBounds(42, 22, 199, 28);
		frmAutoSeller.getContentPane().add(lblAutoSeller);
		
		// Username
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblUsername.setBounds(42, 65, 101, 16);
		frmAutoSeller.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtUsername.setBounds(41, 85, 200, 28);
		frmAutoSeller.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		// Password
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblPassword.setBounds(43, 127, 101, 16);
		frmAutoSeller.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		passwordField.setBounds(42, 147, 200, 28);
		frmAutoSeller.getContentPane().add(passwordField);
		
		// Sign In Button
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			
			/**
			 * Sign In Method
			 * Uses action performed event handler to compare values in text fields with 
			 * values for Users in the database. Includes error handing for incorrect info 
			 * and for duplicates in database.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from Users where Username = ? and Password = ? ";
					
					PreparedStatement pst = connection.prepareStatement(query);  
					
					pst.setString(1, txtUsername.getText());
					
					pst.setString(2, passwordField.getText());
				
					ResultSet rs = pst.executeQuery();
					
					int count = 0;
					while (rs.next()) {
						count =+1;
						
					}
					// If username and password are correct closes current frame and opens up main frame
					if (count == 1)
					{
						// Sets the current User for use throughout the package
						currentUser = txtUsername.getText();
						
						try {
							// Selects the Name of Current User from the Users table
							String currentquery = "select Firstname, Surname from Users where Username = '"+currentUser+"'";
							PreparedStatement currentpst = connection.prepareStatement(currentquery);
							
							ResultSet rscurrent = currentpst.executeQuery();
							
							currentName = rscurrent.getString("Firstname") + " " + rscurrent.getString("Surname") ;
													
							currentpst.close();
							rscurrent.close();
							
						} catch (Exception e3){
							e3.printStackTrace();
						}
						
						frmAutoSeller.dispose();
						AutoSellerMain newAutoSellerMain = new AutoSellerMain();
						newAutoSellerMain.setVisible(true);
					} 
					// If there is a duplicate username and password in the system displays following
					else if (count > 1) {
						JOptionPane.showMessageDialog(null, "Duplicate username and password");
					} 
					// If username and password are incorrect displays following
					else {
						throw new AutoSellerException("Username and Password is not correct Try again..");
					}
					
					rs.close();
					pst.close();
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		
		btnSignIn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnSignIn.setBounds(41, 189, 85, 29);
		frmAutoSeller.getContentPane().add(btnSignIn);
		
		// Don't have an account - Sign up link
		JLabel noAccountlbl = new JLabel("Dont have an account? - ");
		noAccountlbl.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		noAccountlbl.setBounds(42, 252, 144, 16);
		frmAutoSeller.getContentPane().add(noAccountlbl);
		
		JLabel signUplbl = new JLabel("Sign Up");
		signUplbl.addMouseListener(new MouseAdapter() {
			
			/**
			 * Sign Up Method
			 * Disposes Login JFrame and displays the SignUp JFrame.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				frmAutoSeller.dispose();
				SignUp newSignUp = new SignUp();
				newSignUp.setVisible(true);
			}
		});
		
		signUplbl.setBounds(187, 252, 52, 16);
		frmAutoSeller.getContentPane().add(signUplbl);
	}
}
