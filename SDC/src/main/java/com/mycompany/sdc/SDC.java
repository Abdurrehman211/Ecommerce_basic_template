/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sdc;
import java.util.Scanner;
/**
 *
 * @author Sony
 */import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    String name;
    String category;
    double price;
    String size;

    Product(String name, String category, double price, String size) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.size = size;
    }

   
    public String toString() {
        return name + " - " + category + " - " + size + " - $" + price;
    }
}

class User {
    String username;
    String password;
    String email;
    List<Product> cart;

    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cart = new ArrayList<>();
    }

    void addToCart(Product product) {
        cart.add(product);
    }

    void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty!");
        } else {
            System.out.println("Your Cart:");
            cart.forEach(product -> System.out.println(product));
        }
    }

    void clearCart() {
        cart.clear();
    }
}

public class SDC {
    List<User> users;
    List<Product> products;
    String ProductName;
    SDC() {
        users = new ArrayList<>();
        products = new ArrayList<>();
        loadProducts();
    }

    //Products in the form hardcoded alues
    void loadProducts() {
        products.add(new Product("T-shirt", "Clothes", 19.99, "M"));
        products.add(new Product("Jeans", "Clothes", 49.99, "L"));
        products.add(new Product("Apple", "Grocery", 1.99, "N/A"));
        products.add(new Product("Milk", "Grocery", 2.50, "N/A"));
    }

    User login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    User signup(String username, String password, String email) {
        for (User user : users) {
            if (user.username.equals(username)) {
                System.out.println("Username already exists!");
                return null;
            }
        }
        User newUser = new User(username, password, email);
        users.add(newUser);
        return newUser;
    }

    void displayProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    void searchAndFilterProducts(String searchTerm, String category, String size) {
        System.out.println("Searching for products...");
        boolean found = false;
        for (Product product : products) {
            if ((category.isEmpty() || product.category.equalsIgnoreCase(category)) &&
                (size.isEmpty() || product.size.equalsIgnoreCase(size)) &&
                (searchTerm.isEmpty() || product.name.toLowerCase().contains(searchTerm.toLowerCase()))) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found with given criteria.");
        }
    }

    void checkout(User user, String paymentMethod, String address) {
        if (user.cart.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        double totalAmount = 0;
        System.out.println("Checkout Summary:");
        for (Product product : user.cart) {
            System.out.println(product);
            totalAmount += product.price;
            ProductName = product.name;
        }
        
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Shipping Address: " + address);
        System.out.println("Order placed successfully!");
        user.clearCart();
    }

    void viewOrderHistory(User user) {
        System.out.println("Your Order History:");
        // In a real app, this would be fetched from a database, but here it's just the user's cart.
        if (ProductName==null) {
            System.out.println("No previous orders.");
        } else {
            System.out.println("Recent order:");
            System.out.println("Previous order Title :"+ProductName);
//            user.viewCart();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SDC ecommerce = new SDC();
        User loggedInUser = null;

        while (true) {
            System.out.println("\nWelcome to the E-Commerce Platform");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            if (choice == 1) {
                // Login process
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                loggedInUser = ecommerce.login(username, password);

                if (loggedInUser == null) {
                    System.out.println("Invalid login credentials.");
                } else {
                    System.out.println("Login successful!");
                    userMenu(loggedInUser, ecommerce, sc);
                }
            } else if (choice == 2) {
                // Sign up process
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                 System.out.print("Enter email: ");
                String email = sc.nextLine();
                boolean validpassword = false; 
                String password = "";
                while(!validpassword){
                System.out.print("Enter password: ");
                 password = sc.nextLine();
                if (password.length()>8){
                    System.out.println("Please enter Password less than 8 letters !!");
                    System.out.println("Re-enter the Password: ");
                    
                }
                else{
                  validpassword = true;
                } 
                }
                
                  loggedInUser = ecommerce.signup(username, password, email);
                if (loggedInUser != null) {
                    System.out.println("Sign-up successful! Please login.");
                }
            } else if (choice == 3) {
                System.out.println("Thank you for visiting! Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void userMenu(User user, SDC ecommerce, Scanner sc) {
        while (true) {
            System.out.println("\nWelcome " + user.username);
            System.out.println("1. Browse Products");
            System.out.println("2. Search and Filter Products");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Order History");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline

            if (choice == 1) {
                ecommerce.displayProducts();
                System.out.print("Enter the product number to add to cart: ");
                int productChoice = sc.nextInt();
                sc.nextLine(); // Consume the newline
                if (productChoice > 0 && productChoice <= ecommerce.products.size()) {
                    user.addToCart(ecommerce.products.get(productChoice - 1));
                    System.out.println("Product added to cart!");
                } else {
                    System.out.println("Invalid product number.");
                }
            } else if (choice == 2) {
                System.out.print("Enter search term: ");
                String searchTerm = sc.nextLine();
                System.out.print("Enter category (Clothes or Grocery, or leave empty): ");
                String category = sc.nextLine();
                System.out.print("Enter size (leave empty if not applicable): ");
                String size = sc.nextLine();
                ecommerce.searchAndFilterProducts(searchTerm, category, size);
            } else if (choice == 3) {
                user.viewCart();
            } else if (choice == 4) {
                System.out.print("Enter payment method (Credit Card, PayPal): ");
                String paymentMethod = sc.nextLine();
                System.out.print("Enter shipping address: ");
                String address = sc.nextLine();
                ecommerce.checkout(user, paymentMethod, address);
            } else if (choice == 5) {
                ecommerce.viewOrderHistory(user);
            } else if (choice == 6) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

