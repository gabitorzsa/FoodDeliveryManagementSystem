package business;

import business.interfaces.IDeliveryServiceProcessing;
import data.ReportGenerator;
import model.BaseProduct;
import model.CompositeProduct;
import model.MenuItem;
import model.Order;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable{

    private Observer observer;
    private Map<Order, List<MenuItem>> orders = new HashMap<>();
    private ArrayList<MenuItem> menuItemsList = new ArrayList<>();

    private static int lastOrderId;

    public DeliveryService() {
        lastOrderId = 0;
    }

    public boolean isWellFormed() {
        if (menuItemsList != null)
            return true;
        return false;
    }

    @Override
    public void importProducts() {
        assert isWellFormed();

        List<MenuItem> products = importFromFile();
        this.menuItemsList.addAll(products);
    }

    @Override
    public Optional<MenuItem> findProduct(String title) {
        return menuItemsList.stream().filter(menuItem -> menuItem.getProductTitle().equalsIgnoreCase(title)).findFirst();
    }

    @Override
    public void addProduct(MenuItem menuItem) {
        isWellFormed();
        assert menuItem != null;

        BaseProduct baseProduct = (BaseProduct) menuItem;

        Optional<MenuItem> productOptional = this.findProduct(baseProduct.getProductTitle());
        if (productOptional.isPresent()) {
            MenuItem product = productOptional.get();
            menuItemsList.remove(product);
        }

        menuItemsList.add(baseProduct);
    }

    @Override
    public void deleteProduct(MenuItem menuItem) {
        assert menuItem != null;
        BaseProduct baseProduct = (BaseProduct) menuItem;
        Optional<MenuItem> productOptional = this.findProduct(baseProduct.getProductTitle());
        if (productOptional.isPresent()) {
            MenuItem product = productOptional.get();
            menuItemsList.remove(product);
        }
    }

    @Override
    public void addComposedProduct(CompositeProduct compositeProduct) { }

    @Override
    public void addOrder(Order order) {
        int orderPrice = order.getItems().stream()
                .map(item -> ((BaseProduct) item).getPrice())
                .mapToInt(Integer::intValue)
                .sum();
        order.setPrice(orderPrice);
        lastOrderId += 1;
        order.setOrderID(lastOrderId);
        order.setOrderDate(Date.from(Instant.now()));
        orders.put(order, order.getItems());

        System.out.println("add order:");
        System.out.println(order.toString());
    }

    /**
     * @param order
     *
     */
    @Override
    public void generateBill(Order order) {
        if(order != null) {
            ReportGenerator.generateBill(order);
            orders.remove(order);
        }

    }

    public void notifyObserver(Order order) {
        observer.update(this, order);
    }

    private List<MenuItem> importFromFile() {
        List<MenuItem> products = new ArrayList<>();
        try {
            File inputFile = new File("products.csv");
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            products = bufferedReader
                    .lines()
                    .skip(1)
                    .map(mapToItem)
                    .filter(distinctByKey(MenuItem::getProductTitle))
                    .collect(Collectors.toList());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }


    private Function<String, MenuItem> mapToItem = (line) -> {
        String[] p = line.split(",");

        String name = p[0];
        double rating = Double.parseDouble(p[1]);
        int calories = Integer.parseInt(p[2]);
        int protein = Integer.parseInt(p[3]);
        int fat = Integer.parseInt(p[4]);
        int sodium = Integer.parseInt(p[5]);
        int price = Integer.parseInt(p[6]);

        BaseProduct product = new BaseProduct();
        product.setTitle(name);
        product.setRating(rating);
        product.setCalories(calories);
        product.setProtein(protein);
        product.setFat(fat);
        product.setSodium(sodium);
        product.setPrice(price);

        return product;
    };

    public List<Order> getOrders() {
        return new ArrayList<>(orders.keySet());
    }

    public ArrayList<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
