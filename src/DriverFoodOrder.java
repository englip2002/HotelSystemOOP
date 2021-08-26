import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DriverFoodOrder {
    public static void main(String[] args) {
        //get food order
        FoodOrder foodOrder=new FoodOrder();
        foodOrder=FoodOrdering();

        //pass food order to foodOrderRecord to store
        FoodOrderRecord foodOrderRecord=new FoodOrderRecord();
        foodOrderRecord.addFoodOrder(foodOrder);

        System.out.println(foodOrder.getOrderID());
    }

    public static void displayMenu(FoodType[] foodType, int foodTypeChoice) {
        int i;
        System.out.println("\n              MENU              ");
        System.out.println("--------------------------------");
        for (i = 0; i < 100; i++) {
            if (foodType[foodTypeChoice].getFood(i) == null)
                break;

            System.out.print((i + 1) + ") " + foodType[foodTypeChoice].getFood(i).toString() + "\n");
        }
    }

    public static FoodOrder FoodOrdering() {
        final int LIMIT = 100;
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String serveDateString, serveTimeString;
        Scanner scanner = new Scanner(System.in);
        FoodOrder foodOrder = new FoodOrder();

        LocalDateTime serveDateAndTime = LocalDateTime.now();

        
        // initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[100];
        foodType=initializeFood();
        

        // user enter and validate reserve date
        boolean valid;
        do {
            valid = true;
            try {
                System.out.print("Enter food reserve date (DD-MM-YYYY): ");
                serveDateString = scanner.nextLine();

                System.out.print("Enter food reserve time (HH:MM): ");
                serveTimeString = scanner.nextLine();

                DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");
                DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                serveDateAndTime = LocalDateTime.of(LocalDate.parse(serveDateString, Dateformatter),
                        LocalTime.parse(serveTimeString, Timeformatter));

            } catch (DateTimeParseException e) {
                valid = false;
            }

            if (!serveDateAndTime.isAfter(LocalDateTime.now()) || valid == false)
                System.out.println("Invalid Time Please Retry\n");

        } while (!serveDateAndTime.isAfter(LocalDateTime.now()) || valid == false);

        do {
            System.out.println("\n Food Type");
            System.out.println("==================");
            for (i = 0; i < foodType.length; i++) {
                if (foodType[i] == null)
                    break;
                else
                    System.out.print((i + 1) + ") " + foodType[i].getTypeName() +"\n");
            }
            do {
                System.out.print("Enter your choice on food type: ");
                foodTypeChoice = scanner.nextInt() - 1;  
            } while (foodTypeChoice > foodType.length -1);  

            displayMenu(foodType, foodTypeChoice);
            System.out.print("Enter your choice of food: ");
            foodChoice = scanner.nextInt() - 1;
            System.out.print("Enter the number of food: ");
            quantityOfFood = scanner.nextInt();

            // pass data to food order
            // if foodCount = 0, means no order yet
            if (foodOrder.getFoodCount() == 0) {
                foodOrder = new FoodOrder(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood,
                        serveDateAndTime);
            } else {
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            }

            System.out.print("Continue add food ?(Y to continue) ");
            continueAddFood = scanner.next().charAt(0);
            continueAddFood = Character.toUpperCase(continueAddFood);
        } while (continueAddFood == 'Y');

        System.out.print(foodOrder.generateOrderReceipt());    

        // direct pass the order ID to the reservation by return
        return foodOrder;
    }

    public static FoodType[] initializeFood(){
        final int LIMIT = 100;
        Food[] noodles = new Food[LIMIT];
        noodles[0] = new Food("White Sauce Spaghetti", 6.90);
        noodles[1] = new Food("Maggi Kari", 3.2);

        Food[] rice = new Food[LIMIT];
        rice[0] = new Food("Egg Fried Rice", 4.0);
        rice[1] = new Food("Chicken Rice", 5.0);
        rice[2] = new Food("Nasi Kandar", 4.50);

        Food[] drink = new Food[LIMIT];
        drink[0] = new Food("Orange Juice", 2.2);
        drink[1] = new Food("Apple Juice", 2.1);
        drink[2] = new Food("Pineapple Lemon Juice", 3.0);

        // initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[100];
        foodType[0] = new FoodType("Noodle", noodles);
        foodType[1] = new FoodType("Rice", rice);
        foodType[2] = new FoodType("Drink", drink);

        return foodType;
    }

}
