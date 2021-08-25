public class FoodType {
    final int LIMIT = 100;
    private String typeName;
    private Food[] foods = new Food[LIMIT];
    private int numOfFood;

    public FoodType(String typeName, Food[] foods) {
        this.typeName = typeName;
        this.foods=foods;
        numOfFood= foods.length;
    }

    public FoodType() {
    };

    public Food getFood(int i){
        return foods[i];
    }

    public String getTypeName() {
        return typeName;
    }

    public int getNumOfFood(){
        return numOfFood;
    }
}
