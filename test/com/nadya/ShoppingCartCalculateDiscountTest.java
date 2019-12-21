package com.nadya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShoppingCartCalculateDiscountTest {
    private Item item;
    private int expectedDiscount;

    public ShoppingCartCalculateDiscountTest(Item item, int expectedDiscount) {
        this.item = item;
        this.expectedDiscount = expectedDiscount;
    }

    @Parameterized.Parameters
    public static Collection a() {
        return Arrays.asList(new Object[][]{
                { new Item("Apple", 0.99, 5, Item.Type.REGULAR), 0 },
                { new Item("Banana", 20.00, 1, Item.Type.SECOND), 0 },
                { new Item("Banana", 20.00, 4, Item.Type.SECOND), 50 },
                { new Item("Mango", 17.20, 1, Item.Type.SALE), 70 },
                { new Item("Egg", 2.00, 5, Item.Type.DISCOUNT), 10 },
                { new Item("Egg", 2.00, 20, Item.Type.DISCOUNT), 30 },
                { new Item("Egg", 2.00, 60, Item.Type.DISCOUNT), 50 },
                { new Item("Apple", 0.99, 500, Item.Type.REGULAR), 50 },
                { new Item("Apple", 0.99, 1000, Item.Type.REGULAR), 80 }
        });
    }

    @Test
    public void testCalculateDiscount() {
        assertEquals(expectedDiscount, ShoppingCart.calculateDiscount((item)));
    }
}
