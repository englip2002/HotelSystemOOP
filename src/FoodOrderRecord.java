public class FoodOrderRecord {
    private FoodOrder[] foodOrderList = new FoodOrder[100];
    private static int orderCount = 0;

    public FoodOrderRecord() {
    }

    public void addFoodOrder(FoodOrder order) {
        foodOrderList[orderCount] = order;
        orderCount++;
    }

    // make changes use generate order record by Object
    public void generateOrderRecord(Object obj) {
        int i = 0;
        boolean orderFound = false;
        if (obj instanceof Reservation) {
            for (i = 0; i < orderCount; i++) {
                if (foodOrderList[i].getOrderID().equalsIgnoreCase(((Reservation)obj).getReservationID())) {
                    foodOrderList[i].generateOrderReceipt();
                    orderFound = true;
                }
            }
        }

        if (orderFound == false) {
            System.out.println("Order ID not found!");
        }
    }
}