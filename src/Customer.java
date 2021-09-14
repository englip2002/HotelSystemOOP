import java.util.ArrayList;

public class Customer {
	// Customer is here to avoid compile error, waiting for sengwai to upload the complete ver.
	private ArrayList<Reservation> reservations;
	
	Customer() {
		reservations = new ArrayList<>();
	}
	
	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
	
	public void cancelReservation(String reservationID, Block block) {
		for (Reservation each: reservations) {
			if (each.getReservationID().equals(reservationID)) {
				each.cancel(block);
				break;
			}
		}
	}
	
	public ArrayList<Reservation> getReservationList() {
		return reservations;
	}
}
