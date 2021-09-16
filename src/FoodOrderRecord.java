import java.util.Comparator;

public class FoodOrderRecord implements Comparator<Object>{ 
    private FoodOrder[] foodOrderList;
    private static int orderCount = 0;

    public FoodOrderRecord() {
        foodOrderList = new FoodOrder[100];
    }

    public void addFoodOrder(FoodOrder order) {
        foodOrderList[orderCount] = order;
        orderCount++;
    }

    // make changes use generate order record by Object 
    public String generateFoodOrderRecord(Object obj) {
        int i = 0;
        if (obj instanceof Reservation) {
            for (i = 0; i < orderCount; i++) {
                if (compare(foodOrderList[i], obj)==1) {
                    return foodOrderList[i].generateReport();
                }
            }
        }

        return "Order Record not found!";
    }

    @Override
    public int compare(Object obj1, Object obj2){
        if(((FoodOrder)obj1).getOrderID().equalsIgnoreCase(((Reservation)obj2).getOrderID()))
            return 1;
        else
            return 0;
    }

}