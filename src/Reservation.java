import java.util.ArrayList;
import java.time.LocalDate;

public class Reservation implements Reportable {
	private static int newestReservationNo = 0;
	private String reservationID;
	private Customer reservedCustomer;
	private ReservationSchedule schedule;
	private ArrayList<Room> reservedRooms;
	private String orderID;
	private Payment payment;
	private boolean isCancelled;

	public Reservation(Customer reservedCustomer, int startYear, int startMonth, int startDay, int endYear,
			int endMonth, int endDay, ArrayList<Room> reservedRooms, String orderID, Payment payment) {
		this(reservedCustomer, LocalDate.of(startYear, startMonth, startDay), LocalDate.of(endYear, endMonth, endDay),
				reservedRooms, orderID, payment);
	}

	public Reservation(Customer reservedCustomer, LocalDate startDate, LocalDate endDate, ArrayList<Room> reservedRooms,
			String orderID, Payment payment) {
		this(reservedCustomer, new ReservationSchedule(startDate, endDate), reservedRooms, orderID, payment);
	}
	
	public Reservation(Customer reservedCustomer, ReservationSchedule schedule, ArrayList<Room> reservedRooms,
			String orderID, Payment payment) {
		// Auto-calculate reservation ID
		newestReservationNo++;
		reservationID = "R" + String.format("%07d", newestReservationNo);

		this.reservedCustomer = reservedCustomer;
		this.schedule = schedule;
		this.reservedRooms = reservedRooms;
		this.orderID = orderID;
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

	@Override
	public String generateReport() {
		String output = "\n        Reservation Report for " + reservationID + "\n"
				+ schedule.generateReport()
				+ "        Reservation Status: ";
		
		if (isCancelled) {
			output += "Cancelled\n";
		}
		else {
			output += "Completed\n";
		}
		
		output += "+---------------------------------------------+\n"
				+ "| Room No |    Room Type    | Price Per Night |\n"
				+ "+---------------------------------------------+\n";
		for (Room room: reservedRooms) {
			output += room.generateReport();
		}
		output += "+---------------------------------------------+\n"
				+ String.format("|                     TOTAL | RM%13.2f |\n",  payment.getTotalAmount())
				+ "+---------------------------------------------+\n";
		
		
		return output;
	}
	
	
	public String generateTableRow() {
		String output = " " + reservationID + " | "
				+ schedule.generateTableRow();
		if (isCancelled)
			output += String.format("%-8s |", "Cancelled");
		else
			output += String.format("%-8s |", "Active");
		return output;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Reservation) {
			return (((Reservation)obj).getReservationID().equals(reservationID) );
		}
		return false;
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

	public String getOrderID() {
		return orderID;
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
