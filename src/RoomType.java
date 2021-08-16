
public class RoomType {
	private String name;
	private int numberOfBeds;
	private double pricePerStay;
	
	public RoomType() {
		this("", 0, 0);
	}
	
	public RoomType(String name, int numberOfBeds, double pricePerStay) {
		this.setName(name);
		this.setNumberOfBeds(numberOfBeds);
		this.setPricePerStay(pricePerStay);
	}

	@Override
	public String toString() {
		return "\nRoom Type: " + name + "\nNumber of beds: " + numberOfBeds + "\nPrice per Stay: " + pricePerStay;
	}
	
	// Getters
	public String getName() {
		return name;
	}

	public int getNumberOfBeds() {
		return numberOfBeds;
	}	

	public double getPricePerStay() {
		return pricePerStay;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNumberOfBeds(int numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
	}
	
	public void setPricePerStay(double pricePerStay) {
		this.pricePerStay = pricePerStay;
	}	
}
