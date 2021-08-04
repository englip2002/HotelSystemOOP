import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodOrder {
    private Food food;
    private int quantity;
    private double subtotal;
    private LocalDate serveDate;
    private LocalDateTime serveTime;

    public FoodOrder() {
    };

    public FoodOrder(Food food, int quantity, int serveYear, int serveMonth, int serveDay, int hour, int minutes) {
        this.food = food;
        this.quantity = quantity;
        this.subtotal = food.getPrice() * quantity;
        this.serveDate = LocalDate.of(serveYear, serveMonth, serveDay);
        this.serveTime = serveDate.atTime(hour, minutes);
    }

    public void cancel() {
        new FoodOrder();
    }

    public String toString() {
        // convert date to string
        return food.toString() + " " + quantity + " " + subtotal + " " + serveDate.toString() + " "
                + serveTime.getHour() + ":" + serveTime.getMinute();
    }

}