public class FoodType {
    final int LIMIT = 100;
    private String typeName;
    private Food[] foods = new Food[LIMIT];
    private static int amountOfFood = 0;

    public FoodType(String typeName, Food[] foods) {
        this.typeName = typeName;
        this.foods=foods;
        amountOfFood++;
    }

    public FoodType() {
        amountOfFood++;
    };

    public Food getFood(int i){
        return foods[i];
    }

    public String getTypeName() {
        return typeName;
    }

    public int getAmountOfFood() {
        return amountOfFood;
    }

    public String toString() {
        return String.format("%s %d", typeName, amountOfFood);
    }

}
