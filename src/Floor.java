
public class Floor {
	private int floorNumber;
	private int numberOfRooms;
	private int maxNumberOfRooms;
	private Room[] rooms;
	
	public Floor(int floorNumber, int maxNumberOfRooms) {
		this.floorNumber = floorNumber;
		this.maxNumberOfRooms = maxNumberOfRooms;
		this.rooms = new Room[maxNumberOfRooms];
		this.numberOfRooms = 0;
	}
	
	public void addRoom(Room room) {
		if (numberOfRooms >= maxNumberOfRooms) {
			System.out.println("This floor is full already. Cannot add more rooms. ");
			return;
		}
		for (int i = 0; i < numberOfRooms; i++) {
			if (rooms[i].getRoomNumber() == room.getRoomNumber()) {
				System.out.println("Room number " + room.getRoomNumber() + " already exist!");
				return;
			}
		}
		this.rooms[numberOfRooms] = room;
		numberOfRooms++;
	}
	
	public void addRooms(Room[] rooms) {
		for (Room room: rooms) {
			if (numberOfRooms >= maxNumberOfRooms) {
				System.out.println("This floor is full already. Cannot add more rooms. ");
				return;
			}
			addRoom(room);
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

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public Room[] getRooms() {
		return rooms;
	}
}
