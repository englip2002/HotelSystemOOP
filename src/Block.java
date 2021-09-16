import java.util.ArrayList;

public class Block {
	private String blockID;
	private int numberOfFloors;
	private int maxNumberOfFloors;
	private Floor[] floors;
	
	public Block(String blockID, int maxNumberOfFloors) {
		this.blockID = blockID;
		this.maxNumberOfFloors = maxNumberOfFloors;
		this.floors = new Floor[maxNumberOfFloors];
		numberOfFloors = 0;
	}
	
	public void addFloor(Floor floor) {
		if (numberOfFloors >= maxNumberOfFloors) {
			System.out.println("This block is full already. Cannot add more floors.");
			return;
		}
		for (int i = 0; i < numberOfFloors; i++) {
			if (floors[i].getFloorNumber() == floor.getFloorNumber()) {
				System.out.println("Floor number " + floor.getFloorNumber() + " already exist. ");
				return;
			}
		}
		floors[numberOfFloors] = floor;
		numberOfFloors++;
	}
	
	public void addFloors(Floor[] floors) {
		for (Floor floor: floors) {
			if (numberOfFloors >= maxNumberOfFloors) {
				System.out.println("This block is full already. Cannot add more floors.");
				return;
			}
			addFloor(floor);
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
			System.out.println("Unable to find Floor Number " + floorNumber + " at Block " + blockID + "!");
		}
	}
	
	
	// Getters
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public Floor[] getFloors() {
		return floors;
	}
	
}
