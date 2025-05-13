
import java.io.*;
import java.util.*;

class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound() {}
    public ItemCodeNotFound(String message) {
        super(message);
    }
}
class GroceryItem {
    private String itemCode;
    private String itemName;
    private double itemPrice;
    private String manufacturer;
    private String pDate;
    private String eDate;
    private int discount;

    public GroceryItem(String itemCode, String itemName, double itemPrice,String manufacturer, String pDate, String eDate, int discount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.manufacturer = manufacturer;
        this.pDate = pDate;
        this.eDate = eDate;
        this.discount = discount;
    }
    public double getItemPrice(int itemQuantity) {
        return itemPrice * itemQuantity; //Didnt add the discount yet, plz be kind to add it
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
            File file = new File("grocery_item.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); //read the first line in the file
                
            while (line != null) {
                String[] arr = line.split(",");
                String a = arr[0];
                String b = arr[1];
                double c = Double.parseDouble(arr[2]);
                String d = arr[3];
                String e = arr[4];
                String f = arr[5];

                cart.put(a, new GroceryItem(a, b, c, d, e, f));

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
        System.out.print("Enter branch name: ");
        branch = sc.nextLine();
        System.out.print("Enter customer name (or press Enter to skip): ");
        customerName = sc.nextLine();
        if (customerName.trim().isEmpty()) customerName = "Guest";

        GroceryItem item = null;
        // while (true) {
        //     System.out.println(("Enter the item code (or 'exit' to finish):"));
        //     String itemCode = sc.nextLine().trim();
        //     if (itemCode.equalsIgnoreCase("exit")) break;
        //     item = getItemDetails(itemCode);
        //     if (item != null) {
        //         System.out.println(("Enter the quantity: "));
        //         int itemQuantity = sc.nextInt();
        //         sc.nextLine();
        //     }

        // }
        while (true) { 
            System.out.println("\nPress 1 to Add item \nPress 2 to Print bill ");
            int choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the item code:");
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
        System.out.println("----------------------------------------");
        System.out.println("Cashier Name: " + cashierName);
        System.out.println("Branch Name:" + branch);
        System.out.println("Customer Name: " + customerName);
        System.out.println("----------------------------------------");

        double total = 0;
        for (Map.Entry<GroceryItem, Integer> en : currentBill.entrySet()) {
            GroceryItem item = en.getKey();
            Integer qty = en.getValue();
            total = item.getItemPrice(qty);
            
        }
    }
    
}


public class SSS {
    public static void main(String[] args) {

    }
}
