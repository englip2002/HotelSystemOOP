import java.util.ArrayList;

public class Room implements Reportable{
	private int roomNumber;
	private RoomType roomType;
	private ArrayList<ReservationSchedule> reservedDays;

	public Room(int roomNumber, RoomType roomType) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
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
				return;
			}
		}
	}
	
	public boolean validateReservationSchedule(ReservationSchedule schedule) {
		for (ReservationSchedule each : reservedDays) {
			if (each.compareTo(schedule.getStartDate()) == 0 || each.compareTo(schedule.getEndDate()) == 0) {
				return false;
			}
		}
		return true;
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

}
