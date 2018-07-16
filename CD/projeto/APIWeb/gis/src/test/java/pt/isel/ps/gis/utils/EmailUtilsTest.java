package pt.isel.ps.gis.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailUtilsTest {

    @Test
    public void test_is_string_valid_email_true() {
        String email = "test@test.xpto";
        boolean isValid = EmailUtils.isStringValidEmail(email);
        assertTrue(isValid);

        email = "test_00@test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertTrue(isValid);

        email = "test_00-xpto@test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertTrue(isValid);

        email = "test_xpto@test.test.test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertTrue(isValid);
    }

    @Test
    public void test_is_string_valid_email_false() {
        String email = "test.xpto";
        boolean isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);

        email = "test@test";
        isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);

        email = "@test";
        isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);

        email = "@test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);

        email = "?@test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);

        email = ":@test.xpto";
        isValid = EmailUtils.isStringValidEmail(email);
        assertFalse(isValid);
    }
}
