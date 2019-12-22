package com.nadya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import static com.nadya.ShoppingCart2.Item.ItemType;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShoppingCart2CalculateDiscountTest {
    private ItemType itemType;
    private int quantity;
    private int expectedDiscount;

    public ShoppingCart2CalculateDiscountTest(ItemType itemType, int quantity, int expectedDiscount) {
        this.itemType = itemType;
        this.quantity = quantity;
        this.expectedDiscount = expectedDiscount;
    }

    @Parameterized.Parameters
    public static Collection a() {
        return Arrays.asList(new Object[][]{
                { ItemType.NEW, 5, 0 },
                { ItemType.SECOND_FREE, 1, 0 },
                { ItemType.SECOND_FREE, 4, 50 },
                { ItemType.SALE, 5, 70 },
                { ItemType.REGULAR, 5, 0 },
                { ItemType.REGULAR, 20, 2 },
                { ItemType.REGULAR, 500, 50 },
                { ItemType.REGULAR, 1000, 80 },
        });
    }

    @Test
    public void testCalculateDiscount() {
        assertEquals(expectedDiscount, ShoppingCart2.calculateDiscount(itemType, quantity));
    }
}
