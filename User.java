import java.util.ArrayList;
import java.util.List;
 
public class User {
    private String name;
    private String email;
    private String address;
    private final List<Order> orderHistory;
 
    // Constructor
    public User(String name, String email, String address) {
    this.name = name;
    this.email = email;
        this.address = address;
        this.orderHistory = new ArrayList<>();
    }
 
    // Getters and Setters
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
this.email = email;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
    public List<Order> getOrderHistory() {
        return orderHistory;
    }
 
    // Methods
    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }
 
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", orderHistory=" + orderHistory.size() +
                '}';
    }
}
