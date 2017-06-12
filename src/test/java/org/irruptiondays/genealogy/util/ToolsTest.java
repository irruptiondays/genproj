package org.irruptiondays.genealogy.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Testing for utility class.
 */
public class ToolsTest {

    @Test
    public void testDateString() {

        assertEquals("Sunday, June 11, 2017", Tools.dateString(new Date(1497222802000L)));
    }
}
