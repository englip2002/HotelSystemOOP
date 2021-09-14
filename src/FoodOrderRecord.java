
public class FoodOrderRecord { 
    private FoodOrder[] foodOrderList;
    private static int orderCount = 0;

    public FoodOrderRecord() {
        foodOrderList = new FoodOrder[100];
    }

    public void addFoodOrder(FoodOrder order) {
        foodOrderList[orderCount] = order;
        orderCount++;
    }

    public String generateFoodOrderRecord(String orderID) {
        int i = 0;
        for (i = 0; i < orderCount; i++) {
            if (foodOrderList[i].getOrderID().equalsIgnoreCase(orderID)) {
                return foodOrderList[i].generateReport();
            }
        }
        return "Order ID not found!";
    }


    // make changes use generate order record by Object 
    public String generateFoodOrderRecord(Object obj) {
        int i = 0;
        if (obj instanceof Reservation) {
            for (i = 0; i < orderCount; i++) {
                if (foodOrderList[i].getOrderID().equalsIgnoreCase(((Reservation)obj).getOrderID())) {
                    return foodOrderList[i].generateReport();
                }
            }
        }

        return "OrderID not found!";
    }
}