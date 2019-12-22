package com.nadya;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Containing items and calculating price.
 */
public class ShoppingCart2 {

    /**
     * Container for added items
     */
    private List<Item2> items = new ArrayList<Item2>();

    /**
     * Tests all class methods.
     */
    public static void main(String[] args) {
        ShoppingCart2 cart = new ShoppingCart2();
        cart.addItem("Apple", 0.99, 5, Item2.ItemType.NEW);
        cart.addItem("Banana", 20.00, 4, Item2.ItemType.SECOND_FREE);
        cart.addItem("A long piece of toilet paper", 17.20, 1, Item2.ItemType.SALE);
        cart.addItem("Nails", 2.00, 500, Item2.ItemType.REGULAR);
        System.out.println(ShoppingCart2Formatter.formatTicket(cart));
    }

    public List<Item2> getItems() {
        return items;
    }

    /**
     * Adds new item.
     *
     * @param title    item title 1 to 32 symbols
     * @param price    item ptice in USD, > 0
     * @param quantity item quantity, from 1
     * @param type     item type
     * @throws IllegalArgumentException if some value is wrong
     */
    public void addItem(String title, double price, int quantity, Item2.ItemType type) {
        items.add(new Item2(title, price, quantity, type));
    }

    public double getTotal() {
        double total = 0.00;
        for (Item2 item : items) {
            total += item.getItemTotal();
        }

        return total;
    }
}
