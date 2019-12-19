package com.nadya;

public class Item {
    public enum Type { SECOND, REGULAR, SALE, DISCOUNT };
    public String title;
    public double price;
    public int quantity;
    public Type type;

    public Item(String title, double price, int quantity, Item.Type type) {
        this.title = title;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }
}