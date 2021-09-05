public class Food {
    private String foodName;
    private double price;

    public Food() {
    };
    
    //for food initialization purpose
    public Food(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    //for pass food to food order purpose
    public Food(Food foodOrder) {
        this.foodName = foodOrder.foodName;
        this.price = foodOrder.price;
    }

    public double getPrice(){
        return price;
    }
    public String getFoodName() {
        return foodName;
    }

    public String toString() {
        return String.format("%-30s %-10.2f", foodName,price);
    }

}
