import java.util.ArrayList;

public class Customer {
    private String customerID;
    private String customerName;
    private String dateOfBirth;
    private String customerPassword;
    private String customerEmail;
    private ArrayList<Reservation> reservations;
    private static int customerCount=0;
    
    public Customer(String customerName, String dateOfBirth, String customerPassword, String customerEmail){
        this.customerID = String.format("C%04d", customerCount);
        this.customerName =customerName;
        this.dateOfBirth = dateOfBirth;
        this.customerPassword = customerPassword;
        this.customerEmail = customerEmail;
        reservations = new ArrayList<>();
        customerCount++;
    }
    
    public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
	
	public void cancelReservation(String reservationID) {
		for (Reservation each: reservations) {
			if (each.getReservationID().equals(reservationID)) {
				each.cancel();
				break;
			}
		}
	}
	
	public ArrayList<Reservation> getReservationList() {
		return reservations;
	}
    
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public static int getCustomerCount() {
        return customerCount;
    }
}
