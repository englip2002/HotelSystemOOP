public class PaymentByCard extends Payment {
    private static String paymentType = "Card";
    private String cardNumber;
    private static double chargePercentage = 0.01;
    private Bank bank;
    private int CVV;
    private String cardType;

    public PaymentByCard() {
    };

    public PaymentByCard(double subtotal, String cardNumber, Bank bank, int cvv) {
        super(subtotal);
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.CVV = cvv;
        validateCardType();
    }

    //-------------------------getter setter------------------------------------------
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank(){
        return bank;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int cvv) {
        this.CVV = cvv;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    //-----------------------methods-----------------------------------

    //assign card type (visa or mastercard)
    private void validateCardType() {
        if (cardNumber.charAt(0) == '4')
            cardType = "Visa";
        else
            cardType = "MasterCard";
    }

    public double calculateCharge() {
        return getSubtotal() * chargePercentage;
    }

    public void addCharge() {
        setSubtotal(getSubtotal() + calculateCharge());
    }

    public boolean validCard() {
        if (cardNumber.length() == 12) {
            if (CVV >= 100 && CVV < 1000)
                return true;
            else
                return false;
        } else
            return false;
    }

   @Override
   public String generateReceipt() {
       return "\n------------------------------------------------------\n"
               + "                    PAYMENT RECEIPT                  \n"
               + "------------------------------------------------------\n"
               + String.format("%s                   %s\n\n", getPaymentId(), getPaymentDate().getTime())
               + String.format("Payment Method                         %15s\n", paymentType)
               + String.format("Card Number                            %15s\n", cardNumber)
               + String.format("Card Type                              %15s\n", cardType)
               + String.format("Bank                                   %15s\n\n", bank.getBankName())
               + String.format("Subtotal                               %15.2f\n", getSubtotal())
               + String.format("Tax Amount (%d%%)                        %15.2f\n", (int) (getTaxRate() * 100),
               getTaxAmount())
               + String.format("Total Amount                           %15.2f\n", getTotalAmount())
               + "------------------------------------------------------";
   }

    

}
