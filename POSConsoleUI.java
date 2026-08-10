import java.util.Scanner;

public class POSConsoleUI {
    private final Scanner sc;
    private final POS pos;

    public POSConsoleUI(POS pos) {
        this.pos = pos;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\nWelcome to the Grocery Store!");
        System.out.println("===========================================================");

        System.out.print("Enter cashier name: ");
        String cashierName = sc.nextLine();

        System.out.print("Enter branch name: ");
        String branch = sc.nextLine();

        boolean running = true;
        while (running) {

            System.out.print("Enter customer name (or press Enter to skip): ");
            String customerName = sc.nextLine();

            if (customerName.trim().isEmpty()) {
                customerName = "Guest";
            }

            // Create a new Bill
            Bill bill = new Bill(cashierName, branch, customerName);

            // giving the bill to pos
            pos.setCurrentBill(bill); // implement this function in POS

            boolean customerDone = false;
            // main menu
            while (!customerDone) {
                System.out.println(
                        "\nPress 1 to Add item \nPress 2 to Print bill \nPress 3 to Save pending bill \nPress 4 to Load the pending bill \nPress 5 to exit \n");

                try {
                    int choice = Integer.parseInt(sc.nextLine().trim());

                    switch (choice) {
                        case 1 -> addItem();

                        case 2 -> {
                            pos.printBill();
                            customerDone = true;
                            break;
                        }
                        case 3 -> {
                            pos.savePendingBill();
                        }
                        case 4 -> loadBill();
                        case 5 -> {
                            System.out.println("Exiting....");
                            running = false;
                            customerDone = true;
                            break;
                        }
                        default -> System.out.println("Invalid choice. Please try again.");

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number");
                }
            }
        }
    }
    
    private void addItem() {
        System.out.println("\nEnter the item code:");

        String itemCode = sc.nextLine().trim();

        try {
            GroceryItem item = pos.getItemDetails(itemCode);
            System.out.println("Item: " + item.getItemName());
            System.out.println("Price: " + item.getUnitPrice());
            System.out.println("Discount: " + item.getDiscount() + "%");
            System.out.println("\n");
            System.out.println("Enter the quantity: ");
            int quantity = Integer.parseInt(sc.nextLine().trim());

            if (quantity <= 0) {
                System.out.println("Quantiyt must be greater than zero.");
            }

            pos.addItem(item, quantity);

            System.out.println("Item addedd to the bill.");

        } catch (ItemCodeNotFound e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid quantity");
        }
    }
    
    public void loadBill() {
        System.out.println("Enter the customer name: ");

        String customerName = sc.nextLine().trim();
        pos.loadPendingBill(customerName);
    }
}
