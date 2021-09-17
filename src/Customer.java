import java.util.ArrayList;

public class Customer implements Reportable{
    private String customerID;
    private String customerName;
    private LocalDate dateOfBirth;
    private String customerPassword;
    private String customerEmail;
    private ArrayList<Reservation> reservations;
    private static int customerCount=0;
    
    public Customer(String customerName, LocalDate dateOfBirth, String customerPassword, String customerEmail){
        this.customerID = String.format("C%04d", customerCount);
        this.customerName =customerName;
        this.dateOfBirth = dateOfBirth;
        this.customerPassword = customerPassword;
        this.customerEmail = customerEmail;
        reservations = new ArrayList<>();
        customerCount++;
    }
	
	@Override
    public String generateReport() {
        return String.format(
                "\n+------------------------------------------------+\n"
                + "|             View Customer Profile              |\n"
                + "+------------------------------------------------+\n"
                + "| ID            | %-30s |\n"
                + "| Name          | %-30s |\n"
                + "| Date of Birth | %-30s |\n"
                + "| Email         | %-30s |\n"
                + "+------------------------------------------------+\n"
                , customerID, customerName, dateOfBirth, customerEmail);
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
    
    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }

    public static int getCustomerCount() {
        return customerCount;
    }
}
