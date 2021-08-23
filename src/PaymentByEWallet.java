public class PaymentByEWallet extends Payment {
    private static String paymentType = "E-Wallet";
    private String pinNumber;

    //----------------Constructors-------------------
    public PaymentByEWallet(double subtotal){
        super(subtotal);
    }

    //-------------------Setter----------------------
    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    //-------------------Methods---------------------
    public boolean validatePinNumber(){
        if(pinNumber.length() == 6) {
            for(int i = 1; i < pinNumber.length(); i++){
                return Character.isDigit(pinNumber.charAt(i));
            }
        }
        return false;
    }

    @Override
    public String generateReceipt() {
        return  "\n------------------------------------------------------\n" +
                "                    PAYMENT RECEIPT                  \n" +
                "------------------------------------------------------\n" +
                String.format("%s                                     %s\n\n", getPaymentId(), getPaymentDate()) +
                String.format("Payment Method                                %8s\n\n", paymentType) +
                String.format("Subtotal                                      %8.2f\n", getSubtotal()) +
                String.format("Tax Amount (%d%%)                               %8.2f\n",(int)(getTaxRate() * 100), getTaxAmount()) +
                String.format("Total Amount                                  %8.2f\n", getTotalAmount()) +
                "------------------------------------------------------";
    }
}
