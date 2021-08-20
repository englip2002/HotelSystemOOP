import java.time.LocalDate;

public abstract class Payment {
    private static int paymentCount = 0;
    private String paymentId;
    private LocalDate paymentDate;
    private static double taxRate = 0.06;
    private double taxAmount;
    private double subtotal;
    private double totalAmount = 0;
    private String paymentStatus;



    //------------------Constructors--------------------
    public Payment(double subtotal){
        paymentDate = LocalDate.now();
        paymentStatus = "Completed";
        paymentCount++;
        paymentId = String.format("P%06d", paymentCount);
        this.subtotal = subtotal;
    }


    //---------------------Getters-----------------------
    public String getPaymentId() {
        return paymentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public static double getTaxRate() {
        return taxRate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }



    //---------------------Setters-----------------------
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public static void setTaxRate(double taxRate) {
        Payment.taxRate = taxRate;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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
}
