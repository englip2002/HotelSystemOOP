import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class EngLip_driverProgram {
    public static void main(String[] args) {
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String foodServeDate;
        Scanner scanner = new Scanner(System.in);
        FoodOrder foodOrder = new FoodOrder();
        FoodOrderCatalog foodOrderCatalog = new FoodOrderCatalog();

        // initialize serve date
        LocalDate serveDate = LocalDate.now();
        LocalDateTime serveTime = LocalDateTime.now();

        Food[] noodles = new Food[100];
        noodles[0] = new Food("Spaghetti", 3.90);
        noodles[1] = new Food("Noodle1", 3.2);

        Food[] rice = new Food[100];
        rice[0] = new Food("FriedRice", 4.0);
        rice[1] = new Food("FriedRice2", 5.0);

        // initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[100];
        foodType[0] = new FoodType("Noodle", noodles);
        foodType[1] = new FoodType("Rice", rice);

        // user enter and validate reserve date
        do {
            System.out.print("Enter food reserve date (DD-MM-YYYY): ");
            foodServeDate = scanner.nextLine();
            // figure out split how to do
            foodServeDate.split("-");
            serveDate = LocalDate.of(22, 11, 2002);
        } while (FoodOrder.validateServeDate(serveDate, serveTime));

        do {
            for (i = 0; i < 100; i++) {
                if (foodType[i] == null)
                    break;
                else
                    System.out.print((i + 1) + " " + foodType[i].getTypeName() + "\n");
            }
            System.out.print("Enter your choice on food type: ");
            foodTypeChoice = scanner.nextInt() - 1;

            displayMenu(foodType, foodTypeChoice);
            System.out.print("Enter your choice of food: ");
            foodChoice = scanner.nextInt() - 1;
            System.out.print("Enter the number of food: ");
            quantityOfFood = scanner.nextInt() - 1;

            // pass data to food order
            // if foodCount =0, means no order yet
            if (foodOrder.getFoodCount() == 0) {
                foodOrder = new FoodOrder(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood, serveDate,
                        serveTime);
            } else {
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            }

            System.out.print("Continue add food ?(Y/N) ");
            continueAddFood = scanner.next().charAt(0);
        } while (continueAddFood == 'Y');

        // save data to foodOrderCatalog
        foodOrderCatalog.addFoodOrder(foodOrder);
        foodOrder.generateOrderReceipt();

    }

    public static void displayMenu(FoodType[] foodType, int foodTypeChoice) {
        int i;
        System.out.println("              MENU              ");
        System.out.println("--------------------------------");
        for (i = 0; i < 100; i++) {
            if (foodType[foodTypeChoice].getFood(i) == null)
                break;

            System.out.print((i + 1) + " " + foodType[foodTypeChoice].getFood(i).toString() + "\n");
        }
    }
}
