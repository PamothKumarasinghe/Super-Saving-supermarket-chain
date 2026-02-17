import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Both the classes GoceryItem and POS are Serializable-because i am going to add the pending bill feature
// So i need to save the currentBill in a file, which is a HashMap that initializes in the POS class and contains GroceryItems and integers - so both classes are ser.


class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound() {}
    public ItemCodeNotFound(String message) {
        super(message);
    }
}
class GroceryItem implements Serializable {
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
    public String getItemCode() {
        return itemCode;
    }
    public String getItemName() {
        return itemName;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getProductionDate() {
        return pDate;
    }
    public String getExpirationDate() {
        return eDate;
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
class POS implements Serializable{
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
                    
                    System.out.printf("%-10s %-15s $%-8.2f %-15s %-12s %-12s %-5d%%\n", a, b, c, d, e, f, g);
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
                throw new ItemCodeNotFound("erororororororor");  ///sdjck
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
            System.out.println("\nPress 1 to Add item \nPress 2 to Print bill \nPress 3 to Save pending bill \nPress 4 to Load the pending bill \nPress 5 to exit \n");
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
                case 3 -> {
                    savePendingBill();
                }
                case 4 -> {
                    loadPendingBill();
                }
                case 5 -> {
                    System.out.println("Exiting....");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }

    }
    public void savePendingBill() {
        String fileName = customerName+"_bill.ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(currentBill);
            System.out.println("Pending bill successfully saved!");

        }
        catch (IOException e) {
            System.out.println("Error saving pending Bill: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void loadPendingBill() {
        System.out.println("Enter the customer name to Load the pending bill of his/ her : ");
        String fileName = sc.nextLine().trim() + "_bill.ser";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("No such pending bill found for this customer, please check the name and try again.");
            return;
        }
        System.out.println("Loading pending bill for customer: " + fileName);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            currentBill = (HashMap<GroceryItem, Integer>) ois.readObject(); //this ois.readObject() wil return an object,
                                                                            //so i need to cast it to HashMap<GroceryItem, Integer> 
                                                                            //hence used type casting
            System.out.println("Pending bill successfully loaded!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading pending Bill: " + e.getMessage());
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
        System.out.println("\nWelcome to the Grocery Store!");
        System.out.println("===========================================================");
        pos.generateBill();
        System.out.println("\nThank you for shopping with us!");
        System.out.println("===========================================================");
        pos.sc.close();

        System.exit(0);
    }
}