public class PaymentByCard {
    private static String paymentType = "Card";
    private String cardNumber;
    private static double chargePercentage = 0.01;
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

    public double calculateCharge(){
        //return subtotal*chargePercentage;   
    }

    public void addCharge(){

    }

    public String generateReciepe(){
        return String.format("%s %s %s %d %s", paymentType,cardNumber,bank,cvv,cardType);
    }
}
