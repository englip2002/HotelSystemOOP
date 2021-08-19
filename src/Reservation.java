import java.util.ArrayList;
import java.time.LocalDate;

public class Reservation {
	private static int newestReservationNo = 0;
	private String reservationID;
	private Customer reservedCustomer;
	private ReservationSchedule schedule;
	private ArrayList<Room> reservedRooms;
	private Payment payment;
	private boolean isCancelled;

	public Reservation(Customer reservedCustomer, int startYear, int startMonth, int startDay, 
			int endYear, int endMonth, int endDay, ArrayList<Room> reservedRooms, Payment payment) {
		
		// Auto-calculate reservation ID
		newestReservationNo++;
		reservationID = "R" + String.format("%010d", newestReservationNo);
		
		this.reservedCustomer = reservedCustomer;
		this.schedule = new ReservationSchedule(LocalDate.of(startYear, startMonth, startDay), LocalDate.of(endYear, endMonth, endDay));
		this.reservedRooms = new ArrayList<Room>(reservedRooms);
		this.payment = payment;
		this.isCancelled = false;
	}
	
	// Methods
	public void cancel() {
		this.isCancelled = true;
	}

	public String listOfReservedRooms() {
		String result = "";
		for (Room room: reservedRooms) {
			result += room.getRoomNumber() + "(" + room.getRoomType().getName() + "), ";
		}
		return result.substring(0, result.length()-2);
	}
	
	
	
	// Getters
	public String getReservationID() {
		return reservationID;
	}

	public Customer getReservedCustomer() {
		return reservedCustomer;
	}

	public ReservationSchedule getSchedule() {
		return schedule;
	}
	
	
	public ArrayList<Room> getReservedRooms() {
		return reservedRooms;
	}

	public Payment getPayment() {
		return payment;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	// Setters
	public void setReservedCustomer(Customer reservedCustomer) {
		this.reservedCustomer = reservedCustomer;
	}
}
