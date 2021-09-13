public class FoodType {
    private String typeName;
    private Food[] foods;

    public FoodType() {
    };

    public FoodType(String typeName, Food[] foods) {
        this.typeName = typeName;
        this.foods=foods;
    }

    //get particular food
    public Food getFood(int foodIndex){
        return foods[foodIndex];
    }

    //get food array
    public Food[] getFood(){
        return foods;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getNumOfFood(){
        return foods.length;
    }
}
