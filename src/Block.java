
public class Block {
	private int blockNumber;
	private int numberOfFloors;
	private int maxNumberOfFloors;
	private Floor[] floors;
	
	public Block(int blockNumber, int maxNumberOfFloors) {
		this.blockNumber = blockNumber;
		this.maxNumberOfFloors = maxNumberOfFloors;
		this.floors = new Floor[maxNumberOfFloors];
		numberOfFloors = 0;
	}
	
	public void addFloor(Floor floor) {
		if (numberOfFloors < maxNumberOfFloors) {
			floors[numberOfFloors] = floor;
			numberOfFloors++;
		}
		else {
			System.out.println("Block " + blockNumber + " is full of floors already. Cannot add more floors. ");
		}
	}

	public void removeFloor(int floorNumber) {
		boolean found = false;
		for (int i = 0; i < maxNumberOfFloors; i++) {
			if (floors[i].getFloorNumber() == floorNumber) {
				found = true;
				floors[i] = null;
				for (int j = i; j < maxNumberOfFloors - 1; j++) {
					floors[j] = floors[j + 1];
					floors[j + 1] = null;
				}
				break;
			}
		}
		if (!found) {
			System.out.println("Unable to find Floor Number " + floorNumber + " at Block " + blockNumber + "!");
		}
	}
	
	// Getters
	public int getBlockNumber() {
		return blockNumber;
	}
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public Floor[] getFloors() {
		return floors;
	}
	
	// Setters
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}
	
}
