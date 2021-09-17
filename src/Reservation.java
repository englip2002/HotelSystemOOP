import java.util.ArrayList;

public class Reservation implements Reportable {
	private static int newestReservationNo = 0;
	private String reservationID;
	private Customer customer;
	private ReservationSchedule schedule;
	private ArrayList<Room> reservedRooms;
	private String orderID;
	private Payment payment;
	private boolean isCancelled;
	
	// Constructors
	public Reservation(Customer customer, ReservationSchedule schedule, ArrayList<Room> reservedRooms,
			String orderID, Payment payment) {
		// Auto-calculate reservation ID
		newestReservationNo++;
		reservationID = "R" + String.format("%07d", newestReservationNo);

		this.customer = customer;
		this.schedule = schedule;
		this.reservedRooms = reservedRooms;
		this.orderID = orderID;
		this.payment = payment;
		
		this.isCancelled = false;
	}

	// Methods
	public void cancel() {
		this.isCancelled = true;
		for (Room r: reservedRooms) {
			r.removeReservationSchedule(schedule);
		}
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
		double total = 0;
		for (Room room: reservedRooms) {
			output += room.generateReport();
			total += room.getRoomType().getPricePerNight();
		}		
        output += "+---------------------------------------------+\n"
	            + "|           TOTAL (1 night) | RM" + String.format("%13.2f", total ) + " |\n"
	            + "+---------------------------------------------+\n"
	            + String.format("|  GRAND TOTAL (%3d nights) | RM%13.2f |\n",
	            schedule.getDaysBetween(), total  * schedule.getDaysBetween())
	            + "+---------------------------------------------+\n";
        
		return output;
	}
	
	public String generateTableRow() {
		String output = " " + reservationID + " | "
				+ schedule.generateTableRow();
		if (isCancelled)
			output += String.format("%-9s |", "Cancelled");
		else
			output += String.format("%-9s |", "Completed");
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
	
	public ReservationSchedule getReservationSchedule() {
		return schedule;
	}
	
	public boolean getIsCancelled() {
		return isCancelled;
	}

	public String getOrderID() {
		return orderID;
	}
	
	public Payment getPayment() {
		return payment;
	}
}
