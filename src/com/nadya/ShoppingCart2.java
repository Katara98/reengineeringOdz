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
    private static final NumberFormat MONEY;
    /**
     * Container for added items
     */
    private List<Item2> items = new ArrayList<Item2>();


    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        MONEY = new DecimalFormat("$#.00", symbols);
    }

    /**
     * Tests all class methods.
     */
    public static void main(String[] args) {
        // TODO: add tests here
        ShoppingCart2 cart = new ShoppingCart2();
        cart.addItem("Apple", 0.99, 5, Item2.ItemType.NEW);
        cart.addItem("Banana", 20.00, 4, Item2.ItemType.SECOND_FREE);
        cart.addItem("A long piece of toilet paper", 17.20, 1, Item2.ItemType.SALE);
        cart.addItem("Nails", 2.00, 500, Item2.ItemType.REGULAR);
        System.out.println(cart.formatTicket());
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

    /**
     * Formats shopping price.
     *
     * @return string as lines, separated with \n,
     * first line: # Item Price Quan. Discount Total
     * second line: ---------------------------------------------------------
     * next lines: NN Title $PP.PP Q DD% $TT.TT
     * 1 Some title $.30 2 - $.60
     * 2 Some very long $100.00 1 50% $50.00
     * ...
     * 31 Item 42 $999.00 1000 - $999000.00
     * end line: ---------------------------------------------------------
     * last line: 31 $999050.60
     * <p>
     * if no items in cart returns "No items." string.
     */
    public String formatTicket() {
        if (items.size() == 0) {
            return "No items.";
        }
        List<String[]> lines = new ArrayList<String[]>();
        String[] header = {"#", "Item", "Price", "Quan.", "Discount", "Total"};
        int[] align = new int[]{1, -1, 1, 1, 1, 1};
        // formatting each line
        double total = 0.00;
        int index = 0;
        for (Item2 item : items) {
            int discount = item.calculateDiscount();
            double itemTotal = item.getPrice() * item.getQuantity() * (100.00 - discount) / 100.00;
            lines.add(new String[]{
                    String.valueOf(++index),
                    item.getTitle(),
                    MONEY.format(item.getPrice()),
                    String.valueOf(item.getQuantity()),
                    (discount == 0) ? "-" : (String.valueOf(discount) + "%"),
                    MONEY.format(itemTotal)
            });
            total += itemTotal;
        }
        String[] footer = {String.valueOf(index), "", "", "", "",
                MONEY.format(total)};
        // formatting table
        // column max length
        int[] width = new int[]{0, 0, 0, 0, 0, 0};
        for (String[] line : lines) {
            for (int i = 0; i < line.length; i++) {
                width[i] = (int) Math.max(width[i], line[i].length());
            }
        }
        for (int i = 0; i < header.length; i++) {
            width[i] = (int) Math.max(width[i], header[i].length());
        }
        for (int i = 0; i < footer.length; i++) {
            width[i] = (int) Math.max(width[i], footer[i].length());// line length
        }
        int lineLength = width.length - 1;
        for (int w : width) {
            lineLength += w;
        }
        StringBuilder sb = new StringBuilder();
        buildLine(sb, header, align, width);
        sb.append("\n");
        buildSeparator(sb, lineLength);
        sb.append("\n");
        for (String[] line : lines) {
            buildLine(sb, line, align, width);
            sb.append("\n");
        }
        buildSeparator(sb, lineLength);
        sb.append("\n");
        buildLine(sb, footer, align, width);
        return sb.toString();
    }

    private void buildSeparator(StringBuilder stringBuilder, int lineLength) {
        for (int i = 0; i < lineLength; i++) {
            stringBuilder.append("-");
        }
    }

    private void buildLine(StringBuilder stringBuilder, String[] columns, int[] align, int[] width) {
        for (int i = 0; i < columns.length; i++) {
            appendFormatted(stringBuilder, columns[i], align[i], width[i]);
        }
    }

    /**
     * Appends to sb formatted value.
     * Trims string if its length > width.
     *
     * @param align -1 for align left, 0 for center and +1 for align right.
     */
    public static void appendFormatted(StringBuilder sb, String value, int align, int width) {
        if (value.length() > width) {
            value = value.substring(0, width);
        }
        int before = (align == 0)
                ? (width - value.length()) / 2
                : (align == -1) ? 0 : width - value.length();
        int after = width - value.length() - before;
        while (before-- > 0) {
            sb.append(" ");
        }
        sb.append(value);
        while (after-- > 0) {
            sb.append(" ");
        }
        sb.append(" ");
    }
}
