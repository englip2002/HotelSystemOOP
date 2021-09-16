import java.time.LocalDate;

public abstract class Payment implements Reportable {
    protected static int paymentCount = 0;
    protected String paymentId;
    protected LocalDate paymentDate;
    protected static double taxRate = 0.06;
    protected double taxAmount;
    protected double subtotal;
    protected double totalAmount = 0;
    protected String paymentStatus;



    //------------------Constructors--------------------
    public Payment(){};

    protected Payment(double subtotal){
        paymentDate = LocalDate.now();
        paymentStatus = "Completed";
        paymentCount++;
        paymentId = String.format("P%06d", paymentCount);
        this.subtotal = subtotal;
    }


    //---------------------Getters-----------------------
    public double getSubtotal() {
        return subtotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }


    //---------------------Methods------------------------
    public void calculateTaxAmount(){
        taxAmount = subtotal * taxRate;
    }

    public void calculateTotalAmount(){
        totalAmount += (subtotal + taxAmount);
    }

    public abstract String generateReceipt();

    public double refund() {
        paymentStatus = "Refunded";
        return  totalAmount;
    }

    @Override
    public String generateReport() {
        return  "\n+----------------------------------------------------+" +
                "\n|                   PAYMENT REPORT                   |" +
                "\n+-----------------+-----------------+----------------+" +
                "\n| Payment ID      | Amount          | Status         |" +
                "\n+-----------------+-----------------+----------------+" +
                String.format("\n| %-15s | RM%-13.2f | %-14s |", paymentId, totalAmount, paymentStatus) +
                "\n+-----------------+-----------------+----------------+" +
                "\n\nThe following is the payment receipt." + generateReceipt() + "\n";
    }
}
