package com.nadya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import static com.nadya.Item2.ItemType;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShoppingCart2CalculateDiscountTest {
    private Item2 item;
    private int expectedDiscount;

    public ShoppingCart2CalculateDiscountTest(Item2 item, int expectedDiscount) {
        this.item = item;
        this.expectedDiscount = expectedDiscount;
    }

    @Parameterized.Parameters
    public static Collection a() {
        return Arrays.asList(new Object[][]{
                { new Item2("Apple", 0.99, 5, ItemType.NEW), 0 },
                { new Item2("Banana", 20.00, 1, ItemType.SECOND_FREE), 0 },
                { new Item2("Banana", 20.00, 4, ItemType.SECOND_FREE), 50 },
                { new Item2("Mango", 17.20, 5, ItemType.SALE), 70 },
                { new Item2("Egg", 2.00, 5, ItemType.REGULAR), 0 },
                { new Item2("Egg", 2.00, 20, ItemType.REGULAR), 2 },
                { new Item2("Apple", 0.99, 500, ItemType.REGULAR), 50 },
                { new Item2("Apple", 0.99, 1000, ItemType.REGULAR), 80 }
        });
    }

    @Test
    public void testCalculateDiscount() {
        assertEquals(expectedDiscount, item.calculateDiscount());
    }
}
