package cars;

import autoseller.AutoSellerMain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import net.proteanit.sql.DbUtils;

public abstract class Car {
	
	Scanner console = new Scanner(System.in);
	
	// Member Variables
	private String brand;
	private String model;
	private int year;
	private String location;
	private double price;
	
	// Initializes Connection object "connection"
	static Connection connection = null;
	// may need to add connect to database method - connection = SqlliteConnection.dbConnector();
	
	// Constructors
	public Car(){
		sellCar();
	}

	public Car(String brand, String model, int year, String location, double price ){
		setCar(brand, model, year, location, price);
	}
	
	// Member Methods
	public void setCar(String brand, String model, int year, String location, double price) {
		setBrand(brand);
		setModel(model);
		setYear(year);
		setLocation(location);
		setPrice(price);
	}

	public void sellCar() {
		System.out.println("Vehicle Brand: ");
		setBrand(console.nextLine());
		
		System.out.println("Vehicle Model: ");
		setModel(console.nextLine());
		
		System.out.println("Vehicle Year: ");
		setYear(Integer.parseInt(console.nextLine())); // consumes next line if input, console.nextInt() will not
		
		System.out.println("Selling Location: ");
		setLocation(console.nextLine());
		
		System.out.println("Vehicle Price: ");
		setPrice(Double.parseDouble(console.nextLine()));
	}
	
	// set methods
	public void setBrand(String brand) {
		this.brand = brand;	
	}

	public void setModel(String model) {
		this.model = model;	
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void setLocation(String location) {
		// Add boolean (use my location) option
		this.location = location;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	// get methods
	public String getBrand() {
		return this.brand;	
	}

	public String getModel() {
		return this.model;	
	}
	
	public int getYear() {
		return this.year;
	}

	public String getLocation() {
		return this.location;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String toString(){
		return String.format("Brand: %s%nModel: %s%nYear: %d%nLocation: %s%nPrice: $%.2f", getBrand(), getModel(), getYear(), getLocation(), getPrice());
	}
	
	
}
