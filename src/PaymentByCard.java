public class PaymentByCard {
    private static String paymentType = "Card";
    private String cardNumber;
    private Bank bank;
    private int cvv;
    private String cardType;

    public PaymentByCard(){};

    public PaymentByCard(String cardNumber, Bank bank, int cvv) {
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

    public String toString(){
        return String.format("%s %s %s %d %s", paymentType,cardNumber,bank,cvv,cardType);
    }
}
