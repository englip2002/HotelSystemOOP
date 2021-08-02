public class Food {
    private String foodName;
    private FoodType foodType;
    private double price;

    public Food(String foodName, FoodType foodType, double price) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.price = price;
    }

    public Food() {
    };

    public String toString() {
        return String.format("%s %s %s", foodName,foodType,price);
        //String.format("Food Name: %-10s\nFood Type: %-10s\nPrice: %-.2lf\n", foodName, foodType, price);
    }

    public double getPrice(){
        return price;
    }
}
