import java.time.LocalDate;

public class Customer {
    private String customerID;
    private String customerName;
    private String dateOfBirth;
    private String customerPassword;
    private String customerEmail;
    
    public Customer(String customerID, String customerName, String dateOfBirth, String customerPassword, String customerEmail){
        this.customerID = customerID;
        this.customerName =customerName;
        this.dateOfBirth = dateOfBirth;
        this.customerPassword = customerPassword;
        this.customerEmail = customerEmail;
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
}
