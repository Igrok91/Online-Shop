package ru.innopolis.uni.model.service;

import ru.innopolis.uni.model.entityDao.Product;

import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public class ShoppingCart {
    private static HashMap<Integer, ShoppingCartItem> itemsMap = null;
    private int numberOfItems = 0;


    public ShoppingCart() {
        itemsMap = new HashMap<>();
    }

    // Adds items to the shopping cart
    public synchronized void add(int productID, Product p) {
        ShoppingCartItem newItem = new ShoppingCartItem(p);
        itemsMap.put(productID, newItem);
    }

    // Update items in the shopping cart
    public synchronized void updateQuantity(int productID, int quantity,
                                            Product p) {
        if (itemsMap.containsKey(productID)) {
            ShoppingCartItem scItem = (ShoppingCartItem) itemsMap
                    .get(productID);
            scItem.setQuantity(quantity);
        }
    }

    // Remove items from the shopping cart
    public synchronized  void remove(Integer productID) {
        if (itemsMap.containsKey(productID)) {
            ShoppingCartItem scItem = itemsMap.get(productID);
            if (scItem.getQuantity() <= 1) {
                itemsMap.remove(productID);
            }
            numberOfItems--;
        }
    }

    // Clear all items in the shopping cart
    public synchronized void clear() {
        itemsMap.clear();
        numberOfItems = 0;
    }

    // Get All the Items in the Shopping Cart
    public synchronized List<ShoppingCartItem> getItems() {
        List<ShoppingCartItem> listOfItems = new ArrayList<>();
        listOfItems.addAll(this.itemsMap.values());
        return listOfItems;
    }

    // Get Number of Items in the Shopping Cart
    public synchronized int getNumberOfItems() {
        numberOfItems = 0;
        Iterator<ShoppingCartItem> iterator = getItems().iterator();

        while (iterator.hasNext()) {
            ShoppingCartItem items = iterator.next();
            numberOfItems += items.getQuantity();
        }

        return numberOfItems;
    }

    // Get the Total Value of the Shopping Cart
    public synchronized double getTotal() {

        double amount = 0.0;
        Iterator<ShoppingCartItem> iterator = getItems()
                .iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem anotherSCItem = iterator.next();
            Product product = anotherSCItem.getProduct();
            amount += (anotherSCItem.getQuantity() * product.getProductPrice());
        }

        return amount;
    }

    // Checks whether a particular product
    // is already present in the cart
    public static Boolean checkProductInCart(PageContext pageContext) {
        int productID = (int) pageContext.findAttribute("productID");
        if (itemsMap == null)
            itemsMap = new HashMap<>();
        if (itemsMap.containsKey(productID)) {

            return true;
        }
        return false;
    }
}
