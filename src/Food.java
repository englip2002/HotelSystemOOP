public class Food {
    private String foodName;
    private double price;

    public Food(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    public Food(Food foodOrder) {
        this.foodName = foodOrder.foodName;
        this.price = foodOrder.price;
    }

    public Food() {
    };

    public double getPrice(){
        return price;
    }
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return String.format("%-30s %-10.2f", foodName,price);
    }

}
