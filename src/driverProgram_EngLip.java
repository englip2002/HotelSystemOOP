import java.time.LocalDate;
import java.time.LocalDateTime;

public class driverProgram_EngLip {
    public static void main(String[] args) {

        //initialize food data
        Food[] noodles = new Food[100];
        noodles[0] = new Food("Spaghetti", 3.90);

        //initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[100];
        foodType[0]=new FoodType("Noodle",noodles);

        System.out.println(noodles[0].toString());

        //example how construct date and time
        LocalDate date = LocalDate.of(2020, 1, 22);
        LocalDateTime time = date.atTime(14, 3, 20);
        System.out.println(date);
        System.out.println(time.getHour()+":"+time.getMinute());


        /*when need to order, choose food type, get index number, loop foods in that foodType,
          then get index number of food, then pass to order with time*/ 
        int i=0,j=0;
        FoodOrder order=new FoodOrder(foodType[i].getFood(j), 2, 2020, 11, 11, 13, 15);
        System.out.println(order.toString());


        //payment by card
        Bank bank=new Bank("CIMB");
        PaymentByCard card=new PaymentByCard(123,"1234 5678 1234 5678", bank, 123);
        card.calculateTaxAmount();
        card.calculateTotalAmount();
        System.out.println(card.generateReceipt());

    }
}
