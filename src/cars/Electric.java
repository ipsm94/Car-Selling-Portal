package cars;

public final class Electric extends Car {
	
	// Member Variables
	private int batteryLife;
	private int chargingTime;
	private int milesPerCharge;
	
	// Constructors
		public Electric(){	
			super();
			this.sellElectric();
		}
		
		public Electric(String brand, String model, int year, String location, double price, int batteryLife, int chargingTime, int milesPerCharge){
			super(brand, model, year, location, price);
			this.setElectric(batteryLife, chargingTime, milesPerCharge);
		}

		// Member Methods
		private void setElectric(int batteryLife, int chargingTime, int milesPerCharge) {
			setBatteryLife(batteryLife);
			setChargingTime(chargingTime);
			setMilesPerCharge(milesPerCharge);
		}
		
		public void sellElectric() {
			System.out.println("Battery Life: ");
			setBatteryLife(Integer.parseInt(console.nextLine()));
			
			System.out.println("Charging Time: ");
			setChargingTime(Integer.parseInt(console.nextLine()));
			
			System.out.println("Miles Per Charge: ");
			setMilesPerCharge(Integer.parseInt(console.nextLine()));
			
		}

		// set methods
		public void setBatteryLife(int batteryLife) {
			this.batteryLife = batteryLife;
		}

		public void setChargingTime(int chargingTime) {
			this.chargingTime = chargingTime;
		}

		public void setMilesPerCharge(int milesPerCharge) {
			this.milesPerCharge = milesPerCharge;
		}
		
		// get methods
		public int getBatteryLife() {
			return this.batteryLife;
		}

		public int getChargingTime() {
			return this.chargingTime;
		}

		public int getMilesPerCharge() {
			return this.milesPerCharge;
		}
		
		public String toString(){
			return String.format("%s%nBattery Life: %d%nCharging Time: %d%nMiles Per Charge: %d", super.toString(), getBatteryLife(), getChargingTime(), getMilesPerCharge());
		}

}
