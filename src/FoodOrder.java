import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodOrder {
    private int OrderID;
    private Food[] food=new Food[100]; //need to do order more food? food[] and quantity[]
    private int[] quantity=new int[100];
    private double subtotal=0;
    private LocalDateTime serveTime;
    private static int foodCount=0;

    //how to generate order id automatically
    public FoodOrder() {
    };

    public FoodOrder(Food food, int quantity, LocalDateTime serveTime) {
        this.food[foodCount] = new Food(food);
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        this.serveTime = serveTime;
        foodCount++;
    }

    //getter setter
    public int getOrderID() {
        return OrderID;
    }

    public int getFoodCount() {
        return foodCount;
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

    public static boolean validateServeDate(LocalDateTime serveTime){
        if(serveTime.isAfter(LocalDateTime.now()))
            return true;
        else 
            return false;
    }

    public void cancel() {
        new FoodOrder();
    }

    private String loopFood(){
        String foodString=new String();
        for(int i=0;i<foodCount;i++){
            foodString=foodString.concat(String.format("%s %14d %24.2f\n",food[i].toString(),quantity[i],(food[i].getPrice()*quantity[i])));
            //foodString=foodString.concat(food[i].toString()+quantity[i]+"\n");
        }
        return foodString;
    }

    public String generateOrderReceipt() {
        DateTimeFormatter Timeformatter= DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter Dateformatter= DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return  
        "\n------------------------------------------------------\n" +
        "                      ORDER SUMMARY                 \n" +
        "------------------------------------------------------\n" +
        
        String.format("\n%-20s %-20s %-20s %-20s\n", "Food Ordered","Unit Price","Quantity","Subtotal(RM)")+
        "----------------------------------------------------------------------------------\n"+
        String.format("%s", loopFood())+
        String.format("\n%s %61.2f\n","Total(RM)",subtotal)+
        String.format("\nServe Date    %s \n",serveTime.format(Dateformatter))+
        String.format("Serve Time    %s\n",serveTime.format(Timeformatter));
        
    }



}