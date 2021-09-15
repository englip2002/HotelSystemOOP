import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Final {
    public static void main(String[] args) {
        // Variable declaration and definition
        Scanner scanner = new Scanner(System.in);

        RoomType[] roomTypes = { new RoomType("Single Room", 1, 0, 100.00), new RoomType("Double Room", 2, 0, 160.00),
                new RoomType("Family Room", 3, 0, 210.00), new RoomType("Normal Suite", 0, 1, 175.00),
                new RoomType("Deluxe Suite", 1, 1, 245.00), new RoomType("Premium Suite", 2, 1, 295.00) };

        Block block = initializeRooms(roomTypes);

        // this variable is used to store the foods
        FoodType[] foodType = initializeFood();

        // FoodOrderRecord to store order record
        FoodOrderRecord foodOrderRecord = new FoodOrderRecord();

        /*
         *
         * Login
         *
         */

        // Assume customer login successful
        Customer cust = new Customer();
        initializeReservations(cust, roomTypes, block);

        // Main Menu
        int menuOpt = 0;
        while (menuOpt != 5) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(
                    "\n+----------------------------------+\n"
                            + "|      Hotel Reservation Menu      |\n"
                            + "+----------------------------------+\n"
                            + "| 1 - View Profile                 |\n"
                            + "| 2 - Make a Reservation           |\n"
                            + "| 3 - View Reservations            |\n"
                            + "| 4 - Cancel Reservation           |\n"
                            + "| 5 - Logout                       |\n"
                            + "+----------------------------------+");

            menuOpt = getIntegerInput(scanner, "Enter your option: ");
            while (menuOpt < 1 || menuOpt > 5) {
                System.out.println("Invalid menu option. Please re-enter. ");
                menuOpt = getIntegerInput(scanner, "Enter your option: ");
            }

            if (menuOpt != 5) { // Clear Screen
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            switch (menuOpt) {
                case 1: {
                    // View customer profile (Lee Seng Wai)
                    break;
                }

                // Make a Reservation
                case 2: {
                    Reservation reservation = makeReservation(scanner, cust, roomTypes, block, foodType,
                            foodOrderRecord);
                    // store reservation record
                    cust.addReservation(reservation);
                    //foodOrderRecord.generateFoodOrderRecord(reservation.getOrderID());
                    break;
                }

                // View Previous Reservations
                case 3: {
                    viewReservations(scanner, cust, foodOrderRecord);
                    break;
                }

                // Cancel Previous Reservations
                case 4: {
                    cancelReservations(scanner, cust, block);
                    break;
                }

                // Logout
                case 5: {
                    // Print logout msg
                    break;
                }

                default:
                    break;
            }
        }

        System.out.println("EOP");
        scanner.close();
    }

    // ===================================== Main Functions =====================================

    // Option 1 - View Profile
    /*
     * ...
     */

    // Option 2 - Make reservation (Book room + food order + make payment)
    public static Reservation makeReservation(Scanner scanner, Customer cust, RoomType[] roomTypes, Block block,
                                              FoodType[] foodType, FoodOrderRecord foodOrderRecord) {

        // -------------------- Main Function (Reservation - Thong So Xue) --------------------

        // Constants
        int MAX_RESERVATION_DAYS = 365;

        // Title
        System.out.println("\n            ( Make Reservation )\n");


        // Input start date
        LocalDate startDate = getDateInput(scanner, "Enter reservation start date (YYYY-MM-DD): ");
        while (!startDate.isAfter(LocalDate.now())) {
            System.out.println("Start date must be at least 1 day later than today's date! Please re-enter");
            startDate = getDateInput(scanner, "Enter reservation start date (YYYY-MM-DD): ");
        }

        // Input end date
        LocalDate endDate = getDateInput(scanner, "Enter reservation end date   (YYYY-MM-DD): ");
        ReservationSchedule schedule = new ReservationSchedule(startDate, endDate);
        while (!endDate.isAfter(startDate) || schedule.getDaysBetween() > MAX_RESERVATION_DAYS) {
            if (!endDate.isAfter(startDate))
                System.out.println("End Date must be at least 1 day later than Start Date! Please re-enter. ");
            else if (schedule.getDaysBetween() > MAX_RESERVATION_DAYS)
                System.out.println("Sorry, but you can only reserve up to a maximum of " + MAX_RESERVATION_DAYS
                        + " days. Please re-enter. ");
            endDate = getDateInput(scanner, "Enter reservation end date   (YYYY-MM-DD): ");
            schedule = new ReservationSchedule(startDate, endDate);
        }

        // For each room type in the chosen date range:
        int[] availableRoomCounts = new int[roomTypes.length];
        for (int i = 0; i < roomTypes.length; i++) {
            // Calculate the amount of available rooms
            int total = 0;
            for (int f = 0; f < block.getNumberOfFloors(); f++) {
                for (int r = 0; r < block.getFloors()[f].getNumberOfRooms(); r++) {
                    Room room = block.getFloors()[f].getRooms()[r];
                    if (room.getRoomType().equals(roomTypes[i])
                            && room.validateReservationSchedule(schedule)) {
                        total++;
                    }
                }
            }

            availableRoomCounts[i] = total;
        }

        // Display and Choose Room Types
        int[] reservedRoomTypeAmounts = new int[roomTypes.length];
        boolean continueChooseRoomType = true;
        double reservationTotalAmount = 0;
        do { // Room Type loop
            System.out.println("\n"
                    + "+----------------------------------------------------------------------------------+\n"
                    + "| No |    Room Type    | Twin Beds | King Beds | Price Per Night | Available Rooms |\n"
                    + "+----------------------------------------------------------------------------------+");
            // For each room type:
            for (int i = 0; i < availableRoomCounts.length; i++) {
                // Display room type info
                System.out.println("| "
                        + String.format("%2d", (i + 1)) + " | "
                        + String.format("%-15s", roomTypes[i].getName()) + " | "
                        + String.format("%9d", roomTypes[i].getNumberOfTwinBeds()) + " | "
                        + String.format("%9d", roomTypes[i].getNumberOfKingBeds()) + " | " + "RM"
                        + String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | "
                        + String.format("%15d", availableRoomCounts[i]) + " |");
            }
            System.out.println("+----------------------------------------------------------------------------------+");
            int roomChoice, reserveAmount;


            do { // Choose a room type
                roomChoice = getIntegerInput(scanner, "Choose a room type to reserve (Enter number): ") - 1;
                if (roomChoice < 0 || roomChoice > roomTypes.length) {
                    System.out.println("Invalid input! Please re-enter. ");
                }
                else if (availableRoomCounts[roomChoice] == 0) {
                    System.out.println("Sorry, but we currently don't have any available rooms for this room type. Please choose another room type. ");
                }
            } while (roomChoice < 0 || roomChoice > roomTypes.length || availableRoomCounts[roomChoice] == 0);


            do { // Enter reserve amount
                reserveAmount = getIntegerInput(scanner,
                        "Enter the amount of " + roomTypes[roomChoice].getName() + " to be reserved: ");
                if (reserveAmount < 0)
                    System.out.println("Invalid input. Please re-enter. ");
                else if (reserveAmount < 1)
                    System.out.println("You need to reserve at least one room. ");
                else if (reserveAmount > availableRoomCounts[roomChoice]) {
                    System.out.println("Sorry, but we only have " + availableRoomCounts[roomChoice]
                            + " rooms available for this room type. Please enter a smaller amount. ");
                }
            } while (reserveAmount < 1 || reserveAmount > availableRoomCounts[roomChoice]);

            reservedRoomTypeAmounts[roomChoice] += reserveAmount;
            availableRoomCounts[roomChoice] -= reserveAmount;


            // Display summary at end of loop
            System.out.println("\n               < Room Reservation Summary >\n"
                    + "          [ " + startDate + " - " + endDate + " ] : ("
                    + schedule.getDaysBetween() + " nights)\n"
                    + "+-----------------------------------------------------------+\n"
                    + "|    Room Type    | Amount | Price Per Night |   Subtotal   |\n"
                    + "+-----------------------------------------------------------+");

            for (int i = 0; i < roomTypes.length; i++) {
                if (reservedRoomTypeAmounts[i] != 0) {
                    System.out.println("| "
                            + String.format("%-15s", roomTypes[i].getName()) + " | "
                            + String.format("%6d", reservedRoomTypeAmounts[i]) + " | " + "RM"
                            + String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | " + "RM"
                            + String.format("%10.2f",reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight())
                            + " |");
                    reservationTotalAmount  += reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight();
                }
            }


            // Display total
            System.out.println(
                    "+-----------------------------------------------------------+\n"
                            + "|                            TOTAL (1 night) | RM" + String.format("%10.2f", reservationTotalAmount ) + " |\n"
                            + "+-----------------------------------------------------------+\n"
                            + String.format("|                   GRAND TOTAL (%3d nights) | RM%10.2f |\n",
                            schedule.getDaysBetween(), reservationTotalAmount  * schedule.getDaysBetween())
                            + "+-----------------------------------------------------------+\n"
            );


            // Ask to continue reserve more rooms or not
            boolean invalidInput;
            do {
                invalidInput = false;
                System.out.print("Do you want to reserve more rooms? (Y/N): ");
                String inputContinue = scanner.nextLine();
                if (inputContinue.length() != 1)
                    invalidInput = true;
                else {
                    char c = Character.toUpperCase(inputContinue.charAt(0));
                    if (c != 'Y' && c != 'N')
                        invalidInput = true;
                    else
                        continueChooseRoomType = (c == 'Y');
                }
                if (invalidInput)
                    System.out.println("Invalid input! Please re-enter. ");
            } while (invalidInput);

            // If 'Y' then choose more room type to reserve, else proceed
        } while (continueChooseRoomType);

        // Find vacant rooms on the chosen schedule
        ArrayList<Room> reservedRooms = reserveVacantRooms(block, roomTypes, schedule, reservedRoomTypeAmounts);


        // -------------------- Main Function (Food Order - Tan Eng Lip) --------------------

        // make food Order
        char orderChoice = 'N';
        System.out.println("\n\nFood Ordering ");
        System.out.println("=====================");
        System.out.print("Do you wish to order food ? (Y/N) :");
        orderChoice = Character.toUpperCase(scanner.next().charAt(0));

        String foodOrderID = "-";
        double foodOrderTotalAmount = 0;
        FoodOrder foodOrder;

        if (orderChoice == 'Y') {
            // execute food ordering
            foodOrder = FoodOrdering(startDate,endDate, foodType,scanner);
            foodOrderID = foodOrder.getOrderID();
            // get subtotal
            foodOrderTotalAmount = foodOrder.getSubtotal();
            // store food order record
            foodOrderRecord.addFoodOrder(foodOrder);
        }


        // -------------------- Main Function (Payment - Wang Shu Wei) --------------------

        /*
         *
         * Payment (shu wei)
         *
         */
        Payment payment=makePayment(reservationTotalAmount + foodOrderTotalAmount,scanner);

        // Assume payment is made
        Reservation reservation = new Reservation(cust, schedule, reservedRooms, foodOrderID, payment);

        System.out.println("\nThe following is the summary of your reservation: ");
        System.out.println(reservation.generateReport());

        System.out.print("< Press enter to continue >");
        scanner.nextLine();

        return reservation;
    }

    // Option 3 - View Previous Reservations
    public static void viewReservations(Scanner scanner, Customer cust, FoodOrderRecord foodOrderRecord) {
        System.out.println("\n                 ( View Reservations )\n");

        ArrayList<Reservation> reservations = cust.getReservationList();
        if (reservations.size() == 0) {
            System.out.println("         < You have no previous reservations! >");
            System.out.println("              < Press enter to continue >");
            scanner.nextLine();
            return;
        }

        int viewOpt;
        do {
            printReservationsTable(reservations);
            do {
                viewOpt = getIntegerInput(scanner, "Enter No. to view the details (0 to exit): ");
                if (viewOpt < 0 || viewOpt > reservations.size()) {
                    System.out.println("Invalid input! Please re-enter");
                }
            } while(viewOpt < 0 || viewOpt > reservations.size());

            if (viewOpt == 0)
                return;

            viewOpt -= 1;

            System.out.println(reservations.get(viewOpt).generateReport());
            System.out.print("< Press enter to continue >");
            scanner.nextLine();

            // Print Food Order Report
            System.out.println(foodOrderRecord.generateFoodOrderRecord(reservations.get(viewOpt)));
            System.out.print("< Press enter to continue >");
            scanner.nextLine();

            // Print Payment Report
            System.out.println(reservations.get(viewOpt).getPayment().generateReport());
            System.out.print("< Press enter to continue >");
            scanner.nextLine();

        } while (viewOpt != 0);
    }

    // Option 4 - Cancel Previous Reservations
    public static void cancelReservations(Scanner scanner, Customer cust, Block block) {
        System.out.println("\n                ( Cancel Reservations )\n");

        ArrayList<Reservation> reservations = cust.getReservationList();
        if (reservations.size() == 0) {
            System.out.println("         < You have no previous reservations! >");
            System.out.println("              < Press enter to continue >");
            scanner.nextLine();
            return;
        }

        int menuOpt;
        printReservationsTable(reservations);
        do {
            menuOpt = getIntegerInput(scanner, "Enter No. to cancel the reservation (0 to exit): ");
            if (menuOpt < 0 || menuOpt > reservations.size()) {
                System.out.println("Invalid input! Please re-enter");
            }
        } while(menuOpt < 0 || menuOpt > reservations.size());

        if (menuOpt == 0)
            return;

        menuOpt -= 1;
        String cancelID = reservations.get(menuOpt).getReservationID();
        System.out.println("Are you sure you want to cancel the reservation (" + cancelID + ")?");
        System.out.println("[ THIS ACTION CANNOT BE REVERSED! ]");
        System.out.print("(Type 'YES' to cancel) > ");
        String inputYN = scanner.nextLine().toUpperCase();
        if (inputYN.equals("YES")) {
            cust.cancelReservation(reservations.get(menuOpt).getReservationID());
            System.out.printf("( Cancellation completed. Reservation %s is now cancelled\n  and RM%.2f will be refunded to you later. )\n", cancelID, reservations.get(menuOpt).getPayment().refund());
        }
        else {
            System.out.println("< Cancellation stopped. Returning to main menu. >");
        }

        System.out.print("\n< Press enter to continue >");
        scanner.nextLine();
    }

    // ===================================== Other Functions (Food order - Tan Eng Lip) =====================================

    // Food Ordering Methods
    public static FoodOrder FoodOrdering(LocalDate reservationStartDate, LocalDate reservationEndDate, FoodType[] foodType, Scanner scanner) {
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String serveDateString, serveTimeString;
        boolean dateValidity;
        FoodOrder foodOrder = new FoodOrder();
        LocalDateTime serveDateAndTime = LocalDateTime.now();

        // user enter and validate reserve date
        do {
            dateValidity = true;
            try {
                System.out.print("Enter food reserve date (YYYY-MM-DD): ");
                serveDateString = scanner.next();

                System.out.print("Enter food reserve time (HH:MM): ");
                serveTimeString = scanner.next();

                // formatting the entered and store in serveDateandTime
                serveDateAndTime = LocalDateTime.of(LocalDate.parse(serveDateString), LocalTime.parse(serveTimeString));

            } catch (DateTimeParseException e) {
                dateValidity = false;
            }

            // print error message

            if(dateValidity == false)
                System.out.println("Invalid Date Format! Please Re-enter.\n");
            else if (serveDateAndTime.toLocalDate().isBefore(reservationStartDate)|| serveDateAndTime.toLocalDate().isAfter(reservationEndDate))
                System.out.println("Invalid Time! Please Re-enter.\n");

        } while (serveDateAndTime.toLocalDate().isBefore(reservationStartDate)
                || serveDateAndTime.toLocalDate().isAfter(reservationEndDate) || dateValidity == false);

        // print food menu and get user choice
        do {
            System.out.println("\n             Food Type Menu        ");
            System.out.println("+-----+----------------------+---------+");
            System.out.printf("| %-3s | %-20s | %-7s |\n", "No", "Food Type", "Choices");
            System.out.println("+-----+----------------------+---------+");
            for (i = 0; i < foodType.length; i++) {
                System.out.printf("| %-3d | %-20s | %-7d |\n", (i + 1), foodType[i].getTypeName(),
                        foodType[i].getFood().length);
            }
            System.out.println("+-----+----------------------+---------+");

            // validation of foodtype choice
            do {
                System.out.print("Enter the No of Food Type: ");
                foodTypeChoice = scanner.nextInt() - 1;

                // because foodtypeChoice -1 alrd so numOfFoodtype also need to -1 to balance
                if (foodTypeChoice > foodType.length - 1 || foodTypeChoice < 0)
                    System.out.println("Invalid Choice! Please Re-enter.");

            } while (foodTypeChoice > foodType.length - 1 || foodTypeChoice < 0);

            // display menu of the foodType choice
            displayMenu(foodType, foodTypeChoice);

            // validation of food choice
            do {
                System.out.print("Enter the No of Food: ");
                foodChoice = scanner.nextInt() - 1;

                if (foodChoice > foodType[foodTypeChoice].getNumOfFood() - 1 || foodChoice < 0)
                    System.out.println("Invalid Choice! Please Re-enter.");

            } while (foodChoice > foodType[foodTypeChoice].getNumOfFood() - 1 || foodChoice < 0);

            do {
                System.out.print("Enter the Quantity of Purchase: ");
                quantityOfFood = scanner.nextInt();

                if (quantityOfFood <= 0)
                    System.out.println("Invalid Input! Please Re-enter.");

            } while (quantityOfFood <= 0);

            // pass data to food order
            // if foodCount = 0, means no order yet - to pass serveDateAndTime at very
            // begining
            if (foodOrder.getFoodCount() == 0) {
                foodOrder.setServeTime(serveDateAndTime);
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            } else {
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            }

            System.out.print("Continue add food ? (Y to continue) ");
            continueAddFood = scanner.next().charAt(0);

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } while (Character.toUpperCase(continueAddFood) == 'Y');

        System.out.print(foodOrder.generateReport());

        System.out.print("< Press enter to continue >");
        scanner.nextLine();
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        // pass the food order back to Record and get order ID to store in reservation
        return foodOrder;
    }

    public static void displayMenu(FoodType[] foodType, int foodTypeChoice) {
        int i;
        System.out.println("\n                       MENU                        ");
        System.out.println("+-----+--------------------------------+------------+");
        System.out.println("| No  | Food                           | Price(RM)  |");
        System.out.println("+-----+--------------------------------+------------+");
        for (i = 0; i < foodType[foodTypeChoice].getNumOfFood(); i++) {
            System.out.printf("| %-3d | %-30s | %-10.2f |\n", (i + 1),
                    foodType[foodTypeChoice].getFood(i).getFoodName(), foodType[foodTypeChoice].getFood(i).getPrice());
        }
        System.out.println("+-----+--------------------------------+------------+");
    }

    public static FoodType[] initializeFood() {
        Food[] noodles = new Food[2];
        noodles[0] = new Food("White Sauce Spaghetti", 6.90);
        noodles[1] = new Food("Maggi Kari", 3.2);

        Food[] rice = new Food[3];
        rice[0] = new Food("Egg Fried Rice", 4.0);
        rice[1] = new Food("Chicken Rice", 5.0);
        rice[2] = new Food("Nasi Kandar", 4.50);

        Food[] drink = new Food[3];
        drink[0] = new Food("Orange Juice", 2.2);
        drink[1] = new Food("Apple Juice", 2.1);
        drink[2] = new Food("Pineapple Lemon Juice", 3.0);

        // initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[3];
        foodType[0] = new FoodType("Noodle", noodles);
        foodType[1] = new FoodType("Rice", rice);
        foodType[2] = new FoodType("Drink", drink);

        return foodType;
    }


    // ===================================== Other Functions (Payment - Wang Shu Wei) =====================================

    // Payment
    public static Payment paymentByCash(double subtotal, Scanner scanner) {
        PaymentByCash paymentByCash = new PaymentByCash(subtotal);

        paymentByCash.calculateTaxAmount();
        paymentByCash.calculateTotalAmount();

        System.out.println("\n============== PAY BY CASH ===============");
        System.out.printf(" Total Amount : %.2f\n", paymentByCash.getTotalAmount());
        System.out.println(" **included tax");

        System.out.println("------------------------------------------");
        System.out.print(" Input cash > ");
        paymentByCash.setTotalReceived(scanner.nextDouble());

        while (!paymentByCash.validateTotalReceived()) {
            System.out.println(" Insufficient cash!!");
            System.out.print(" Add cash > ");
            paymentByCash.setTotalReceived(paymentByCash.getTotalReceived() + scanner.nextDouble());
        }

        System.out.print("\nConfirm payment? (y=yes/n=no) > ");
        char confirmPay = Character.toLowerCase(scanner.next().charAt(0));

        while (confirmPay != 'y' && confirmPay != 'n') {
            System.out.println("Invalid input!! Please enter again.");
            System.out.print("\nConfirm payment? (y=yes/n=no) > ");
            confirmPay = Character.toLowerCase(scanner.next().charAt(0));
        }

        if (confirmPay == 'n') {
            makePayment(subtotal,scanner);
        }

        paymentByCash.calculateChange();

        System.out.printf("\n Total received : %.2f\n", paymentByCash.getTotalReceived());
        System.out.printf(" Change         : %.2f\n", paymentByCash.getChange());
        System.out.println("\nPayment Completed !!");

        System.out.print("\n< Press enter to view receipt >");
        scanner.nextLine();
        scanner.nextLine();

        System.out.println(paymentByCash.generateReceipt());
        return paymentByCash;
    }

    public static Payment paymentByCard(double subtotal, Scanner scanner) {
        PaymentByCard paymentByCard = new PaymentByCard(subtotal);
        Bank[] bank = new Bank[5];
        bank[0] = new Bank("Maybank");
        bank[1] = new Bank("Public Bank");
        bank[2] = new Bank("Hong Leong Bank");
        bank[3] = new Bank("AmBank");
        bank[4] = new Bank("CIMB Bank");

        paymentByCard.calculateTaxAmount();
        paymentByCard.calculateTotalAmount();

        System.out.println("\n============== PAY BY CARD ===============");
        System.out.printf(" Total Amount : %.2f\n", paymentByCard.getTotalAmount());
        System.out.println(" **included tax and charge");
        System.out.println("------------------------------------------");
        System.out.println("Bank List:");
        for (int i = 0; i < bank.length; i++) {
            System.out.printf("\t[%d] %s\n", i + 1, bank[i].getBankName());
        }
        System.out.print("\nSelect a bank (1-5) > ");
        int bankOption = scanner.nextInt();

        while (bankOption < 1 || bankOption > 5) {
            System.out.println("\nInvalid option!! Please enter again.");
            System.out.print("Select a bank (1-5) > ");
            bankOption = scanner.nextInt();
        }
        paymentByCard.setBank(bank[bankOption - 1]);

        System.out.print("\n Card Number : ");
        paymentByCard.setCardNumber(scanner.next());

        System.out.print(" CVV         : ");
        paymentByCard.setCVV(scanner.nextInt());

        while (!paymentByCard.validCard()) {
            System.out.println("\nInvalid card!!");
            System.out.print("\n Card Number: ");
            paymentByCard.setCardNumber(scanner.next());

            System.out.print("\n CVV        : ");
            paymentByCard.setCVV(scanner.nextInt());
        }

        System.out.print("\nConfirm payment? (y=yes/n=no) > ");
        char confirmPay = Character.toLowerCase(scanner.next().charAt(0));

        while (confirmPay != 'y' && confirmPay != 'n') {
            System.out.println("Invalid input!! Please enter again.");
            System.out.print("\nConfirm payment? (y=yes/n=no) > ");
            confirmPay = Character.toLowerCase(scanner.next().charAt(0));
        }

        if (confirmPay == 'n') {
            makePayment(subtotal,scanner);
        }

        System.out.print("\n< Press enter to request the OTP number >");
        scanner.nextLine();
        scanner.nextLine();

        paymentByCard.getBank().generateOTPNumber();
        System.out.println("\n+-----------------+");
        System.out.printf("| OTP No : %s |\n", paymentByCard.getBank().getOtpNumber());
        System.out.println("+-----------------+");
        System.out.print(" Enter OTP No : ");

        while (!paymentByCard.getBank().validateOTPNumber(scanner.next())) {
            System.out.println("\nInvalid OTP No !!");
            System.out.print("< Press enter to request OTP Number again >");
            scanner.nextLine();
            scanner.nextLine();
            paymentByCard.getBank().generateOTPNumber();
            System.out.println("\n+-----------------+");
            System.out.printf("| OTP No : %s |\n", paymentByCard.getBank().getOtpNumber());
            System.out.println("+-----------------+");
            System.out.print(" Enter OTP No : ");
        }

        System.out.println();
        for (int i = 3; i > 0; i--) {
            System.out.printf("Processing payment... (%d sec)\n", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nPayment Completed !!");

        System.out.print("\n< Press enter to view receipt >");
        scanner.nextLine();
        scanner.nextLine();

        System.out.println(paymentByCard.generateReceipt());
        return paymentByCard;
    }

    public static Payment paymentByEWallet(double subtotal,Scanner scanner) {
        PaymentByEWallet paymentByEWallet = new PaymentByEWallet(subtotal);

        paymentByEWallet.calculateTaxAmount();
        paymentByEWallet.calculateTotalAmount();

        System.out.println("\n============ PAY BY eWALLET =============");
        System.out.printf(" Total Amount : %.2f\n", paymentByEWallet.getTotalAmount());
        System.out.println(" **included tax");

        System.out.println("------------------------------------------");

        System.out.print(" Enter your 6-digit PIN > ");
        paymentByEWallet.setPinNumber(scanner.next());

        while (!paymentByEWallet.validatePinNumber()) {
            System.out.println("Invalid PIN!! Please enter again.");
            System.out.print("\n Enter your 6-digit PIN > ");
            paymentByEWallet.setPinNumber(scanner.next());
        }

        System.out.print("\nConfirm payment? (y=yes/n=no) > ");
        char confirmPay = Character.toLowerCase(scanner.next().charAt(0));

        while (confirmPay != 'y' && confirmPay != 'n') {
            System.out.println("Invalid input!! Please enter again.");
            System.out.print("\nConfirm payment? (y=yes/n=no) > ");
            confirmPay = Character.toLowerCase(scanner.next().charAt(0));
        }

        if (confirmPay == 'n') {
            makePayment(subtotal,scanner);
        }

        System.out.println();
        for (int i = 3; i > 0; i--) {
            System.out.printf("Processing payment... (%d sec)\n", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nPayment Completed !!");

        System.out.print("\n< Press enter to view receipt >");
        scanner.nextLine();
        scanner.nextLine();

        System.out.println(paymentByEWallet.generateReceipt());
        return paymentByEWallet;
    }

    public static Payment makePayment(double subtotal,Scanner scanner) {
        Payment payment;
        System.out.println("\n------------------------------------------");
        System.out.println("|                PAYMENT                 |");
        System.out.println("------------------------------------------");
        System.out.println("Choose payment method:");
        System.out.println(" [1] Cash");
        System.out.println(" [2] Credit/Debit Card");
        System.out.println(" [3] E-Wallet");
        System.out.print("\nEnter your choice > ");
        int paymentMethod = scanner.nextInt();

        while (paymentMethod < 1 || paymentMethod > 3) {
            System.out.println("Invalid input!! Please enter again.");
            System.out.print("\nEnter your choice > ");
            paymentMethod = scanner.nextInt();
        }

        if (paymentMethod == 1)
            payment=paymentByCash(subtotal,scanner);
        else if (paymentMethod == 2)
            payment=paymentByCard(subtotal,scanner);
        else
            payment=paymentByEWallet(subtotal,scanner);

        return payment;
    }

    // ===================================== Other Functions (Reservation - Thong So Xue) =====================================

    // Prompt and validate date input
    public static LocalDate getDateInput(Scanner scanner, String question) {
        LocalDate d = null;
        boolean invalidDate = true;
        do {
            try {
                System.out.print(question);
                String inputDate = scanner.nextLine();
                d = LocalDate.parse(inputDate);
                invalidDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, please re-enter. ");
            }
        } while (invalidDate);
        return d;
    }

    // Prompt and validate date input
    public static int getIntegerInput(Scanner scanner, String question) {
        int output = 0;
        boolean invalidInput;
        do {
            invalidInput = false;
            System.out.print(question);
            String inputStr = scanner.nextLine().replaceAll("\\s+", "");
            if (inputStr.length() == 0)
                invalidInput = true;
            else {
                try {
                    output = Integer.parseInt(inputStr);
                } catch (NumberFormatException e) {
                    invalidInput = true;
                }
            }
            if (invalidInput)
                System.out.println("Invalid input! Please re-enter. ");
        } while (invalidInput);
        return output;
    }

    // Loop through all rooms in the block and reserve the vacant rooms on the given schedule
    public static ArrayList<Room> reserveVacantRooms(Block block, RoomType[] roomTypes, ReservationSchedule schedule, int[] reservedRoomTypeAmounts) {
        ArrayList<Room> reservedRooms = new ArrayList<Room>();
        for (int i = 0; i < roomTypes.length; i++) {	//For each room type
            for (int j = 0; j < reservedRoomTypeAmounts[i]; j++) {	// Get vacant rooms for n times
                nextRoom:
                for (Floor f: block.getFloors()) {
                    for (Room r: f.getRooms()) {
                        if (roomTypes[i].equals(r.getRoomType()) && r.validateReservationSchedule(schedule)) {
                            r.addReservationSchedule(schedule);
                            reservedRooms.add(r);
                            break nextRoom;
                        }
                    }
                }
            }
        }
        return reservedRooms;
    }

    // Manually create rooms to fill the block
    public static Block initializeRooms(RoomType[] roomTypes) {
        int floorsPerBlock = 10;
        int roomsPerFloor = 10;
        Block block = new Block("A", floorsPerBlock);

        Floor[] floors = new Floor[floorsPerBlock];
        for (int i = 0; i < floorsPerBlock; i++) {
            floors[i] = new Floor(i+1, roomsPerFloor);
            for (int j = 0; j < roomsPerFloor; j++) {
                int k = 0;
                switch(i) {
                    case 0, 1: { k = 0; break; }
                    case 2, 3: { k = 1; break; }
                    case 4, 5: { k = 2; break; }
                    case 6, 7: {k = 3; break; }
                    case 8: { k = 4; break; }
                    case 9: {k = 5; break; }
                }
                floors[i].addRoom(new Room((i+1)*100+(j+1), roomTypes[k]));
            }
        }
        block.addFloors(floors);
        return block;
    }

    public static void initializeReservations(Customer cust, RoomType[] roomTypes, Block block) {

        ArrayList<Room> r2 = new ArrayList<>();
        r2.add(new Room(999, roomTypes[0]));
        cust.addReservation(new Reservation(
                cust, new ReservationSchedule(LocalDate.of(2021, 10, 5), LocalDate.of(2021, 10, 10)),
                r2, "", new PaymentByCard(100, "Public Bank", "4123678298658986")));
    }

    public static void printReservationsTable(ArrayList<Reservation> reservations) {
        String tableLine = "+-----+----------+------------+------------+-----------+\n";
        System.out.print(tableLine
                + String.format("| %-3s | %-8s | %-10s | %-10s | %-9s |\n",
                "No.", "ID", "Start Date", "End Date", "Status")
                + tableLine
        );

        for (int i = 0; i < reservations.size(); i++) {
            System.out.printf("| %3d |", i+1);
            System.out.println(reservations.get(i).generateTableRow());
        }
        System.out.println(tableLine);

    }
}
