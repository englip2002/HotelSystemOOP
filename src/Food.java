public class Food {
    private String foodName;
    private double price;

    public Food(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    public Food() {
    };


    public double getPrice(){
        return price;
    }

    public String toString() {
        return String.format("%s %s", foodName,price);
        //String.format("Food Name: %-10s\nFood Type: %-10s\nPrice: %-.2lf\n", foodName, foodType, price);
    }

}
