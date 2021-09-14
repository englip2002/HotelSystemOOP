import java.util.ArrayList;

public class Room implements Reportable{
	private int roomNumber;
	private RoomType roomType;
	private ArrayList<ReservationSchedule> reservedDays;
	private static int totalNumberOfRooms = 0;

	public Room(int roomNumber, RoomType roomType) {
		this.roomNumber = roomNumber;
		this.setRoomType(roomType);
		totalNumberOfRooms++;
		reservedDays = new ArrayList<ReservationSchedule>();
	}

	public void addReservationSchedule(ReservationSchedule schedule) {
		if (this.validateReservationSchedule(schedule)) {
			reservedDays.add(schedule);	
		}
	}
	
	public void removeReservationSchedule(ReservationSchedule schedule) {
		for (int i = 0; i < reservedDays.size(); i++) {
			if (reservedDays.get(i).equals(schedule)) {
				reservedDays.remove(i);
				break;
			}
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
		return String.format("%03d ", roomNumber) + roomType.toString();
	}
	
	@Override
	public String generateReport() {
		return String.format("| %7d | ", roomNumber) + roomType.generateReport() + "\n";
	}

	// Getters
	public int getRoomNumber() {
		return roomNumber;
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
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
}
