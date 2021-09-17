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
            for(int i = 0; i < pinNumber.length(); i++){
                if(!Character.isDigit(pinNumber.charAt(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
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
                String.format("Total Amount                                  %8.2f\n", totalAmount) +
                "------------------------------------------------------";
    }
}
