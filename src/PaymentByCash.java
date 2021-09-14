
public class PaymentByCash extends Payment {
    private static String paymentType = "Cash";
    private double totalReceived;
    private double change;


    //----------------Constructors-------------------
    public PaymentByCash(double subtotal){
        super(subtotal);
    }


    //-------------------Getters----------------------
    public double getTotalReceived() {
        return totalReceived;
    }

    public double getChange() {
        return change;
    }



    //-------------------Setters----------------------
    public void setTotalReceived(double totalReceived) {
        this.totalReceived = totalReceived;
    }



    //--------------------Methods----------------------
    public void calculateChange(){
        change = totalReceived - totalAmount;
    }

    @Override
    public String generateReceipt() {
        return  "\n------------------------------------------------------\n" +
                "                    PAYMENT RECEIPT                  \n" +
                "------------------------------------------------------\n" +
                String.format("%s                                     %s\n\n", paymentId, paymentDate) +
                String.format("Payment Method                                %8s\n\n", paymentType) +
                String.format("Subtotal                                      %8.2f\n", subtotal) +
                String.format("Tax Amount (%d%%)                               %8.2f\n",(int)(taxRate * 100), taxAmount) +
                String.format("Total Amount                                  %8.2f\n\n", totalAmount) +
                "Total Received                                " + String.format("%8.2f\n", totalReceived) +
                "Change                                        " + String.format("%8.2f\n", change) +
                "------------------------------------------------------";
    }

    public boolean validateTotalReceived(){
        return !(totalReceived < totalAmount);
    }
}
