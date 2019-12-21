package com.nadya;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.*;

public class ShoppingCartToStringTest {
    private static ShoppingCart normalCart;
    private static String normalCartString;
    private final int headerLinesCount = 2;

    @BeforeClass
    public static void createCarts() {
        normalCart = new ShoppingCart();
        normalCart.addItem("Apple", 0.99, 5, Item.Type.REGULAR);
        normalCart.addItem("Banana", 20.00, 4, Item.Type.SECOND);
        normalCart.addItem("A long piece of toilet paper", 17.20, 1, Item.Type.SALE);
        normalCart.addItem("Nails", 2.00, 500, Item.Type.REGULAR);
        normalCartString = normalCart.toString();
    }

    @Test
    public void testToStringNoItems() {
        assertEquals("No items.", new ShoppingCart().toString());
    }

    @Test
    public void testNumbersPaddedRight() {
        Column[] columns = Arrays.stream(Column.values()).filter(Column::isNumberColumn).toArray(Column[]::new);
        int[] headersLastCharIndices = new int[columns.length];
        int[] headersFirstCharIndices = new int[columns.length];
        for (int i = 0; i < columns.length; i++) {
            headersLastCharIndices[i] = getColumnPaddedRightLastCharIndex(columns[i]);
            headersFirstCharIndices[i] = getColumnPaddedRightFirstCharIndex(columns[i], headersLastCharIndices[i]);
        }

        String[] lines = normalCartString.split("\n");
        for (int i = headerLinesCount; i < lines.length; i++) {
            String line = lines[i];
            if (line.contains("--")) {
                continue;
            }

            for (int j = 0; j < columns.length; j++) {
                if (i != lines.length - 1 || j == columns.length - 1) {
                    String columnValue = line.substring(headersFirstCharIndices[j], headersLastCharIndices[j]);
                    int lastIndexOfSpace = columnValue.lastIndexOf(" ");
                    assertTrue(lastIndexOfSpace < columnValue.length() - 1);
                }
            }
        }
    }

    @Test
    public void testCroppedLongTitles() {
        List items = normalCart.getItems();
        int headerFirstCharIndex = normalCartString.indexOf(Column.TITLE.getHeader());
        int headerLastCharIndex = headerFirstCharIndex + Column.TITLE.getWidth();

        String[] lines = normalCartString.split("\n");
        for (int i = headerLinesCount; i < lines.length - 2; i++) {
            Item item = (Item)items.get(i - headerLinesCount);
            if (item.title.length() > Column.TITLE.getWidth()) {
                String columnValue = lines[i].substring(headerFirstCharIndex, headerLastCharIndex);
                String longTitleEnding = "...";
                assertEquals(columnValue, item.title.substring(0, Column.TITLE.getWidth() - longTitleEnding.length()) + longTitleEnding);
            }
        }
    }

    @Test
    public void testDashInsteadZeroDiscount() {
        List items = normalCart.getItems();
        int headerLastCharIndex = getColumnPaddedRightLastCharIndex(Column.DISCOUNT);
        int headerFirstCharIndex = getColumnPaddedRightFirstCharIndex(Column.DISCOUNT, headerLastCharIndex);

        String[] lines = normalCartString.split("\n");
        for (int i = headerLinesCount; i < lines.length - 2; i++) {
            Item item = (Item)items.get(i - headerLinesCount);
            if (ShoppingCart.calculateDiscount(item) == 0) {
                String columnValue = lines[i].substring(headerFirstCharIndex, headerLastCharIndex);
                assertThat(columnValue, endsWith("-"));
            }
        }
    }

    private int getColumnPaddedRightFirstCharIndex(Column column, int headerLastCharIndex) {
        return headerLastCharIndex - column.getWidth()  + 1;
    }

    private int getColumnPaddedRightLastCharIndex(Column column) {
        return normalCartString.indexOf(column.getHeader()) + column.getHeader().length();
    }

    enum Column{
        ID("#", 2, true),
        TITLE("Item", 20, false),
        PRICE("Price", 7, true),
        QUANTITY("Quan.", 5, true),
        DISCOUNT("Discount", 8, true),
        TOTAL("Total", 10, true);

        private String header;
        private int width;
        private boolean isNumberColumn;

        Column(String header, int width, boolean isNumberColumn){
            this.header = header;
            this.width = width;
            this.isNumberColumn = isNumberColumn;
        }

        public int getWidth() {
            return width;
        }

        public String getHeader(){
            return header;
        }

        public boolean isNumberColumn() {
            return isNumberColumn;
        }
    }
}