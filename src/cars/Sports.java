package cars;

public final class Sports extends Car {
	
	// Member Variables
	private double zeroToSixty;
	private int horsepower;
	private boolean turbocharger;
	
	// Constructors
	public Sports(){	
		super();
		this.sellSports();
	}
	
	public Sports(String brand, String model, int year, String location, double price, double zeroToSixty, int horsepower, boolean turbocharger){
		super(brand, model, year, location, price);
		this.setSports(zeroToSixty, horsepower, turbocharger);
	}

	// Member Methods
	private void setSports(double zeroToSixty, int horsepower, boolean turbocharger) {
		setZeroToSixty(zeroToSixty);
		setHorsepower(horsepower);
		setTurbocharger(turbocharger);
	}
	
	public void sellSports() {
		System.out.println("Zero to sixty: ");
		setZeroToSixty(Double.parseDouble(console.nextLine()));
		
		System.out.println("Horsepower: ");
		setHorsepower(Integer.parseInt(console.nextLine()));
		
		System.out.println("Has Turbocharger? ");
		setTurbocharger(Boolean.parseBoolean(console.nextLine()));
	}

	// set methods
	public void setZeroToSixty(double zeroToSixty) {
		this.zeroToSixty = zeroToSixty;
	}

	public void setHorsepower(int horsepower) {
		this.horsepower = horsepower;
	}

	public void setTurbocharger(boolean turbocharger) {
		this.turbocharger = turbocharger;
	}

	// get methods
	public double getZeroToSixty() {
		return this.zeroToSixty;
	}

	public int getHorsepower() {
		return this.horsepower;
	}

	public boolean getTurbocharger() {
		return this.turbocharger;
	}
	
	public String toString(){
		return String.format("%s%nZero to Sixty: %.1f (seconds)%nHorsepower: %d%nHas Turbocharger? %b%n", super.toString(), getZeroToSixty(), getHorsepower(), getTurbocharger());
	}

}
