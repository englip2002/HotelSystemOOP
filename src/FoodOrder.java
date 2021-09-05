import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodOrder{
    private String OrderID;
    private Food[] food = new Food[100];
    private int[] quantity = new int[100];
    private double subtotal = 0;
    private LocalDateTime serveTime;
    private int foodCount = 0;
    private static int orderCount = 0;

    public FoodOrder() {

    };

    public String generateOrderID() {
        return String.format("O%03d", orderCount+1);
    }

    public FoodOrder(Food food, int quantity, LocalDateTime serveTime) {
        this.food[foodCount] = new Food(food);
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        this.serveTime = serveTime;
        OrderID = OrderID + orderCount;
        OrderID=generateOrderID();

        foodCount++;
        orderCount++;
    }

    // getter setter
    public String getOrderID() {
        return OrderID;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public LocalDateTime getServeDate() {
        return serveTime;
    }

    public void addFood(Food food, int quantity) {
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        calculateSubtotal();
        foodCount++;
    }

    public void calculateSubtotal() {
        subtotal += food[foodCount].getPrice() * quantity[foodCount];
    }

    public void cancel() {
        new FoodOrder();
    }

    private String loopFood() {
        String foodString = new String();
        for (int i = 0; i < foodCount; i++) {
            foodString = foodString.concat(String.format("%s %14d %24.2f\n", food[i].toString(), quantity[i],
                    (food[i].getPrice() * quantity[i])));
            // foodString=foodString.concat(food[i].toString()+quantity[i]+"\n");
        }
        return foodString;
    }

    public String generateOrderReceipt() {
        DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return "\n----------------------------------------------------------------------------------------\n"
                + "                                       ORDER SUMMARY                 \n"
                + "----------------------------------------------------------------------------------------\n" +

                String.format("\n%-30s %-20s %-20s %-20s\n", "Food Ordered", "Unit Price", "Quantity", "Subtotal(RM)")
                + "----------------------------------------------------------------------------------------\n"
                + String.format("%s", loopFood()) + String.format("\n%s %71.2f\n", "Total(RM)", subtotal)
                + String.format("\nServe Date    %s \n", serveTime.format(Dateformatter))
                + String.format("Serve Time    %s\n", serveTime.format(Timeformatter));

    }

}