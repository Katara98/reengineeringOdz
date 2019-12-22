package com.nadya;

/**
 * item info
 */
public class Item2 {
    public enum ItemType {NEW, REGULAR, SECOND_FREE, SALE}
    private String title;
    private double price;
    private int quantity;
    private ItemType type;

    public Item2(String title, double price, int quantity, ItemType type) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.length() == 0 || title.length() > 32) {
            throw new IllegalArgumentException("Illegal title");
        }

        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0.01) {
            throw new IllegalArgumentException("Illegal price");
        }

        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Illegal quantity");
        }

        this.quantity = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
