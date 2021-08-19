import java.util.ArrayList;

public class Room {
	private int roomNumber;
	private boolean isReserved;
	private RoomType roomType;
	private ArrayList<ReservationSchedule> reservedDays;
	private static int totalNumberOfRooms = 0;

	public Room(int roomNumber, RoomType roomType) {
		this.roomNumber = roomNumber;
		this.setRoomType(roomType);
		totalNumberOfRooms++;
		roomType.addRoom();
		reservedDays = new ArrayList<ReservationSchedule>();
	}

	public void addReservationSchedule(ReservationSchedule schedule) {
		if (this.validateReservationSchedule(schedule)) {
			reservedDays.add(schedule);	
		}
	}
	
	public boolean validateReservationSchedule(ReservationSchedule schedule) {
		for (ReservationSchedule each : reservedDays) {
			if (! ( ( schedule.getStartDate().isBefore(each.getStartDate()) && schedule.getEndDate().compareTo(each.getStartDate()) <= 0 )
					&& ( schedule.getStartDate().compareTo(each.getStartDate()) >= 0 && schedule.getEndDate().isAfter(each.getStartDate()) ))
					) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Room Number: " + this.getRoomNumber() + this.getRoomType() + "\nIs reserved: " + this.isReserved();
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

	public static int getTotalNumberOfRooms() {
		return totalNumberOfRooms;
	}
	
	public ArrayList<ReservationSchedule> getReservedDays() {
		return reservedDays;
	}

	// Setters
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
}
