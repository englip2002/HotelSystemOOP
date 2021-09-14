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
	
	public void removeReservation(String reservationID) {
		for (Reservation each: reservations) {
			if (each.getReservationID().equals(reservationID)) {
				reservations.remove(each);
				break;
			}
		}
	}
	
	public ArrayList<Reservation> getReservationList() {
		return reservations;
	}
}
