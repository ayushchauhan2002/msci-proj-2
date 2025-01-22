import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
 
        // Step 1: Create a catalog of books
        List<Book> catalog = new ArrayList<>();
        catalog.add(new Book(1, "Effective Java", "Joshua Bloch", 45.0, "Programming"));
        catalog.add(new Book(2, "Clean Code", "Robert Martin", 50.0, "Programming"));
        catalog.add(new Book(3, "The Pragmatic Programmer", "Andy Hunt", 40.0, "Programming"));
        catalog.add(new Book(4, "Introduction to Algorithms", "CLRS", 100.0, "Algorithms"));
        catalog.add(new Book(5, "The White Tiger", "Aravind Adiga", 15.0, "Fiction"));
        catalog.add(new Book(6, "Midnight's Children", "Salman Rushdie", 20.0, "Fiction"));
        catalog.add(new Book(7, "India After Gandhi", "Ramachandra Guha", 30.0, "History"));
        catalog.add(new Book(8, "The Guide", "R.K. Narayan", 12.0, "Fiction"));
        catalog.add(new Book(9, "Train to Pakistan", "Khushwant Singh", 18.0, "Historical Fiction"));
        catalog.add(new Book(10, "Wings of Fire", "A.P.J. Abdul Kalam", 10.0, "Autobiography"));
        catalog.add(new Book(11, "The Discovery of India", "Jawaharlal Nehru", 25.0, "History"));
        catalog.add(new Book(12, "A Suitable Boy", "Vikram Seth", 35.0, "Fiction"));
 
        System.out.println("Welcome to the Online Bookstore!");
 
        // Step 2: User registration
        System.out.println("\nPlease register:");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        User user = new User(name, email, address);
        System.out.println("Registration successful! Welcome, " + user.getName() + ".");
 
        // Step 3: Show catalog to the user
        System.out.println("\nHere is our book catalog:");
        for (Book book : catalog) {
            System.out.println(book.getBookId() + ". " + book.getTitle() + " by " + book.getAuthor() +
                    " - $" + book.getPrice() + " (" + book.getCategory() + ")");
        }
 
        // Step 4: Shopping cart
        Cart cart = new Cart();
 
        OUTER:
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a book to the cart");
            System.out.println("2. Remove a book from the cart");
            System.out.println("3. Proceed to checkout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Add a book to the cart
                    System.out.print("Enter the book ID to add: ");
                    int bookId = scanner.nextInt();
                    Book selectedBook = null;
                    for (Book book : catalog) {
                        if (book.getBookId() == bookId) {
                            selectedBook = book;
                            break;
                        }
                    }   if (selectedBook == null) {
                        System.out.println("Invalid book ID. Please try again.");
                    } else {
                        System.out.print("Enter the quantity: ");
                        int quantity = scanner.nextInt();
                        cart.addItem(new CartItem(selectedBook, quantity));
                        System.out.println(quantity + " copies of \"" + selectedBook.getTitle() + "\" added to your cart.");
                    }   break;
                case 2:
                    // Remove a book from the cart
                    System.out.println("\nYour current cart:");
                    List<CartItem> items = cart.getItems();
                    if (items.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        for (int i = 0; i < items.size(); i++) {
                            System.out.println((i + 1) + ". " + items.get(i).getBook().getTitle() +
                                    " - Quantity: " + items.get(i).getQuantity());
                        }
                        System.out.print("Enter the item number to remove: ");
                        int itemIndex = scanner.nextInt() - 1;
                        if (itemIndex >= 0 && itemIndex < items.size()) {
                            System.out.println("Removed \"" + items.get(itemIndex).getBook().getTitle() + "\" from your cart.");
                            cart.removeItem(items.get(itemIndex));
                        } else {
                            System.out.println("Invalid item number. Please try again.");
                        }
                    }   break;
                case 3:
                    // Proceed to checkout
                    if (cart.getItems().isEmpty()) {
                        System.out.println("Your cart is empty. Add items before proceeding to checkout.");
                    } else {
                        break OUTER;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
 
        // Step 5: Display cart items and calculate total cost
        System.out.println("\nYour Cart:");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getBook().getTitle() + " - Quantity: " + item.getQuantity() +
                               ", Subtotal: $" + item.calculatePrice());
        }
        double totalCost = cart.calculateTotalCost();
        System.out.println("Total cost before discount: $" + totalCost);
 
        // Step 6: Apply discount
        System.out.print("Enter discount percentage (0 if none): ");
        double discount = scanner.nextDouble();
        cart.applyDiscount(discount);
        totalCost = cart.calculateTotalCost();
        System.out.println("Total cost after discount: $" + totalCost);
 
        // Step 7: Place the order
        Order order = new Order(user, cart.getItems(), totalCost);
        order.saveOrderToHistory();
        System.out.println("\nOrder placed successfully!");
        System.out.println("Order Summary:");
        order.generateInvoice();
 
        // Step 8: Display order history
        System.out.println("\nOrder history for " + user.getName() + ":");
        for (Order pastOrder : user.getOrderHistory()) {
            pastOrder.generateInvoice();;
        }
 
        scanner.close();
    }
}