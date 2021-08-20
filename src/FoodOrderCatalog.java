public class FoodOrderCatalog {
    private FoodOrder[] foodOrderList= new FoodOrder[100];
    private static int orderCount=0;

    public FoodOrderCatalog(){}

    public void addFoodOrderCatalog(FoodOrder order){
        foodOrderList[orderCount]=order;
        orderCount++;
    }

    public void generateOrderRecord(int orderID) {
        int i=0;
        boolean orderFound=false;
        for(i=0;i<orderCount;i++){
            if(foodOrderList[i].getOrderID()==orderID){
                foodOrderList[i].generateOrderReceipt();
                orderFound=true;
            }
        }

        if(orderFound==false){
            System.out.println("Order ID not found!");
        }
    }
}
