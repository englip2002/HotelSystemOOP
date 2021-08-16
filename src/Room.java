
public class Room {
	private int roomNumber;
	private Floor floor;
	private boolean isReserved;
	private RoomType roomType;
	
	public Room(Floor floor, int roomNumber, RoomType roomType) {
		this.roomNumber = roomNumber;
		this.setRoomType(roomType);
	}
	
	@Override
	public String toString() {
		return "Room Number: " + this.getRoomNumber() + this.floor + this.getRoomType() + "\nIs reserved: " + this.isReserved();
	}

	// Getters
	public int getRoomNumber() {
		return roomNumber;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public RoomType getRoomType() {
		return roomType;
	}
	
	public Floor getFloor() {
		return floor;
	}

	// Setters
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
}
