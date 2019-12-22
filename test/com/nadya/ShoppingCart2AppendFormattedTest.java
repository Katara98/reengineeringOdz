package com.nadya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShoppingCart2AppendFormattedTest {
    private String value;
    private int align;
    private int width;
    private String expected;

    public ShoppingCart2AppendFormattedTest(String value, int align, int width, String expected) {
        this.value = value;
        this.align = align;
        this.width = width;
        this.expected = expected;
    }
    @Parameterized.Parameters
    public static Collection a() {
        return Arrays.asList(new Object[][]{
                { "SomeLine", 0, 14, "   SomeLine    " },
                { "SomeLine", 0, 15, "   SomeLine     " },
                { "SomeLine", 0, 5, "SomeL " },
                { "SomeLine", 1, 15, "       SomeLine " },
                { "SomeLine", -1, 15, "SomeLine        " }
        });
    }
    @Test
    public void testAppendFormatted() {
        StringBuilder sb = new StringBuilder();
        ShoppingCart2Formatter.appendFormatted(sb, value, align, width);
        assertEquals(expected, sb.toString());
    }
}
    
