import java.util.Scanner;

public class testPayment {
    public static void paymentByCash(double subtotal) {
        Scanner scanner = new Scanner(System.in);
        PaymentByCash paymentByCash = new PaymentByCash(subtotal);

        paymentByCash.calculateTaxAmount();
        paymentByCash.calculateTotalAmount();

        System.out.println("\n============== PAY BY CASH ===============");
        System.out.printf (" Total Amount : %.2f\n",paymentByCash.getTotalAmount());
        System.out.println("------------------------------------------");
        System.out.print(" Input cash > ");
        paymentByCash.setTotalReceived(scanner.nextDouble());

        while(!paymentByCash.validateTotalReceived()){
            System.out.println(" Insufficient cash!!");
            System.out.print(" Add cash > ");
            paymentByCash.setTotalReceived(paymentByCash.getTotalReceived() + scanner.nextDouble());
        }

        paymentByCash.calculateChange();

        System.out.printf("\n Total received : %.2f\n", paymentByCash.getTotalReceived());
        System.out.printf(" Change         : %.2f\n", paymentByCash.getChange());
        System.out.println("\nPayment Completed...");

        System.out.print("\nPress enter to view receipt");
        scanner.nextLine();
        scanner.nextLine();

        System.out.println(paymentByCash.generateReceipt());
    }

    public static void paymentByCard(double subtotal){
        Scanner scanner = new Scanner(System.in);
        PaymentByCard paymentByCard = new PaymentByCard();
        Bank[] bank = new Bank[5];
        bank[0] = new Bank("Maybank");
        bank[1] = new Bank("Public Bank");
        bank[2] = new Bank("Hong Leong Bank");
        bank[3] = new Bank("AmBank");
        bank[4] = new Bank("CIMB Bank");
        //123
        System.out.println("\n============== PAY BY CARD ===============");
        System.out.println("Bank List:");
        for (int i = 0; i < bank.length; i++) {
            System.out.printf("\t[%d] %s\n", i+1, bank[i].getBankName());
        }
        System.out.print("\nSelect a bank > ");
        Bank selectedBank = new Bank(bank[scanner.nextInt()-1].getBankName());

        System.out.print("Card Number: ");
        String cardNum = scanner.next();

        System.out.print("CVV        : ");
        int cvv = scanner.nextInt();

        paymentByCard = new PaymentByCard(subtotal, cardNum, selectedBank, cvv);

        while (!paymentByCard.validCard()){
            System.out.println("Invalid card!!\n");
            System.out.print("Card Number: ");
            paymentByCard.setCardNumber(scanner.next());

            System.out.print("CVV        : ");
            paymentByCard.setCVV(scanner.nextInt());
        }

        System.out.print("Press enter to request the OTP number");
        scanner.nextLine();
        scanner.nextLine();

        selectedBank.generateOTPNumber();
        System.out.println("\n+-----------------+");
        System.out.printf ("| OTP No : %s |\n", selectedBank.getOtpNumber());
        System.out.println("+-----------------+");
        System.out.print  ("Enter OTP No : ");

        while (!selectedBank.validateOTPNumber(scanner.next())) {
            System.out.println("Invalid PAC No!!");
            System.out.println("Press enter to request OTP Number again.");
            scanner.nextLine();
            scanner.nextLine();
            selectedBank.generateOTPNumber();
            System.out.println("+-----------------+");
            System.out.printf ("| OTP No : %s |\n", selectedBank.getOtpNumber());
            System.out.println("+-----------------+");
            System.out.print  ("Enter OTP No : ");
        }

        System.out.println("Valid OTP No..");
        System.out.println("Payment Completed...");

        paymentByCard.calculateTaxAmount();
        paymentByCard.addCharge();
        paymentByCard.calculateTotalAmount();

        System.out.print("\nPress enter to view receipt");
        scanner.nextLine();
        scanner.nextLine();

        System.out.println(paymentByCard.generateReceipt());
    }

    public static void main(String[] args){
        double subtotal = 456.70;

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n******************************************");
        System.out.println("||               PAYMENT                ||");
        System.out.println("******************************************");
        System.out.println("Choose payment method:");
        System.out.println(" [1] Cash");
        System.out.println(" [2] Credit/Debit Card");
        System.out.print("\nEnter your choice > ");
        int paymentMethod = scanner.nextInt();

        if (paymentMethod == 1) {
            paymentByCash(subtotal);
        } else if (paymentMethod == 2) {
            paymentByCard(subtotal);
        }

    }
}
