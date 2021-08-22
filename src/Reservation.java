import java.util.ArrayList;
import java.time.LocalDate;

public class Reservation {
	private static int newestReservationNo = 0;
	private String reservationID;
	private Customer reservedCustomer;
	private ReservationSchedule schedule;
	private ArrayList<Room> reservedRooms;
	private FoodOrder foodOrder;
	private Payment payment;
	private boolean isCancelled;

	public Reservation(Customer reservedCustomer, int startYear, int startMonth, int startDay, int endYear,
			int endMonth, int endDay, ArrayList<Room> reservedRooms, FoodOrder foodOrder, Payment payment) {
		this(reservedCustomer, LocalDate.of(startYear, startMonth, startDay), LocalDate.of(endYear, endMonth, endDay),
				reservedRooms, foodOrder, payment);
	}

	public Reservation(Customer reservedCustomer, LocalDate startDate, LocalDate endDate, ArrayList<Room> reservedRooms,
			FoodOrder foodOrder, Payment payment) {
		this(reservedCustomer, new ReservationSchedule(startDate, endDate), reservedRooms, foodOrder, payment);
	}
	
	public Reservation(Customer reservedCustomer, ReservationSchedule schedule, ArrayList<Room> reservedRooms,
			FoodOrder foodOrder, Payment payment) {
		// Auto-calculate reservation ID
		newestReservationNo++;
		reservationID = "R" + String.format("%010d", newestReservationNo);

		this.reservedCustomer = reservedCustomer;
		this.schedule = schedule;
		this.reservedRooms = reservedRooms;
		this.foodOrder = foodOrder;
		this.payment = payment;
		this.isCancelled = false;
	}

	// Methods
	public void cancel() {
		this.isCancelled = true;
	}

	public String listOfReservedRooms() {
		String result = "";
		for (Room room : reservedRooms) {
			result += room.getRoomNumber() + "(" + room.getRoomType().getName() + "), ";
		}
		return result.substring(0, result.length() - 2);
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

	public FoodOrder getFoodOrder() {
		return foodOrder;
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
