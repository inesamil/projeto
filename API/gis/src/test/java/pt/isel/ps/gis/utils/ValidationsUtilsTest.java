package pt.isel.ps.gis.utils;

import org.junit.Before;
import org.junit.Test;
import pt.isel.ps.gis.exceptions.EntityException;

import static org.junit.Assert.*;

public class ValidationsUtilsTest {

    @Test
    public void validateHouseId() {
        Long nullId = null;
        Long invalidId = -1L;
        Long validId = 1L;

        // Test null House ID
        try {
            ValidationsUtils.validateHouseId(nullId);
            fail("No exception thrown.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "House ID is required.");
        }

        // Test invalid House ID
        try {
            ValidationsUtils.validateHouseId(invalidId);
            fail("No exception thrown.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "Invalid House ID.");
        }

        // Test valid House ID
        try {
            ValidationsUtils.validateHouseId(validId);
            assertTrue(true);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateHouseName() {
        String nullName = null;
        String invalidName = "This Name is too long. Thus it's not allowed.";
        String validName = "Smith's House";

        // Test null House name
        try {
            ValidationsUtils.validateHouseName(nullName);
            fail("No exception thrown.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "House name is required.");
        }

        // Test invalid House name
        try {
            ValidationsUtils.validateHouseName(invalidName);
            fail("No exception thrown.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "Invalid House name.");
        }

        // Test valid House name
        try {
            ValidationsUtils.validateHouseName(validName);
            assertTrue(true);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateCharacteristics() {
    }

    @Test
    public void validateUserUsername() {
    }

    @Test
    public void validateUserEmail() {
    }

    @Test
    public void validateUserAge() {
    }

    @Test
    public void validateUserName() {
    }

    @Test
    public void validateUserPassword() {
    }

    @Test
    public void validateUserHouseAdministrator() {
    }

    @Test
    public void validateAllergyAllergen() {
    }

    @Test
    public void validateHouseAllergyAllergicsNum() {
    }

    @Test
    public void validateListId() {
    }

    @Test
    public void validateListName() {
    }

    @Test
    public void validateListType() {
    }

    @Test
    public void validateCategoryId() {
    }

    @Test
    public void validateCategoryName() {
    }

    @Test
    public void validateProductId() {
    }

    @Test
    public void validateProductName() {
    }

    @Test
    public void validateProductEdible() {
    }

    @Test
    public void validateProductShelflife() {
    }

    @Test
    public void validateProductShelflifeTimeunit() {
    }

    @Test
    public void validateDate() {
    }

    @Test
    public void validateStorageId() {
    }

    @Test
    public void validateStorageName() {
    }

    @Test
    public void validateStorageTemperature() {
    }

    @Test
    public void validateStockItemSku() {
    }

    @Test
    public void validateStockItemBrand() {
    }

    @Test
    public void validateStockItemSegment() {
    }

    @Test
    public void validateStockItemSegmentUnit() {
    }

    @Test
    public void validateStockItemVariety() {
    }

    @Test
    public void validateStockItemQuantity() {
    }

    @Test
    public void validateStockItemDescription() {
    }

    @Test
    public void validateStockItemConservationStorage() {
    }

    @Test
    public void validateStockItemMovementDateTime() {
    }

    @Test
    public void validateStockItemMovementType() {
    }

    @Test
    public void validateStockItemMovementQuantity() {
    }

    @Test
    public void validateExpirationDateQuantity() {
    }

    @Test
    public void validateListProductBrand() {
    }

    @Test
    public void validateListProductQuantity() {
    }

    @Test
    public void validateStockItemStorageQuantity() {
    }

    @Test
    public void validateListShareable() {
    }
}