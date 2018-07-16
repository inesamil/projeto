package pt.isel.ps.gis.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputUtilsTest {

    @Test
    public void test_split_numbers_from_letters1() {
        String[] first = InputUtils.splitNumbersFromLetters("1L");
        assertEquals(2, first.length);
        assertEquals("1", first[0]);
        assertEquals("L", first[1]);
    }

    @Test
    public void test_split_numbers_from_letters2() {
        String[] second = InputUtils.splitNumbersFromLetters("200mg");
        assertEquals(2, second.length);
        assertEquals("200", second[0]);
        assertEquals("mg", second[1]);
    }

    @Test
    public void test_split_numbers_from_letters3() {
        String[] third = InputUtils.splitNumbersFromLetters("200mg1L");
        assertEquals(2, third.length);
        assertEquals("2001", third[0]);
        assertEquals("mgL", third[1]);
    }
}
