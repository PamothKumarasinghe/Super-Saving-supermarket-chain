import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FileProductRepositary implements ProductRepositary {
    private final String fileName;
    private final Map<String, GroceryItem> products;

    public FileProductRepositary(String fileName) {
        this.fileName = fileName;
        this.products = new HashMap<>();
        loadProducts();
    }

    @Override
    public Map<String, GroceryItem> loadProducts() {
        try {
            File file = new File(fileName);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();

                while (line != null) {
                    String[] arr = line.split(",");
                    if (arr.length >= 7) {
                        String itemCode = arr[0].trim();
                        String b = arr[1].trim();
                        BigDecimal c = new BigDecimal(arr[2].trim());
                        String d = arr[3].trim();
                        String e = arr[4].trim();
                        String f = arr[5].trim();
                        int g = Integer.parseInt(arr[6].trim());

                        products.put(itemCode, new GroceryItem(itemCode, b, c, d, e, f, g));
                        System.out.printf("%-10s %-15s $%-8.2f %-15s %-12s %-12s %-5d%%\n", itemCode, b,
                                c.setScale(2, RoundingMode.HALF_UP).doubleValue(), d, e, f, g);
                    }
                    line = br.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }

    @Override
    public GroceryItem findByCode(String itemCode) {
        return products.get(itemCode);
    }
}
