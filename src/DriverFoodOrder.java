import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DriverFoodOrder {
    public static void main(String[] args) {
        // this variable is a variable that will be used by all order
        FoodOrder foodOrder = new FoodOrder();
        // FoodOrderRecord to store order record
        FoodOrderRecord foodOrderRecord = new FoodOrderRecord();
        LocalDate reservationStartDate = LocalDate.now(); // for sample can run
        // get food order
        foodOrder = FoodOrdering(reservationStartDate);
        // after done all ordering pass food order to foodOrderRecord to store
        foodOrderRecord.addFoodOrder(foodOrder);

        System.out.println(foodOrder.getOrderID());
        System.out.println(foodOrderRecord.generateFoodOrderRecord("O001"));
    }

    public static void displayMenu(FoodType[] foodType, int foodTypeChoice) {
        int i;
        System.out.println("\n              MENU              ");
        System.out.println("--------------------------------");
        for (i = 0; i < foodType[foodTypeChoice].getNumOfFood(); i++) {
            System.out.print((i + 1) + ") " + foodType[foodTypeChoice].getFood(i).toString() + "\n");
        }
    }

    public static FoodOrder FoodOrdering(LocalDate reservationStartDate) {
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String serveDateString, serveTimeString;
        boolean dateValidity;
        Scanner scanner = new Scanner(System.in);
        FoodOrder foodOrder = new FoodOrder();

        LocalDateTime serveDateAndTime = LocalDateTime.now();

        // initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[0];
        foodType = initializeFood();

        // user enter and validate reserve date
        do {
            dateValidity = true;
            try {
                System.out.print("Enter food reserve date (DD-MM-YYYY): ");
                serveDateString = scanner.nextLine();

                System.out.print("Enter food reserve time (HH:MM): ");
                serveTimeString = scanner.nextLine();

                DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");
                DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

                // formatting the entered and store in serveDateandTime
                serveDateAndTime = LocalDateTime.of(LocalDate.parse(serveDateString, Dateformatter),
                        LocalTime.parse(serveTimeString, Timeformatter));

            } catch (DateTimeParseException e) {
                dateValidity = false;
            }

            // print error message
            if (!serveDateAndTime.toLocalDate().isAfter(reservationStartDate) || dateValidity == false)
                System.out.println("Invalid Time Please Retry\n");

        } while (!serveDateAndTime.toLocalDate().isAfter(reservationStartDate) || dateValidity == false);

        // print food menu and get user choice
        do {
            System.out.println("\n Food Type");
            System.out.println("==================");
            for (i = 0; i < FoodType.getNumOfFoodType(); i++) {
                System.out.print((i + 1) + ") " + foodType[i].getTypeName() + "\n");
            }

            // validation of foodtype choice
            do {
                System.out.print("Enter your choice on food type: ");
                foodTypeChoice = scanner.nextInt() - 1;

                // because foodtypeChoice -1 alrd so numOfFoodtype also need to -1 to balance
                if (foodTypeChoice > FoodType.getNumOfFoodType() - 1 || foodTypeChoice < 0)
                    System.out.println("Invalid Choice! Please Re-enter.");

            } while (foodTypeChoice > FoodType.getNumOfFoodType() - 1 || foodTypeChoice < 0);

            // display menu of the foodType choice
            displayMenu(foodType, foodTypeChoice);

            // validation of food choice
            do {
                System.out.print("Enter your choice of food: ");
                foodChoice = scanner.nextInt() - 1;

                if (foodChoice > foodType[foodTypeChoice].getNumOfFood() - 1 || foodChoice < 0)
                    System.out.println("Invalid Choice! Please Re-enter.");

            } while (foodChoice > foodType[foodTypeChoice].getNumOfFood() - 1 || foodChoice < 0);

            do {
                System.out.print("Enter the number of food: ");
                quantityOfFood = scanner.nextInt();

                if(quantityOfFood<=0)
                    System.out.println("Invalid Input! Please Re-enter.");

            } while (quantityOfFood <= 0);

            // pass data to food order
            // if foodCount = 0, means no order yet - to pass serveDateAndTime at very begining
            if (foodOrder.getFoodCount() == 0) {
                foodOrder = new FoodOrder(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood,
                        serveDateAndTime);
            } else {
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            }

            System.out.print("Continue add food ? (Y to continue) ");
            continueAddFood = scanner.next().charAt(0);

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } while (Character.toUpperCase(continueAddFood) == 'Y');

        System.out.print(foodOrder.generateOrderReceipt());

        // direct pass the order ID to the reservation by return
        return foodOrder;
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

}
