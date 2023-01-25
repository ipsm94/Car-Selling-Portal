package cars;

import java.util.Scanner;

public final class RegularCar extends Car {
	
	Scanner console = new Scanner(System.in);

	// Member Variables
	private String color;
	private int doors;
	private int seats;
	
	// Constructors
		public RegularCar(){	
			super();
			this.sellRegularCar();
		}
		
		public RegularCar(String brand, String model, int year, String location, double price, String color, int doors, int seats){
			super(brand, model, year, location, price);
			this.setRegularCar(color, doors, seats);
		}

		// Member Methods
		private void setRegularCar(String color, int doors, int seats) {
			setColor(color);
			setDoors(doors);
			setSeats(seats);
		}
		
		public void sellRegularCar() {
			System.out.println("Color: ");
			setColor(console.nextLine());
			
			System.out.println("Doors: ");
			setDoors(Integer.parseInt(console.nextLine()));
			
			System.out.println("Seats: ");
			setSeats(Integer.parseInt(console.nextLine()));
			
		}

		// set methods
		public void setColor(String color) {
			this.color = color;
		}

		public void setDoors(int doors) {
			this.doors = doors;
		}

		public void setSeats(int seats) {
			this.seats = seats;
		}
		
		// get methods
		public String getColor() {
			return this.color;
		}

		public int getDoors() {
			return this.doors;
		}

		public int getSeats() {
			return this.seats;
		}
		
		public String toString(){
			return String.format("%s%nColor %s%nDoors: %d%nSeats: %d", super.toString(), getColor(), getDoors(), getSeats());
		}

}
