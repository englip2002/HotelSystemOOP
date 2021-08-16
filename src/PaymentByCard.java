public class PaymentByCard extends Payment {
    private static String paymentType = "Card";
    private String cardNumber;
    private static double chargePercentage = 0.01;
    private Bank bank;
    private int cvv;
    private String cardType;

    public PaymentByCard() {
    };

    public PaymentByCard(double subtotal, String cardNumber, Bank bank, int cvv) {
        super(subtotal);
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvv = cvv;
        validateCardType();
    }

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

    @Override
    public void calculateTotalAmount(){
       
        super.setTotalAmount(super.getTotalAmount()+getSubtotal()*chargePercentage);
    }

    @Override
    public String generateReceipt() {
        return "\n------------------------------------------------------\n"
                + "                    PAYMENT RECEIPT                  \n"
                + "------------------------------------------------------\n"
                + String.format("%s                   %s\n\n", getPaymentId(), getPaymentDate().getTime())
                + String.format("Payment Method                                %-8s\n", paymentType)
                + String.format("Card Number                                   %-8s\n", cardNumber)
                + String.format("Card Type                                     %-12s\n", cardType)
                + String.format("Bank                                          %-8s\n", bank.getBankName())
                + String.format("Subtotal                                      %-8.2f\n", getSubtotal())
                + String.format("Tax Amount (%d%%)                             %-8.2f\n", (int) (getTaxRate() * 100), getTaxAmount())
                + String.format("Total Amount                                  %-8.2f\n\n", getTotalAmount())
                + "------------------------------------------------------";
    }
}
