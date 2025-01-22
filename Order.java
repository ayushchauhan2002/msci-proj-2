import java.time.LocalDate;
import java.util.List;

public class Order {
    private static int idCounter = 0;
    private int orderId;
    private User user;
    private List<CartItem> orderedItems;
    private double totalCost;
    private LocalDate orderDate;
 
    public Order(User user, List<CartItem> items, double totalCost) {
        this.orderId = ++idCounter;
        this.user = user;
        this.orderedItems = items;
        this.totalCost = totalCost;
        this.orderDate = LocalDate.now();
    }
 
    public void generateInvoice() {
        System.out.println("Order ID: " + orderId);
        System.out.println("User: " + user.getName());
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Cost: " + totalCost);
    }
 
    public void saveOrderToHistory() {
        user.getOrderHistory().add(this);
    }
}