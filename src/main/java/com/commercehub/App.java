
package com.commercehub;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.commercehub.builder.ProductBuilder;
import com.commercehub.model.Product;
import com.commercehub.repository.InMemoryProductRepository;
import com.commercehub.repository.ProductRepository;
import com.commercehub.service.ProductService;
import com.commercehub.strategy.DiscountStrategy;
import com.commercehub.strategy.FixedDiscountStrategy;
import com.commercehub.strategy.NoDiscountStrategy;
import com.commercehub.strategy.PercentageDiscountStrategy;

public class App {

    private static final Scanner scanner =
        new Scanner(System.in);

    public static void main(String[] args) {

        ProductRepository repository =
            new InMemoryProductRepository();

        ProductService service =
            new ProductService(repository);

        boolean running = true;

        while (running) {
            showMenu();

            try {
                int choice = readInt("Choose option: ");

                switch (choice) {
                    case 1 -> addPhysicalProduct(service);
                    case 2 -> addDigitalProduct(service);
                    case 3 -> displayProducts(
                        service.getAllProducts());
                    case 4 -> searchProduct(service);
                    case 5 -> updateStock(service);
                    case 6 -> purchaseProduct(service);
                    case 7 -> deleteProduct(service);
                    case 8 -> calculateTotal(service);
                    case 9 -> displayProducts(
                        service.getProductsSortedById());
                    case 10 -> displayProducts(
                        service.getProductsSortedByPrice());
                    case 11 -> displayProducts(
                        service.getProductsSortedByPriceDesc());
                    case 12 -> displayProducts(
                        service.getProductsSortedByName());
                    case 13 -> displayProducts(
                        service.getProductsSortedByStock());
                    case 14 -> searchByName(service);
                    case 15 -> filterByPrice(service);
                    case 16 -> showProductTypes(service);
                    case 17 -> showInventoryValue(service);
                    case 18 -> calculateDiscountedPrice(service);
                    case 19 -> createUsingBuilder(service);
                    case 0 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println(
                        "Invalid menu option.");
                }

            } catch (RuntimeException e) {
                System.out.println(
                    "Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n===== COMMERCEHUB =====");
        System.out.println("1. Add Physical Product");
        System.out.println("2. Add Digital Product");
        System.out.println("3. View All Products");
        System.out.println("4. Search Product by ID");
        System.out.println("5. Update Stock");
        System.out.println("6. Purchase Product");
        System.out.println("7. Delete Product");
        System.out.println("8. Calculate Total Price");
        System.out.println("9. Sort by ID");
        System.out.println("10. Sort by Price (Low to High)");
        System.out.println("11. Sort by Price (High to Low)");
        System.out.println("12. Sort by Name");
        System.out.println("13. Sort by Stock");
        System.out.println("14. Search by Name");
        System.out.println("15. Filter by Price Range");
        System.out.println("16. View Product Types");
        System.out.println("17. Total Inventory Value");
        System.out.println("18. Calculate Discounted Price");
        System.out.println("19. Create Product Using Builder");
        System.out.println("0. Exit");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(
            scanner.nextLine().trim());
    }

    private static BigDecimal readPrice(String prompt) {
        System.out.print(prompt);
        return new BigDecimal(
            scanner.nextLine().trim());
    }

    private static void addPhysicalProduct(
            ProductService service) {

        int id = readInt("Enter product ID: ");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        BigDecimal price = readPrice("Enter price: ");
        int quantity = readInt("Enter quantity: ");

        System.out.print("Enter weight (kg): ");
        double weight = Double.parseDouble(
            scanner.nextLine().trim());

        service.addPhysicalProduct(
            id, name, price, quantity, weight);

        System.out.println("Physical product added!");
    }

    private static void addDigitalProduct(
            ProductService service) {

        int id = readInt("Enter product ID: ");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        BigDecimal price = readPrice("Enter price: ");
        int quantity = readInt("Enter quantity: ");

        System.out.print("Enter download URL: ");
        String url = scanner.nextLine();

        service.addDigitalProduct(
            id, name, price, quantity, url);

        System.out.println("Digital product added!");
    }

    private static void displayProducts(
            List<Product> products) {

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void searchProduct(
            ProductService service) {

        int id = readInt("Enter product ID: ");
        System.out.println(service.getProduct(id));
    }

    private static void updateStock(
            ProductService service) {

        int id = readInt("Enter product ID: ");
        int quantity = readInt("Enter new quantity: ");

        service.updateStock(id, quantity);
        System.out.println("Stock updated!");
    }

    private static void purchaseProduct(
            ProductService service) {

        int id = readInt("Enter product ID: ");
        int quantity = readInt("Enter purchase quantity: ");

        service.purchaseProduct(id, quantity);
        System.out.println("Purchase successful!");
    }

    private static void deleteProduct(
            ProductService service) {

        int id = readInt("Enter product ID: ");

        service.deleteProduct(id);
        System.out.println("Product deleted!");
    }

    private static void calculateTotal(
            ProductService service) {

        int id = readInt("Enter product ID: ");
        Product product = service.getProduct(id);

        System.out.println("Product: " + product.getName());
        System.out.println("Base price: $" + product.getPrice());
        System.out.println("Delivery: $" +
            product.getDeliveryCost());
        System.out.println("Total: $" +
            service.calculateTotalPrice(id));
    }

    private static void searchByName(
            ProductService service) {

        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();

        displayProducts(service.searchByName(keyword));
    }

    private static void filterByPrice(
            ProductService service) {

        BigDecimal min = readPrice("Minimum price: ");
        BigDecimal max = readPrice("Maximum price: ");

        displayProducts(service.filterByPrice(min, max));
    }

    private static void showProductTypes(
            ProductService service) {

        System.out.println("Product types:");

        service.getProductTypes().stream()
            .sorted()
            .forEach(System.out::println);
    }

    private static void showInventoryValue(
            ProductService service) {

        System.out.println(
            "Total inventory value: $" +
            service.calculateInventoryValue());
    }

    private static void calculateDiscountedPrice(
            ProductService service) {

        int id = readInt("Enter product ID: ");

        System.out.println("Choose discount:");
        System.out.println("1. No Discount");
        System.out.println("2. Percentage Discount");
        System.out.println("3. Fixed Discount");

        int choice = readInt("Choose option: ");

        DiscountStrategy strategy;

        switch (choice) {
            case 1 ->
                strategy = new NoDiscountStrategy();

            case 2 -> {
                BigDecimal percentage =
                    readPrice("Enter percentage (0-100): ");

                strategy = new PercentageDiscountStrategy(
                    percentage);
            }

            case 3 -> {
                BigDecimal amount =
                    readPrice("Enter discount amount: ");

                strategy = new FixedDiscountStrategy(amount);
            }

            default -> {
                System.out.println("Invalid discount option.");
                return;
            }
        }

        Product product = service.getProduct(id);

        BigDecimal finalPrice =
            service.calculateDiscountedPrice(id, strategy);

        System.out.println("Product: " + product.getName());
        System.out.println("Base price: $" + product.getPrice());
        System.out.println("Delivery: $" +
            product.getDeliveryCost());
        System.out.println("Final price: $" + finalPrice);
    }

    private static void createUsingBuilder(
            ProductService service) {

        System.out.println("Choose product type:");
        System.out.println("1. Physical");
        System.out.println("2. Digital");

        int type = readInt("Choose option: ");

        if (type != 1 && type != 2) {
            throw new IllegalArgumentException(
                "Invalid product type");
        }

        int id = readInt("Enter product ID: ");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        BigDecimal price = readPrice("Enter price: ");
        int quantity = readInt("Enter quantity: ");

        ProductBuilder builder =
            type == 1
                ? ProductBuilder.physical()
                : ProductBuilder.digital();

        builder.id(id)
               .name(name)
               .price(price)
               .quantity(quantity);

        if (type == 1) {
            System.out.print("Enter weight (kg): ");

            double weight = Double.parseDouble(
                scanner.nextLine().trim());

            builder.weightKg(weight);
        } else {
            System.out.print("Enter download URL: ");
            builder.downloadUrl(scanner.nextLine());
        }

        Product product = builder.build();
        service.addProduct(product);

        System.out.println("Product created using Builder!");
        System.out.println(product);
    }
}
