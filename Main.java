import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Book> catalog = new ArrayList<>();
    private static User user;
    private static Cart cart = new Cart();

    public static void main(String[] args) {
        setupCatalog();
        welcomeMessage();
        registerUser();
        showCatalog();
        shoppingCartFlow();
        displayCartAndTotal();
        applyDiscountAndProceedToCheckout();
        placeOrder();
        showOrderHistory();
    }

    // Step 1: Set up the catalog of books
    private static void setupCatalog() {
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
    }

    // Step 2: Welcome message
    private static void welcomeMessage() {
        System.out.println("Welcome to the Online Bookstore!");
    }

    // Step 3: User registration
    private static void registerUser() {
        System.out.println("\nPlease register:");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        user = new User(name, email, address);
        System.out.println("Registration successful! Welcome, " + user.getName() + ".");
    }

    // Step 4: Show catalog to the user
    private static void showCatalog() {
        System.out.println("\nHere is our book catalog:");
        for (Book book : catalog) {
            System.out.println(book.getBookId() + ". " + book.getTitle() + " by " + book.getAuthor() +
                    " - $" + book.getPrice() + " (" + book.getCategory() + ")");
        }
    }

    // Step 5: Shopping cart flow
    private static void shoppingCartFlow() {
        OUTER:
        while (true) {
            showCartOptions();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addBookToCart();
                    break;
                case 2:
                    removeBookFromCart();
                    break;
                case 3:
                    if (cart.getItems().isEmpty()) {
                        System.out.println("Your cart is empty. Add items before proceeding to checkout.");
                    } else {
                        break OUTER;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Show options for the user
    private static void showCartOptions() {
        System.out.println("\nOptions:");
        System.out.println("1. Add a book to the cart");
        System.out.println("2. Remove a book from the cart");
        System.out.println("3. Proceed to checkout");
        System.out.print("Enter your choice: ");
    }

    // Add a book to the cart
    private static void addBookToCart() {
        System.out.print("Enter the book ID to add: ");
        int bookId = scanner.nextInt();
        Book selectedBook = findBookById(bookId);
        if (selectedBook == null) {
            System.out.println("Invalid book ID. Please try again.");
        } else {
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            cart.addItem(new CartItem(selectedBook, quantity));
            System.out.println(quantity + " copies of \"" + selectedBook.getTitle() + "\" added to your cart.");
        }
    }

    // Remove a book from the cart
    private static void removeBookFromCart() {
        System.out.println("\nYour current cart:");
        List<CartItem> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            displayCartItems(items);
            System.out.print("Enter the item number to remove: ");
            int itemIndex = scanner.nextInt() - 1;
            if (itemIndex >= 0 && itemIndex < items.size()) {
                CartItem itemToRemove = items.get(itemIndex);
                cart.removeItem(itemToRemove);
                System.out.println("Removed \"" + itemToRemove.getBook().getTitle() + "\" from your cart.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        }
    }

    // Display cart items
    private static void displayCartItems(List<CartItem> items) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            System.out.println((i + 1) + ". " + item.getBook().getTitle() + " - Quantity: " + item.getQuantity());
        }
    }

    // Find book by ID
    private static Book findBookById(int bookId) {
        for (Book book : catalog) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    // Step 6: Display cart and calculate total
    private static void displayCartAndTotal() {
        System.out.println("\nYour Cart:");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getBook().getTitle() + " - Quantity: " + item.getQuantity() +
                               ", Subtotal: $" + item.calculatePrice());
        }
        double totalCost = cart.calculateTotalCost();
        System.out.println("Total cost before discount: $" + totalCost);
    }

    // Step 7: Apply discount and proceed to checkout
    private static void applyDiscountAndProceedToCheckout() {
        System.out.print("Enter discount percentage (0 if none): ");
        double discount = scanner.nextDouble();
        cart.applyDiscount(discount);
        double totalCost = cart.calculateTotalCost();
        System.out.println("Total cost after discount: $" + totalCost);
    }

    // Step 8: Place the order
    private static void placeOrder() {
        double totalCost = cart.calculateTotalCost();
        Order order = new Order(user, cart.getItems(), totalCost);
        order.saveOrderToHistory();
        System.out.println("\nOrder placed successfully!");
        System.out.println("Order Summary:");
        order.generateInvoice();
    }

    // Step 9: Show order history
    private static void showOrderHistory() {
        System.out.println("\nOrder history for " + user.getName() + ":");
        for (Order pastOrder : user.getOrderHistory()) {
            pastOrder.generateInvoice();
        }
    }
}
