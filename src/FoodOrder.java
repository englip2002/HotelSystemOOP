import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FoodOrder implements Reportable{
    private String OrderID;
    private Food[] food;
    private int[] quantity;
    private double subtotal;
    private LocalDateTime serveTime;
    private int foodCount;
    private static int orderCount = 0;

    public FoodOrder() {
        final int LIMIT=100;
        orderCount++;
        OrderID = String.format("O%03d", orderCount);
        food=new Food[LIMIT];
        quantity=new int[LIMIT];
        subtotal=0;
        foodCount=0;
    }

    // getter setter
    public String getOrderID() {
        return OrderID;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setServeTime(LocalDateTime serveTime) {
        this.serveTime=serveTime;
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

    private String compileOrderedFood() {
        String foodString = new String();
        for (int i = 0; i < foodCount; i++) {
            foodString = foodString.concat(String.format("%s %14d %24.2f\n", food[i].toString(), quantity[i],
                    (food[i].getPrice() * quantity[i])));
        }
        return foodString;
    }

    @Override
    public String generateReport() {
        DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return "\n----------------------------------------------------------------------------------------\n"
                + "                                       ORDER SUMMARY                 \n"
                + "----------------------------------------------------------------------------------------\n" +

                String.format("%-30s %-20s %-20s %-20s\n", "Food Ordered", "Unit Price", "Quantity", "Subtotal(RM)")
                + "----------------------------------------------------------------------------------------\n"
                + String.format("%s", compileOrderedFood()) + String.format("\n%s %71.2f\n", "Total(RM)", subtotal)
                + String.format("\nOrder ID                    : %-20s", OrderID)
                + String.format("\nServe Date (DD-MM-YYYY)     : %-20s", serveTime.format(Dateformatter))
                + String.format("\nServe Time (HH:MM)          : %-20s\n", serveTime.format(Timeformatter));

    }

}