
public class Floor {
	private int floorNumber;
	private int numberOfRooms;
	private int maxNumberOfRooms;
	private Room[] rooms;
	
	
	public Floor(int floorNumber, int maxNumberOfRooms) {
		this.setFloorNumber(floorNumber);
		this.maxNumberOfRooms = maxNumberOfRooms;
		this.rooms = new Room[maxNumberOfRooms];
		this.numberOfRooms = 0;
	}
	
	public void addRoom(Room room) {
		if (numberOfRooms < maxNumberOfRooms) {
			this.rooms[numberOfRooms] = room;
			numberOfRooms++;
		}
		else {
			System.out.println("This floor is full already. Cannot add more rooms. ");
		}
	}
	
	public void removeRoom(int roomNumber) {
		boolean found = false;
		for (int i = 0; i < maxNumberOfRooms; i++) {
			if (rooms[i].getRoomNumber() == roomNumber) {
				found = true;
				rooms[i] = null;
				for (int j = i; j < maxNumberOfRooms - 1; j++) {
					rooms[j] = rooms[j + 1];
					rooms[j + 1] = null;
				}
				break;
			}
		}
		if (!found) {
			System.out.println("Unable to find Room Number " + roomNumber + " at Floor " + floorNumber + "!");
		}
	}

	public int getFloorNumber() {
		return floorNumber;
	}


	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}


	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	
	public int getMaxNumberOfRooms() {
		return maxNumberOfRooms;
	}


	public Room[] getRooms() {
		return rooms;
	}
	
	public Room getRoom(int roomNumber) {
		for (Room room : rooms) {
			if (room.getRoomNumber() == roomNumber) {
				return room;
			}
		}
		return null;
	}
	
}
