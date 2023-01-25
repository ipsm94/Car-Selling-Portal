package cars;

public final class RecreationalVehicle extends Car{
	
	// Member Variables
	private int rooms;
	private int bathrooms;
	private boolean doubleDecker;
	private char rvClass;
	
	// Constructors
	public RecreationalVehicle (){
		super();
		this.sellRv();
	}
	
	public RecreationalVehicle (String brand, String model, int year, String location, double price, int rooms, int bathrooms, boolean doubleDecker, char rvClass){
		super(brand, model, year, location, price);
		this.setRv(rooms, bathrooms, doubleDecker, rvClass);
	}

	// Member Methods
	private void setRv(int rooms, int bathrooms, boolean doubleDecker, char rvClass){
		setRooms(rooms);
		setBathrooms(bathrooms);
		setDoubleDecker(doubleDecker);
		setRvClass(rvClass);
	}
	
	public void sellRv() {
		System.out.println("Rooms: ");
		setRooms(Integer.parseInt(console.nextLine()));
		
		System.out.println("Bathrooms: ");
		setBathrooms(Integer.parseInt(console.nextLine()));
		
		System.out.println("Double Decker? ");
		setDoubleDecker(Boolean.parseBoolean(console.nextLine()));
		
		System.out.println("RV Class: ");
		setRvClass(console.nextLine().charAt(0));
	}
	
	// set methods
	private void setRooms(int rooms) {
		this.rooms = rooms;
	}

	private void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}
	
	private void setDoubleDecker(boolean doubleDecker) {
		this.doubleDecker = doubleDecker;
	}
	
	public void setRvClass(char rvClass) {
		this.rvClass = rvClass;
	}
	
	// get methods
	public int getRooms() {
		return this.rooms;
	}

	public int getBathrooms() {
		return this.bathrooms;
	}

	public boolean getDoubleDecker() {
		return this.doubleDecker;
	}

	public char getRvClass() {
		return this.rvClass;
	}
	
	public String toString(){
		return String.format("%s%nRooms: %d%nBathrooms: %d%nDouble Decker?: %b%nRV Class: %c", super.toString(), getRooms(), getBathrooms(), getDoubleDecker(), getRvClass());
	}
	
}
