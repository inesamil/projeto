package pt.isel.ps.gis.utils;

import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void test_is_string_valid_date() {
        String date = "2018-06-21";
        boolean isValid = DateUtils.isStringValidDate(date);
        assertTrue(isValid);

        date = "2020-02-29";
        isValid = DateUtils.isStringValidDate(date);
        assertTrue(isValid);

        date = "2018-06-31";
        isValid = DateUtils.isStringValidDate(date);
        assertFalse(isValid);

        date = "2018-02-29";
        isValid = DateUtils.isStringValidDate(date);
        assertFalse(isValid);
    }

    @Test
    public void test_is_string_valid_date_time() {
        String datetime = "2018-06-21 12:05:32";
        boolean isValid = DateUtils.isStringValidDateTime(datetime);
        assertTrue(isValid);

        datetime = "2020-02-29 23:59:59";
        isValid = DateUtils.isStringValidDateTime(datetime);
        assertTrue(isValid);

        datetime = "2020-02-29 23:59:60";
        isValid = DateUtils.isStringValidDateTime(datetime);
        assertFalse(isValid);

        datetime = "2020-02-29 24:00:00";
        isValid = DateUtils.isStringValidDateTime(datetime);
        assertFalse(isValid);
    }

    @Test
    public void test_convert_string_to_date_format_and_vice_versa() throws ParseException {
        String strDate = "2018-06-21";
        Date date = DateUtils.convertStringToDate(strDate);
        String result = DateUtils.convertDateFormat(date);
        assertEquals(strDate, result);
    }

    @Test
    public void test_convert_string_to_timestamp_format_and_vice_versa() throws ParseException {
        String strTimestamp = "2018-06-21 12:05:32";
        Timestamp timestamp = DateUtils.convertStringToTimestamp(strTimestamp);
        String result = DateUtils.convertTimestampFormat(timestamp);
        assertEquals(strTimestamp, result);
    }
}
