import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodOrder {
    private Food[] food=new Food[100]; //need to do order more food? food[] and quantity[]
    private int[] quantity=new int[100];
    private double subtotal=0;
    private LocalDate serveDate;
    private LocalDateTime serveTime;
    private static int foodCount=0;

    public FoodOrder() {
    };

    public FoodOrder(Food food, int quantity, int serveYear, int serveMonth, int serveDay, int hour, int minutes) {
        this.food[foodCount] = new Food(food);
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        this.serveDate = LocalDate.of(serveYear, serveMonth, serveDay);
        this.serveTime = serveDate.atTime(hour, minutes);
        foodCount++;
    }

    public void addFood(Food food, int quantity){
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        foodCount++;
    }

    public void calculateSubtotal(){
        subtotal+=food[foodCount].getPrice()*quantity[foodCount];
    }

    public boolean validateServeDate(){
        if(serveDate.compareTo(LocalDate.now())>=0){
            if(serveTime.compareTo(LocalDateTime.now())>=0)
                return true;
            else 
                return false;
        }
        else 
            return false;
    }

    public void cancel() {
        new FoodOrder();
    }

    private String loopFood(){
        String foodString=new String();
        for(int i=0;i<foodCount;i++){
            foodString=foodString.concat(String.format("%s %14d %24.2f\n",food[i].toString(),quantity[i],food[i].getPrice()*quantity[i]));
            //foodString=foodString.concat(food[i].toString()+quantity[i]+"\n");
        }
        return foodString;
    }

    public String generateOrderReceipt() {
        DateTimeFormatter Timeformatter= DateTimeFormatter.ofPattern("HH:mm");
        return  
        "\n------------------------------------------------------\n" +
        "                      ORDER SUMMARY                 \n" +
        "------------------------------------------------------\n" +
        
        String.format("\n%-20s %-20s %-20s %-20s\n", "Food Ordered","Unit Price","Quantity","Subtotal(RM)")+
        "----------------------------------------------------------------------------------\n"+
        String.format("%s", loopFood())+
        String.format("\n%-65s %.2f\n","Total(RM)",subtotal)+
        String.format("\nServe Date    %s \n",serveDate.toString())+
        String.format("Serve Time    %s\n",serveTime.format(Timeformatter));
        
        
        //return food.toString() + " " + quantity + " " + subtotal + " " + serveDate.toString() + " " + serveTime.getHour() + ":" + serveTime.getMinute();
    }

}