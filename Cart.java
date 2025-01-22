import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private double totalCost;
 
    public void addItem(CartItem item) {
        items.add(item);
    }
 
    public void removeItem(CartItem item) {
        items.remove(item);
    }
 
    public double calculateTotalCost() {
        totalCost = items.stream().mapToDouble(CartItem::calculatePrice).sum();
        return totalCost;
    }
 
    public void applyDiscount(double discountPercentage) {
        totalCost -= totalCost * (discountPercentage / 100);
    }
 
    public List<CartItem> getItems() {
        return items;
    }
}