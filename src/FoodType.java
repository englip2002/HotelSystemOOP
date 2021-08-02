
public class FoodType {
    private String typeName;
    private static int amountOfFood = 0;

    public FoodType(String typeName) {
        this.typeName = typeName;
        amountOfFood++;
    }

    public FoodType() {
        amountOfFood++;
    };

    public String getTypeName() {
        return typeName;
    }

    public int getAmountOfFood() {
        return amountOfFood;
    }

    public String toString(){
        return String.format("%s %d", typeName,amountOfFood);
    }

}
