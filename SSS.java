import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound() {}
    public ItemCodeNotFound(String message) {
        super(message);
    }
}
class GroceryItem {
    private final String itemCode;
    private final String itemName;
    private final double itemPrice;
    private final String manufacturer;
    private final String pDate;
    private final String eDate;
    private final int discount;

    public GroceryItem(String itemCode, String itemName, double itemPrice,String manufacturer, String pDate, String eDate, int discount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.manufacturer = manufacturer;
        this.pDate = pDate;
        this.eDate = eDate;
        this.discount = discount;
    }
    public String getItemName() {
        return itemName;
    }
    public double getUnitPrice() {
        return itemPrice;
    }
    public int getDiscount() {
        return discount;
    }
    /* return the price before discount added to an ITEM */
    public double getItemPrice(int itemQuantity) {
        return itemPrice * itemQuantity; //Didnt add the discount yet, plz be kind to add it
    }
    /* returns the price after adding the discount for an ITEM */
    public double getItemNetPrice(double total) {
        return total - (total * discount / 100);
    }
    
}
class POS {
    static final HashMap<String, GroceryItem> cart = new HashMap<>();
    HashMap<GroceryItem, Integer> currentBill = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    String cashierName;
    String branch;
    String customerName;

    
    static {
        try {
            File file = new File("grocery_items.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // try-with-resources ensures br is closed
                String line = br.readLine(); //read the first line in the file
                
                while (line != null) {
                    String[] arr = line.split(",");
                    String a = arr[0];
                    String b = arr[1];
                    double c = Double.parseDouble(arr[2]);
                    String d = arr[3];
                    String e = arr[4];
                    String f = arr[5];
                    int g = Integer.parseInt(arr[6]);

                    cart.put(a, new GroceryItem(a, b, c, d, e, f, g));
                    line = br.readLine();
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        
    }
    public GroceryItem getItemDetails(String itemCode) {
        GroceryItem item = null;
        // System.out.println("Enter the item code:");
        
        try {
            // String itemCode = sc.nextLine().trim();
            if (!cart.containsKey(itemCode)) {
                throw new ItemCodeNotFound("Item code not found");
            }
            item = cart.get(itemCode);
        } catch (ItemCodeNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        return item;
    }
    public void generateBill() {
        System.out.print("Enter cashier name: ");
        cashierName = sc.nextLine();
        System.out.print("Enter branch  name: ");
        branch = sc.nextLine();
        System.out.print("Enter customer name (or press Enter to skip): ");
        customerName = sc.nextLine();
        if (customerName.trim().isEmpty()) customerName = "Guest";

        GroceryItem item;
        while (true) { 
            System.out.println("\nPress 1 to Add item \nPress 2 to Print bill ");
            int choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 1 -> {
                    System.out.println("\nEnter the item code:");
                    String itemCode = sc.nextLine().trim();
                    item = getItemDetails(itemCode);
                    if (item != null) {
                        System.out.println("Enter the quantity: ");
                        int itemQuantity = Integer.parseInt(sc.nextLine().trim());
                        currentBill.put(item, itemQuantity);
                        System.out.println("Item added to the bill.");
                    } 
                }
                case 2 -> {
                    printBill();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }

    }
    public void printBill() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Cashier  Name: " + cashierName);
        System.out.println("Branch   Name: " + branch);
        System.out.println("Customer Name: " + customerName);
        System.out.println("-----------------------------------------------------------");

        double total = 0, totalDiscount = 0;
        for (Map.Entry<GroceryItem, Integer> en : currentBill.entrySet()) {
            GroceryItem item = en.getKey();
            Integer qty = en.getValue();

            double itemTotal = item.getItemPrice(qty);
            double itemNetPrice = item.getItemNetPrice(itemTotal);
            int itemDiscount = item.getDiscount();
            total += itemNetPrice; totalDiscount += itemDiscount;

            System.out.printf("%-15s %-5d $%-7.2f $%-7.2f %-9d%% $%-10.2f\n", item.getItemName(), qty, item.getUnitPrice(), itemTotal, itemDiscount, itemNetPrice);
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Total Discount : $" + String.format("%.2f",totalDiscount));
        System.out.println("Total Price    : $" + String.format("%.2f",total));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDate = now.format(dtf);
        System.out.println("Printed on     : " + formattedDate);
        System.out.println("-----------------------------------------------------------");
    }
    
}


public class SSS {
    public static void main(String[] args) {
        POS pos = new POS();
        System.out.println("Welcome to the Grocery Store!");
        System.out.println("===========================================================");
        pos.generateBill();
        System.out.println("Thank you for shopping with us!");
        System.out.println("===========================================================");
        pos.sc.close();

        System.exit(0);
    }
}
