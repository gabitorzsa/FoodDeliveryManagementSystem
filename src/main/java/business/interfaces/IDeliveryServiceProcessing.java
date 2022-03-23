package business.interfaces;

import model.CompositeProduct;
import model.MenuItem;
import model.Order;

import java.util.Optional;

public interface IDeliveryServiceProcessing {
    // for administrator

    /**
     * imports menuItems from .csv file
     *
     * @pre getSize() == 0
     * @post getSize() != 0
     */
    void importProducts();

    /**
     * finds the product from MenuItem based on its title
     *
     * @param title
     * @return
     * @pre title != null
     * @post @return != null
     */
    Optional<MenuItem> findProduct(String title);

    /**
     * adds a new menuItem to menu
     *
     * @param baseProduct
     * @pre menuItem != null
     * @invariant isWellFormed()
     */
    void addProduct(MenuItem baseProduct);

    /**
     * delete the product with name title
     *
     * @param menuItem
     * @pre getSize() > 0 && title != null
     * @post getSize() == getSize()@pre - 1
     */
    void deleteProduct(MenuItem menuItem);

    /**
     * create composed products
     *
     * @param compositeProduct
     * @pre compositeProduct != null
     * @post getSize() == getSize()@pre - 1
     * @invariant isWellFormed()
     */
    void addComposedProduct(CompositeProduct compositeProduct);


// -----------------------------------------------------------

    // for client
    /**
     * create composed products
     *
     * @param order
     * @pre order != null
     * @post orders != null
     */
    void addOrder(Order order);

    /**
     * generate a bill for an order
     * @param order
     */
    void generateBill(Order order);

}
