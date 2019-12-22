package com.nadya;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart2Formatter {
    private static final NumberFormat MONEY;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        MONEY = new DecimalFormat("$#.00", symbols);
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
    public static String formatTicket(ShoppingCart2 shoppingCart) {
        if (shoppingCart.getItems().isEmpty()) {
            return "No items.";
        }

        String[] header = {"#", "Item", "Price", "Quan.", "Discount", "Total"};
        int[] align = new int[]{1, -1, 1, 1, 1, 1};
        List<String[]> lines = getLines(shoppingCart.getItems());
        String[] footer = {String.valueOf(shoppingCart.getItems().size()), "", "", "", "", MONEY.format(shoppingCart.getTotal())};

        int[] width = getColumnsWidths(header, lines, footer);
        int lineLength = getLineLength(width);
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

    private static List<String[]> getLines(Iterable<Item2> items) {
        List<String[]> lines = new ArrayList<>();
        int index = 0;
        for (Item2 item : items) {
            int discount = item.calculateDiscount();
            double itemTotal = item.getItemTotal();
            lines.add(new String[]{
                    String.valueOf(++index),
                    item.getTitle(),
                    MONEY.format(item.getPrice()),
                    String.valueOf(item.getQuantity()),
                    (discount == 0) ? "-" : (discount + "%"),
                    MONEY.format(itemTotal)
            });
        }

        return lines;
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

    private static int getLineLength(int[] width) {
        int lineLength = width.length - 1;
        for (int w : width) {
            lineLength += w;
        }
        return lineLength;
    }

    private static int[] getColumnsWidths(String[] header, List<String[]> lines, String[] footer) {
        int[] width = new int[header.length];
        for (String[] line : lines) {
            for (int i = 0; i < line.length; i++) {
                width[i] = Math.max(width[i], line[i].length());
            }
        }

        for (int i = 0; i < header.length; i++) {
            width[i] = Math.max(width[i], header[i].length());
        }

        for (int i = 0; i < footer.length; i++) {
            width[i] = Math.max(width[i], footer[i].length());
        }

        return width;
    }

    private static void buildSeparator(StringBuilder stringBuilder, int lineLength) {
        for (int i = 0; i < lineLength; i++) {
            stringBuilder.append("-");
        }
    }

    private static void buildLine(StringBuilder stringBuilder, String[] columns, int[] align, int[] width) {
        for (int i = 0; i < columns.length; i++) {
            appendFormatted(stringBuilder, columns[i], align[i], width[i]);
        }
    }
}
