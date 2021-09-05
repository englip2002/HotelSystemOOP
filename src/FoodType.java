public class FoodType {
    private String typeName;
    private Food[] foods;
    private static int numOfFoodType=0;

    public FoodType() {
    };

    public FoodType(String typeName, Food[] foods) {
        this.typeName = typeName;
        this.foods=foods;
        numOfFoodType++;
    }

    public Food getFood(int foodIndex){
        return foods[foodIndex];
    }

    public String getTypeName() {
        return typeName;
    }

    public static int getNumOfFoodType(){
        return numOfFoodType;
    }

    public int getNumOfFood(){
        return foods.length;
    }
}
