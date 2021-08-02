import java.time.LocalDate;
import java.time.LocalDateTime;

public class driverProgram {
    public static void main(String[] args) {

        //apa jaychou diu lei lou mou

        //apa jaychou chibai kia ss 


        FoodType noodle = new FoodType("Noodle");
        Food[] food = new Food[100];
        food[0] = new Food("Spaghetti", noodle, 3.90);
        System.out.println(food[0].toString());

        LocalDate date = LocalDate.of(2020, 1, 22);
        LocalDateTime time = date.atTime(14, 3, 20);
        System.out.println(date);
        System.out.println(time.getHour()+":"+time.getMinute());


        FoodOrder order=new FoodOrder(food[0], 1, 2020, 11, 11, 13, 15);
        System.out.println(order.toString());

    }
}
