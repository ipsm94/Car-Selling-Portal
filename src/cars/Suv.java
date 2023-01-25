package cars;

public final class Suv extends Car {
	
	// Member Variables
	private int towingCapacity;
	private boolean fourWheelDrive;
	private boolean miniSuv;
	private boolean snowTyres;
	
	// Constructors
	public Suv(){	
		super();
		this.sellSuv();
	}
	
	public Suv(String brand, String model, int year, String location, double price, boolean snowTyres, boolean miniSuv, boolean fourWheelDrive, int towingCapacity){
		super(brand, model, year, location, price);
		this.setSuv(snowTyres, miniSuv, fourWheelDrive, towingCapacity);
	}

	// Member Methods
	private void setSuv(boolean snowTyres, boolean miniSuv, boolean fourWheelDrive, int towingCapacity) {
		setSnowTyres(snowTyres);
		setMiniSuv(miniSuv);
		setFourWheelDrive(fourWheelDrive);
		setTowingCapacity(towingCapacity);
	}
	
	public void sellSuv() {
		System.out.println("Towing Capacity: ");
		setTowingCapacity(Integer.parseInt(console.nextLine()));
		
		System.out.println("Is Four Wheel Drive? ");
		setFourWheelDrive(Boolean.parseBoolean(console.nextLine()));
		
		System.out.println("Mini SUV? ");
		setMiniSuv(Boolean.parseBoolean(console.nextLine()));
		
		System.out.println("Snow Tyres? ");
		setSnowTyres(Boolean.parseBoolean(console.nextLine()));
	}

	// set methods
	public void setSnowTyres(boolean snowTyres) {
		this.snowTyres = snowTyres;
	}

	public void setMiniSuv(boolean miniSuv) {
		this.miniSuv = miniSuv;
	}

	public void setFourWheelDrive(boolean fourWheelDrive) {
		this.fourWheelDrive = fourWheelDrive;
	}

	public void setTowingCapacity(int towingCapacity) {
		this.towingCapacity = towingCapacity;
	}
	
	// get methods
	public boolean getSnowTyres() {
		return this.snowTyres;
	}

	public boolean getMiniSuv() {
		return this.miniSuv;
	}

	public boolean getFourWheelDrive() {
		return this.fourWheelDrive;
	}

	public int getTowingCapacity() {
		return this.towingCapacity;
	}
	
	public String toString(){
		return String.format("%s%nHas Snow Tyres?: %b%nIs Mini SUV?: %b%nHas Four Wheel Drive?: %b%nTowing Capacity: %d (tonnes)", super.toString(), getSnowTyres(), getMiniSuv(), getFourWheelDrive(), getTowingCapacity());
	}

}
