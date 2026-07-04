package com.robisa693.alwayshearsdrops;

import org.junit.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

public class AlwaysHearDropsPluginTest
{
    private static final Pattern VALUABLE_DROP_PATTERN = Pattern.compile(
        "Valuable drop: (.+) \\(([\\d,]+) coins\\)"
    );
    private static final Pattern UNTRADEABLE_DROP_PATTERN = Pattern.compile(
        "Untradeable drop: (.+)"
    );

    @Test
    public void testValuableDropSingle()
    {
        String msg = "<col=ef1020>Valuable drop: Rune scimitar (25,600 coins)</col>";
        Matcher m = VALUABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("Rune scimitar", m.group(1).trim());
        assertEquals("25,600", m.group(2));
    }

    @Test
    public void testValuableDropQuantity()
    {
        String msg = "<col=ef1020>Valuable drop: 6 x Bronze arrow (42 coins)</col>";
        Matcher m = VALUABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("6 x Bronze arrow", m.group(1).trim());
        assertEquals("42", m.group(2));
    }

    @Test
    public void testValuableDropItemWithParens()
    {
        String msg = "<col=ef1020>Valuable drop: 2222 x Ranging potion(3) (987 coins)</col>";
        Matcher m = VALUABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("2222 x Ranging potion(3)", m.group(1).trim());
        assertEquals("987", m.group(2));
    }

    @Test
    public void testUntradeableDropSingle()
    {
        String msg = "<col=ef1020>Untradeable drop: Rusty sword";
        Matcher m = UNTRADEABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("Rusty sword", m.group(1).trim());
    }

    @Test
    public void testUntradeableDropQuantity()
    {
        String msg = "<col=ef1020>Untradeable drop: 3 x Bronze arrow";
        Matcher m = UNTRADEABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("3 x Bronze arrow", m.group(1).trim());
    }

    @Test
    public void testNoValuableDropMatch()
    {
        String msg = "<col=ef1020>Valuable drop: something without coins tag</col>";
        Matcher m = VALUABLE_DROP_PATTERN.matcher(msg);
        assertFalse(m.find());
    }

    @Test
    public void testValuableDropLargeValue()
    {
        String msg = "<col=ef1020>Valuable drop: 100 x Test item (1,000,000 coins)</col>";
        Matcher m = VALUABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertEquals("1,000,000", m.group(2));
    }

    @Test
    public void testUntradeableDropWithUntrimmedName()
    {
        String msg = "<col=ef1020>Untradeable drop: 111111 x Ranging potion(3)";
        Matcher m = UNTRADEABLE_DROP_PATTERN.matcher(msg);
        assertTrue(m.find());
        assertTrue(m.group(1).trim().startsWith("111111"));
    }
}
