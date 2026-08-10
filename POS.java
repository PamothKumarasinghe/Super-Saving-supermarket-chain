
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;

class POS implements Serializable {

    // make attributes private - encapsulation (avoid data be direct accessed)
    // why Map? Map is the interface (Map interface as reference type, instantiating
    // HashMap obj)
    // for Abstraction its better to use Map instead of HashMap as the reference
    private Bill currentBill;
    private final ProductRepositary productRepositary;

    public POS(ProductRepositary productRepositary) {
        this.productRepositary = productRepositary;
    }

    public GroceryItem getItemDetails(String itemCode) throws ItemCodeNotFound {
        GroceryItem item = productRepositary.findByCode(itemCode);

        if (item == null) {
            throw new ItemCodeNotFound(
                    "Item code not found: " + itemCode);
        }

        return item;
    }

    public void setCurrentBill(Bill bill) {
        this.currentBill = bill;
    }

    public void addItem(GroceryItem item, int quantity) {
        if (currentBill == null) {
            throw new IllegalStateException(
                    "No active bill.");
        }
        currentBill.addItem(item, quantity);
    }

    public void savePendingBill() {
        String fileName = currentBill.getCustomerName() + "_bill.ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(currentBill);
            System.out.println("Pending bill successfully saved!");

        } catch (IOException e) {
            System.out.println("Error saving pending Bill: " + e.getMessage());
        }
    }

    // @SuppressWarnings("unchecked")
    public void loadPendingBill(String customerName) {
        System.out.println("Enter the customer name to Load the pending bill of his/ her : ");
        String fileName = customerName + "_bill.ser";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("No such pending bill found for this customer, please check the name and try again.");
            return;
        }
        System.out.println("Loading pending bill for customer: " + fileName);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            currentBill = (Bill) ois.readObject(); // this ois.readObject() wil return an
                                                   // object,
                                                   // so i need to cast it to
                                                   // HashMap<GroceryItem, Integer>
                                                   // hence used type casting
            System.out.println("Pending bill successfully loaded!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading pending Bill: " + e.getMessage());
        }

    }

    public void printBill() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Cashier  Name: " + currentBill.getCashierName());
        System.out.println("Branch   Name: " + currentBill.getBranch());
        System.out.println("Customer Name: " + currentBill.getCustomerName());
        System.out.println("-----------------------------------------------------------");
        for (BillItem billItem : currentBill.getItems()) {
            GroceryItem item = billItem.getItem();
            int qty = billItem.getQuantity();
            BigDecimal subTotal = billItem.getSubTotal();
            // BigDecimal discount = billItem.getDiscountAmount();
            BigDecimal netPrice = billItem.getNetPrice();

            System.out.printf("%-15s %-5d $%-7.2f $%-7.2f %-9d%% $%-10.2f\n",
                    item.getItemName(), qty,
                    item.getUnitPrice().setScale(2, RoundingMode.HALF_UP).doubleValue(),
                    subTotal.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                    item.getDiscount(),
                    netPrice.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        System.out.println("-----------------------------------------------------------");
        System.out.printf("Total Discount : $%.2f%n",
                currentBill.getTotalDiscount().setScale(2, RoundingMode.HALF_UP).doubleValue());

        System.out.printf("Total Price    : $%.2f%n",
                currentBill.getTotal().setScale(2, RoundingMode.HALF_UP).doubleValue());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        System.out.println("Printed on     : " + now.format(dtf));
        System.out.println("-----------------------------------------------------------");
    }

}
