
public class RoomType {
	private String name;
	private int numberOfTwinBeds;
	private int numberOfKingBeds;
	private double pricePerNight;
	private int totalNumberOfRooms;
	
	public RoomType() {
		this("", 0, 0, 0);
	}
	
	public RoomType(String name, int numberOfTwinBeds, int numberOfKingBeds, double pricePerNight) {
		this.setName(name);
		this.setNumberOfTwinBeds(numberOfTwinBeds);
		this.setNumberOfKingBeds(numberOfKingBeds);
		this.setPricePerNight(pricePerNight);
		this.totalNumberOfRooms = 0;
	}

//	@Override
//	public String toString() {
//		return name;
//	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoomType) {
			return this.name.equals( ((RoomType)obj).getName() );
		}
		return false;
	}
	
	public void addRoom() {
		this.totalNumberOfRooms++;
	}
	
	// Getters
	public String getName() {
		return name;
	}

	public int getNumberOfTwinBeds() {
		return numberOfTwinBeds;
	}	
	
	public int getNumberOfKingBeds() {
		return numberOfKingBeds;
	}	

	public double getPricePerNight() {
		return pricePerNight;
	}
	
	public int getTotalNumberOfRooms() {
		return totalNumberOfRooms;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNumberOfTwinBeds(int numberOfTwinBeds) {
		this.numberOfTwinBeds = numberOfTwinBeds;
	}
	
	public void setNumberOfKingBeds(int numberOfKingBeds) {
		this.numberOfKingBeds = numberOfKingBeds;
	}
	
	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}	
}
