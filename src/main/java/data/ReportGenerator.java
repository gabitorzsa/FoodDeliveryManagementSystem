package data;

import model.MenuItem;
import model.Order;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {

    public static void generateBill(Order order) {
        try {
            File bill = new File("bill" + order.getOrderID() + ".txt");
            System.out.println(bill.toString());
            bill.createNewFile();

            FileWriter fileWriter = new FileWriter(bill.getAbsoluteFile(), true);
            fileWriter.write("Order " + order.getOrderID() + "\n");
            fileWriter.write("Date/time: " + order.getOrderDate().toString() + "\n");
            fileWriter.write("Products: \n" );
            order.getItems().stream().forEach(item -> {
                try {
                    fileWriter.write(item + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.write("Total price: " + order.getPrice());
            fileWriter.write("\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateIntervalReport(List<MenuItem> orders) throws IOException {
        File bill = new File("Interval hours reports.txt");
        bill.createNewFile();
        FileWriter fileWriter = new FileWriter(bill.getAbsoluteFile(), true);
        for(MenuItem item : orders) {
            fileWriter.write(item.getProductTitle());
        }
    }

    public static void generateProductMoreThanNTimes(List<Order> orders, int minTimes) throws IOException {

        Map<String, Integer> occuranceMap = new HashMap<String, Integer>();
        orders.forEach(order ->{
            for (Order o : orders) {
                Integer j = occuranceMap.get(o);
                for (MenuItem mi : order.getItems()) {
                    occuranceMap.put(mi.getProductTitle(), (j == null) ? 1 : j + 1);
                }
            }
        });

        List<String> result = occuranceMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= minTimes)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

            File bill = new File("reportMoreThan" + minTimes +  ".txt");
            System.out.println(bill.toString());
            bill.createNewFile();

            FileWriter fileWriter = new FileWriter(bill.getAbsoluteFile(), true);

            result.forEach(productTitle ->{
                try {
                    fileWriter.write("products" + productTitle);
                    fileWriter.write("\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            fileWriter.close();
    }

    public static void generateClientWithMoreThan(List<User> users) {}

    public static void generateProductsOrderedOnSpecifiedDay(Map<MenuItem, Integer> list) throws IOException {
        File bill = new File("reportProductsOrdered.txt");
        bill.createNewFile();

        FileWriter fileWriter = new FileWriter(bill.getAbsoluteFile(), true);
        List<MenuItem> items = new ArrayList<>(list.keySet());
        List<Integer> number = new ArrayList<>(list.values());

        for(int i = 0; i < list.size(); i++) {
            fileWriter.write("%s " + items.get(i) + number.get(i).toString());
        }
        fileWriter.close();
    }
}
