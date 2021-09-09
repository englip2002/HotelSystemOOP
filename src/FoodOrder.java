import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodOrder {
    private String OrderID;
    private Food[] food;
    private int[] quantity;
    private double subtotal;
    private LocalDateTime serveTime;
    private int foodCount;
    private static int orderCount = 0;

    public FoodOrder() {
        final int LIMIT=100;
        food=new Food[LIMIT];
        quantity=new int[LIMIT];
        subtotal=0;
        foodCount=0;
    };

    private String generateOrderID() {
        return String.format("O%03d", orderCount + 1);
    }

    public FoodOrder(Food food, int quantity, LocalDateTime serveTime) {
        OrderID = generateOrderID();
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        subtotal=0;
        subtotal=calculateSubtotal();
        this.serveTime = serveTime;
        foodCount=1;
        orderCount++;
    }

    // getter setter
    public String getOrderID() {
        return OrderID;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void addFood(Food food, int quantity) {
        this.food[foodCount] = food;
        this.quantity[foodCount] = quantity;
        subtotal=calculateSubtotal();
        foodCount++;
    }

    private double calculateSubtotal() {
        return subtotal+(food[foodCount].getPrice() * quantity[foodCount]);
    }

    private String CompileOrderedFood() {
        String foodString = new String();
        for (int i = 0; i < foodCount; i++) {
            foodString = foodString.concat(String.format("%s %14d %24.2f\n", food[i].toString(), quantity[i],
                    (food[i].getPrice() * quantity[i])));
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
                + String.format("%s", CompileOrderedFood()) + String.format("\n%s %71.2f\n", "Total(RM)", subtotal)
                + String.format("\nServe Date:       %s \n", serveTime.format(Dateformatter))
                + String.format("Serve Time  :     %s\n", serveTime.format(Timeformatter));

    }

}