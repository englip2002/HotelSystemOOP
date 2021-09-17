
public class RoomType implements Reportable{
	private String name;
	private int numberOfSingleBeds;
	private int numberOfDoubleBeds;
	private double pricePerNight;
	
	// Constructors
	public RoomType(String name, int numberOfSingleBeds, int numberOfDoubleBeds, double pricePerNight) {
		this.name = name;
		this.numberOfSingleBeds = numberOfSingleBeds;
		this.numberOfDoubleBeds = numberOfDoubleBeds;
		this.pricePerNight = pricePerNight;
	}

	// Methods	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoomType) {
			return this.name.equals( ((RoomType)obj).getName() );
		}
		return false;
	}
	
	@Override
	public String generateReport() {
		return String.format("%-15s | RM%13.2f |", name, pricePerNight);
	}
	
	
	// Getters
	public String getName() {
		return name;
	}

	public int getNumberOfSingleBeds() {
		return numberOfSingleBeds;
	}	
	
	public int getNumberOfDoubleBeds() {
		return numberOfDoubleBeds;
	}	

	public double getPricePerNight() {
		return pricePerNight;
	}	
}
