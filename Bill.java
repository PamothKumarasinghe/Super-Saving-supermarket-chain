import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bill implements Serializable {
    private final String cashierName;
    private final String branch;
    private final String customerName;

    private final List<BillItem> items = new ArrayList<>();

    public Bill(String cashierName, String branch, String customerName) {
        this.cashierName = cashierName;
        this.branch = branch;
        this.customerName = customerName;
    }

    public void addItem(GroceryItem item, int quantity) {
        items.add(new BillItem(item, quantity));
    }

    public List<BillItem> getItems() {
        return items;
    }

    public String getCashierName() {
        return cashierName;
    }

    public String getBranch() {
        return branch;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getSubTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (BillItem item : items) {
            total = total.add(item.getSubTotal());
        }

        return total;
    }

    public BigDecimal getTotalDiscount() {
        BigDecimal discount = BigDecimal.ZERO;

        for (BillItem item : items) {
            discount = discount.add(item.getDiscountAmount());
        }

        return discount;
    }

    public BigDecimal getTotal() {
        return getSubTotal().subtract(getTotalDiscount());
    }
}
