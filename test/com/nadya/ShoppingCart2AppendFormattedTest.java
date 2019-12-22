package com.nadya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import static com.nadya.ShoppingCart2Formatter.Align;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShoppingCart2AppendFormattedTest {
    private String value;
    private ShoppingCart2Formatter.Align align;
    private int width;
    private String expected;

    public ShoppingCart2AppendFormattedTest(String value, Align align, int width, String expected) {
        this.value = value;
        this.align = align;
        this.width = width;
        this.expected = expected;
    }
    @Parameterized.Parameters
    public static Collection a() {
        return Arrays.asList(new Object[][]{
                { "SomeLine", Align.CENTER, 14, "   SomeLine    " },
                { "SomeLine", Align.CENTER, 15, "   SomeLine     " },
                { "SomeLine", Align.CENTER, 5, "SomeL " },
                { "SomeLine", Align.RIGHT, 15, "       SomeLine " },
                { "SomeLine", Align.LEFT, 15, "SomeLine        " }
        });
    }
    @Test
    public void testAppendFormatted() {
        StringBuilder sb = new StringBuilder();
        ShoppingCart2Formatter.appendFormatted(sb, value, align, width);
        assertEquals(expected, sb.toString());
    }
}
    
