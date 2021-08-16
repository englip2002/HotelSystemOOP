import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodOrder {
    private Food[] food; //need to do order more food? food[] and quantity[]
    private int[] quantity;
    private double subtotal=0;
    private LocalDate serveDate;
    private LocalDateTime serveTime;
    private static int foodCount=0;

    public FoodOrder() {
    };

    public FoodOrder(Food food, int quantity, int serveYear, int serveMonth, int serveDay, int hour, int minutes) {
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        this.serveDate = LocalDate.of(serveYear, serveMonth, serveDay);
        this.serveTime = serveDate.atTime(hour, minutes);
        foodCount++;
    }

    public void addFood(Food food, int quantity){
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        foodCount++;
    }

    public void calculateSubtotal(){
        for(int i=0;i<foodCount;i++){
            subtotal+=food[0].getPrice()*quantity[0];
        }
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

    public String generateOrderReceipt() {
        // convert date to string
        //loop foods and quantity
        //calculate subtotal
        //calculate tax
        //calculate total
        return food.toString() + " " + quantity + " " + subtotal + " " + serveDate.toString() + " "
                + serveTime.getHour() + ":" + serveTime.getMinute();
    }

}