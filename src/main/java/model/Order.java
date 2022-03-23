package model;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderID;
    private int clientID;
    private Date orderDate;
    private List<MenuItem> items;
    private int price;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order[" +
                "orderID = " + orderID +
                ", clientID = " + clientID +
                ", orderDate = " + orderDate +
                ", items = " + items +
                ", price = " + price +
                ']';
    }
}
