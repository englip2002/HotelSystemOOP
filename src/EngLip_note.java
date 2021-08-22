import java.time.LocalDate;
import java.time.LocalDateTime;

public class EngLip_note {
    public static void main(String[] args) {

        //initialize food data
        Food[] noodles = new Food[100];
        noodles[0] = new Food("Spaghetti", 3.90);
        noodles[1]=new Food("Noodle1", 3.2);

        Food[] rice=new Food[100];
        rice[0]=new Food("FriedRice", 4.0);
        rice[1]=new Food("FriedRice2",5.0);

        //initialize foodType and pass foods to foodtype
        FoodType[] foodType = new FoodType[100];
        foodType[0]=new FoodType("Noodle",noodles);
        foodType[1]=new FoodType("Rice", rice);

        //System.out.print(noodles[0].toString());

        //example how construct date and time
        LocalDate date = LocalDate.of(2020, 1, 22);
        LocalDateTime time = date.atTime(14, 3, 20);
        System.out.println(date);
        System.out.println(time.getHour()+":"+time.getMinute());


        /*when need to order, choose food type, get index number, loop foods in that foodType,
          then get index number of food, then pass to order with time*/ 
        int i=0,j=0;
        FoodOrder order=new FoodOrder(foodType[i].getFood(j),1, time);
        order.addFood(foodType[1].getFood(1), 1);
        System.out.print(order.generateOrderReceipt());
        displayMenu(foodType,0);

    }

    public static void displayMenu(FoodType[] foodTypes, int foodTypeChoice){
      int i;
      System.out.println("              MENU              ");
      System.out.println("--------------------------------");
      for(i=0;i<100;i++){
         if(foodTypes[foodTypeChoice].getFood(i)==null)
          break;
         
        System.out.print(foodTypes[foodTypeChoice].getFood(i).toString()+"\n");
      }
    }


  }

