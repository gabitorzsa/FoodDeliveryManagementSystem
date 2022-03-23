package model;

import java.util.List;

public class CompositeProduct extends MenuItem {

    private String title;
    private List<MenuItem> compositeProductItem;

    public CompositeProduct(List<MenuItem> compositeProductItem) {
        this.compositeProductItem = compositeProductItem;
    }

    public int computePrice() {
        return compositeProductItem.stream().mapToInt(MenuItem::computePrice).sum();
    }

    @Override
    public String getProductTitle() {
        return this.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuItem> getCompositeProductItem() {
        return compositeProductItem;
    }

    public void setCompositeProductItem(List<MenuItem> compositeProductItem) {
        this.compositeProductItem = compositeProductItem;
    }
}
