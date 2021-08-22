import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EngLip_driverProgram {
    public static void main(String[] args) {
        int i, foodTypeChoice, foodChoice, quantityOfFood;
        char continueAddFood = 'N';
        String serveString;
        ArrayList<String> serveArray=new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        FoodOrder foodOrder = new FoodOrder();
        FoodOrderCatalog foodOrderCatalog = new FoodOrderCatalog();

        LocalDateTime serveTime=LocalDateTime.now();

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

        do{
            System.out.print("Enter food reserve date (DD-MM-YYYY): ");
            serveString = scanner.nextLine();
            //split the string, convert to int and parse to serveDate
            serveArray.addAll(Arrays.asList(serveString.split("-")));

            System.out.print("Enter food reserve time (HH:MM): ");
            serveString = scanner.nextLine();
            //split the string, convert to int and parse to serveDate
            serveArray.addAll(Arrays.asList(serveString.split(":")));
            serveTime=LocalDateTime.of(Integer.parseInt(serveArray.get(2)), Integer.parseInt(serveArray.get(1)), Integer.parseInt(serveArray.get(0)), Integer.parseInt(serveArray.get(3)), Integer.parseInt(serveArray.get(4)));

        }while (!FoodOrder.validateServeDate(serveTime));

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
            quantityOfFood = scanner.nextInt();

            // pass data to food order
            // if foodCount =0, means no order yet
            if (foodOrder.getFoodCount() == 0) {
                foodOrder = new FoodOrder(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood, serveTime);
            } else {
                foodOrder.addFood(foodType[foodTypeChoice].getFood(foodChoice), quantityOfFood);
            }

            System.out.print("Continue add food ?(Y/N) ");
            continueAddFood = scanner.next().charAt(0);
            continueAddFood= Character.toUpperCase(continueAddFood);
        } while (continueAddFood == 'Y');

        // save data to foodOrderCatalog
        foodOrderCatalog.addFoodOrder(foodOrder);
        System.out.print(foodOrder.generateOrderReceipt());

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
