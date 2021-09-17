    public class PaymentByCard extends Payment {
        private static String paymentType = "Card";
        private String cardNumber;
        private static double chargePercentage = 0.01;
        private Bank bank;
        private int CVV;
        private String cardType;

        public PaymentByCard(double subtotal) {
            super(subtotal);
        };

        //-------------------------getter setter------------------------------------------
        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            validateCardType();
        }

        public void setBank(Bank bank) {
            this.bank = bank;
        }

        public void setCVV(int cvv) {
            this.CVV = cvv;
        }

        public Bank getBank(){
            return bank;
        }

        //-----------------------methods-----------------------------------

        //assign card type (visa or mastercard)
        private void validateCardType() {
            if (cardNumber.charAt(0) == '4')
                cardType = "Visa";
            else
                cardType = "Master Card";
        }

        public double calculateCharge() {
            return getSubtotal() * chargePercentage;
        }

        @Override
        public void calculateTotalAmount() {
            totalAmount = calculateCharge();
            super.calculateTotalAmount();
        }

        public boolean validCard() {
            if (cardNumber.length() == 16) {
                if (CVV < 100 || CVV >= 1000)
                    return false;

                for (int i = 0; i < cardNumber.length(); i++){
                    if(!Character.isDigit(cardNumber.charAt(i))){
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
            return "\n------------------------------------------------------\n"
                    + "                    PAYMENT RECEIPT                  \n"
                    + "------------------------------------------------------\n"
                    + String.format("%s                                     %s\n\n", paymentId, paymentDate)
                    + String.format("Payment Method                        %16s\n", paymentType)
                    + String.format("Card Number                           %16s\n", cardNumber)
                    + String.format("Card Type                             %16s\n", cardType)
                    + String.format("Bank                                  %16s\n\n", bank.getBankName())
                    + String.format("Subtotal                              %16.2f\n", subtotal)
                    + String.format("Tax Amount (%d%%)                     %18.2f\n", (int) (taxRate * 100), getTaxAmount())
                    + String.format("Charge     (%d%%)                     %18.2f\n", (int) (chargePercentage * 100), calculateCharge())
                    + String.format("Total Amount                          %16.2f\n", totalAmount)
                    + "------------------------------------------------------";
        }
    }
