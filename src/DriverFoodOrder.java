import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DriverFoodOrder {
    public static void main(String[] args) {
        // this variable is a variable that will be used by all order
        FoodOrder foodOrder = new FoodOrder();

        // FoodOrderRecord to store order record
        FoodOrderRecord foodOrderRecord = new FoodOrderRecord();
        LocalDate reservationStartDate = LocalDate.now(); // actually is the reservation time

        //Food Type and Foods
        FoodType[] foodType = initializeFood();

        // get food order
        foodOrder = FoodOrdering(reservationStartDate,foodType);
        // after done all ordering pass food order to foodOrderRecord to store
        foodOrderRecord.addFoodOrder(foodOrder);

        System.out.println(foodOrder.getOrderID());
        System.out.println(foodOrderRecord.generateFoodOrderRecord(foodOrder.getOrderID()));
    }


    public static FoodOrder FoodOrdering(LocalDate reservationStartDate, FoodType[] foodType) {
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String serveDateString, serveTimeString;
        boolean dateValidity;
        Scanner scanner = new Scanner(System.in);
        FoodOrder foodOrder = new FoodOrder();

        LocalDateTime serveDateAndTime = LocalDateTime.now();

        // user enter and validate reserve date
        do {
            dateValidity = true;
            try {
                System.out.print("Enter food reserve date (YYYY-MM-DD): ");
                serveDateString = scanner.nextLine();

                System.out.print("Enter food reserve time (HH:MM): ");
                serveTimeString = scanner.nextLine();

                // formatting the entered and store in serveDateandTime
                serveDateAndTime = LocalDateTime.of(LocalDate.parse(serveDateString),
                        LocalTime.parse(serveTimeString));

            } catch (DateTimeParseException e) {
                dateValidity = false;
            }

            // print error message
            if (serveDateAndTime.toLocalDate().isBefore(reservationStartDate) || dateValidity == false)
                System.out.println("Invalid Time! Please Re-enter.\n");

        } while (serveDateAndTime.toLocalDate().isBefore(reservationStartDate) || dateValidity == false);

        // print food menu and get user choice
        do {
            System.out.println("\n             Food Type Menu        ");
            System.out.println("+-----+----------------------+---------+");
            System.out.printf("| %-3s | %-20s | %-7s |\n","No","Food Type","Choices");
            System.out.println("+-----+----------------------+---------+");
            for (i = 0; i < foodType.length; i++) {
                System.out.printf("| %-3d | %-20s | %-7d |\n", (i+1),foodType[i].getTypeName(),foodType[i].getFood().length);
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

                if(quantityOfFood<=0)
                    System.out.println("Invalid Input! Please Re-enter.");

            } while (quantityOfFood <= 0);

            // pass data to food order
            // if foodCount = 0, means no order yet - to pass serveDateAndTime at very begining
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

        // pass the food order back to main to Record and get order ID to store in reservation
        return foodOrder;
    }

    public static void displayMenu(FoodType[] foodType, int foodTypeChoice) {
        int i;
        System.out.println("\n                       MENU                        ");
        System.out.println("+-----+--------------------------------+------------+");
        System.out.println("| No  | Food                           | Price(RM)  |");
        System.out.println("+-----+--------------------------------+------------+");
        for (i = 0; i < foodType[foodTypeChoice].getNumOfFood(); i++) {
            System.out.printf("| %-3d | %-30s | %-10.2f |\n",(i+1),foodType[foodTypeChoice].getFood(i).getFoodName(),foodType[foodTypeChoice].getFood(i).getPrice());
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

}
