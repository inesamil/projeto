package pt.isel.ps.gis.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xpath.internal.operations.Mod;
import pt.isel.ps.gis.exceptions.ModelException;

import java.io.Serializable;

public class ValidationsUtils {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            House                                                           ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateHouseId(Long houseId) throws ModelException {
        if (houseId == null || houseId < RestrictionsUtils.HOUSE_ID_MIN)
            throw new ModelException("Invalid house ID.");
    }

    public static void validateHouseName(String houseName) throws ModelException {
        if (houseName == null || houseName.length() > RestrictionsUtils.HOUSE_NAME_MAX_LENGTH)
            throw new ModelException("Invalid house name.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            User                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateUserUsername(String username) throws ModelException {
        if (username == null)
            throw new ModelException("Username is required.");
        if (username.length() > RestrictionsUtils.USER_USERNAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid username. Username must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_USERNAME_MAX_LENGTH));
    }

    public static void validateUserEmail(String email) throws ModelException {
        if (email == null)
            throw new ModelException("Email is required.");
        if (email.length() > RestrictionsUtils.USER_EMAIL_MAX_LENGTH)
            throw new ModelException("Invalid email.");
        if (!EmailUtils.isStringValidEmail(email))
            throw new ModelException("Invalid email.");
    }

    public static void validateUserAge(Short age) throws ModelException{
        if (age == null)
            throw new ModelException("Age is required.");
        if (age < RestrictionsUtils.USER_AGE_MIN || age > RestrictionsUtils.USER_AGE_MAX)
            throw new ModelException("Invalid age.");
    }

    public static void validateUserName(String name) throws ModelException {
        if (name == null)
            throw new ModelException("Name is required.");
        if (name.length() > RestrictionsUtils.USER_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_NAME_MAX_LENGTH));
    }

    public static void validateUserPassword(String password) throws ModelException {
        if (password == null)
            throw new ModelException("Password is required.");
        if (password.length() > RestrictionsUtils.USER_PASSWORD)
            throw new ModelException(String.format("Password too long. Password must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_PASSWORD));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserHouse                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateUserHouseAdministrator(Boolean userhouseAdministrator) throws ModelException {
        if (userhouseAdministrator == null)
            throw new ModelException("Administrator is required.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Allergy                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param allergyAllergen
     * @throws ModelException
     */
    public static void validateAllergyAllergen(String allergyAllergen) throws ModelException {
        if (allergyAllergen == null || allergyAllergen.length() > RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH)
            throw new ModelException("Invalid allergen.");
    }

    public static void validateHouseAllergyAllergicsNum(Short alergicsNum) throws ModelException {
        if (alergicsNum == null || alergicsNum < RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN)
            throw new ModelException("Invalid allergics number.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            List                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateListId(Short listId) throws ModelException {
        if (listId == null || listId < RestrictionsUtils.LIST_ID_MIN)
            throw new ModelException("Invalid list ID.");
    }

    public static void validateListName(String listName) throws ModelException {
        if (listName == null || listName.length() > RestrictionsUtils.LIST_NAME_MAX_LENGTH)
            throw new ModelException("Invalid list name.");
    }

    public static void validateListType(String listType) throws ModelException {
        if (listType == null)
            throw new ModelException("Must specify a list type.");
        if (listType.length() > RestrictionsUtils.LIST_TYPE_MAX_LENGTH)
            throw new ModelException("Invalid list type.");
        for (RestrictionsUtils.LIST_TYPE type : RestrictionsUtils.LIST_TYPE.values()) {
            if (listType.compareToIgnoreCase(type.name()) == 0)
                return;
        }
        throw new ModelException("Invalid list type.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Category                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateCategoryId(Integer categoryId) throws ModelException {
        if (categoryId == null || categoryId < RestrictionsUtils.CATEGORY_ID_MIN)
            throw new ModelException("Invalid category ID.");
    }

    public static void validateCategoryName(String categoryName) throws ModelException {
        if (categoryName == null || categoryName.length() > RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH)
            throw new ModelException("Invalid category name.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Product                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateProductId(Integer productId) throws ModelException {
        if (productId == null || productId < RestrictionsUtils.PRODUCT_ID_MIN)
            throw new ModelException("Invalid product ID.");
    }

    public static void validateProductName(String productName) throws ModelException {
        if (productName == null || productName.length() > RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH)
            throw new ModelException("Invalid product name.");
    }

    public static void validateProductEdible(Boolean productEdible) throws ModelException {
        if (productEdible == null)
            throw new ModelException("Product edibility is mandatory.");
    }

    public static void validateProductShelflife(Short productShelflife) throws ModelException {
        if (productShelflife == null || productShelflife < RestrictionsUtils.PRODUCT_SHELFLIFE_MIN)
            throw new ModelException("Invalid product shelflife.");
    }

    public static void validateProductShelflifeTimeunit(String shelflifeTimeUnit) throws ModelException {
        if (shelflifeTimeUnit == null)
            throw new ModelException("Must specify a time unit for the product shelflife.");
        if (shelflifeTimeUnit.length() > RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT_MAX_LENGTH)
            throw new ModelException("Invalid product shelflife time unit.");
        for (RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT shelflifetimeunit : RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.values()) {
            if (shelflifeTimeUnit.compareToIgnoreCase(shelflifetimeunit.name()) == 0)
                return;
        }
        throw new ModelException("Invalid product shelflife time unit.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Date                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateDate(String date) throws ModelException {
        if (date == null || !DateUtils.isStringValidDate(date))
            throw new ModelException("Invalid date.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Storage                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateStorageId(Short storageId) throws ModelException {
        if (storageId == null)
            throw new ModelException("Storage ID is mandatory.");
        if (storageId < RestrictionsUtils.STORAGE_ID_MIN)
            throw new ModelException(String.format("Storage ID must not be less than %d", RestrictionsUtils.STORAGE_ID_MIN));
    }

    public static void validateStorageName(String storageName) throws ModelException {
        if (storageName == null)
            throw new ModelException("Name is required.");
        if (storageName.length() > RestrictionsUtils.STORAGE_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.STORAGE_NAME_MAX_LENGTH));
    }

    public static void validateStorageTemperature(Serializable storageTemperature) throws ModelException {
        if (storageTemperature == null)
            throw new ModelException("Temperature is required.");
        //TODO: validations
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItem                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateStockItemSku(String sku) throws ModelException {
        if (sku == null || sku.length() > RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH)
            throw new ModelException("Invalid stock item SKU.");
    }

    public static void validateStockItemBrand(String stockitemBrand) throws ModelException {
        if (stockitemBrand == null || stockitemBrand.length() > RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH)
            throw new ModelException("Invalid stock item brand.");
    }

    public static void validateStockItemSegment(Float stockitemSegment) throws ModelException {
        if (stockitemSegment == null || stockitemSegment < RestrictionsUtils.STOCKITEM_SEGMENT_MIN)
            throw new ModelException("Invalid stock item segment.");
    }

    public static void validateStockItemSegmentUnit(String segmentUnit) throws ModelException {
        if (segmentUnit == null)
            throw new ModelException("Stock item segment unit is mandatory.");
        if (segmentUnit.length() > RestrictionsUtils.STOCKITEM_SEGMENTUNIT_MAX_LENGTH)
            throw new ModelException("Invalid stock item segment unit.");
        for (RestrictionsUtils.STOCKITEM_SEGMENTUNIT unit : RestrictionsUtils.STOCKITEM_SEGMENTUNIT.values()) {
            if (segmentUnit.compareToIgnoreCase(unit.toString()) == 0)
                return;
        }
        throw new ModelException("Invalid stock item segment unit.");
    }

    public static void validateStockItemVariety(String stockitemVariety) throws ModelException {
        if (stockitemVariety == null)
            throw new ModelException("Stock item variety is mandatory.");
        if (stockitemVariety.length() > RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item variety. Variety must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH));
    }

    public static void validateStockItemQuantity(Short stockitemQuantity) throws ModelException {
        if (stockitemQuantity == null)
            throw new ModelException("Stock item quantity is mandatory.");
        if (stockitemQuantity < RestrictionsUtils.STOCKITEM_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid stock item quantity. Quantity must not be less than %d",
                    RestrictionsUtils.STOCKITEM_QUANTITY_MIN));
    }

    public static void validateStockItemDescription(String stockitemDescription) throws ModelException {
        if (stockitemDescription != null || stockitemDescription.length() > RestrictionsUtils.STOCKITEM_DESCRIPTION_MAX_LENGTH)
            throw new ModelException("Invalid description. Too much characters.");

    }

    public static void validateStockItemConservationStorage(String stockitemConservationstorage) throws ModelException {
        if (stockitemConservationstorage == null)
            throw new ModelException("Stock item conservation storage is mandatory.");
        if (stockitemConservationstorage.length() < RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item conservation storage. Conservation storage must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItemMovement                                               ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateStockItemMovementDateTime(String datetime) throws ModelException {
        if (datetime == null)
            throw new ModelException("Stock item movement DateTime is mandatory.");
        if (!DateUtils.isStringValidDateTime(datetime))
            throw new ModelException("Invalid stock item movement DateTime. The format is: \"yyyy-MM-dd HH:mm:ss\".");
    }

    public static void validateStockItemMovementType(Boolean type) throws ModelException {
        if (type == null)
            throw new ModelException("Movement type is mandatory.");
    }

    public static void validateStockItemMovementQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Movement quantity is mandatory.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ExpirationDate                                                  ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateExpirationDateQuantity(Short quantity) throws ModelException {
        if (quantity == null || quantity < RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN)
            throw new ModelException("Invalid quantity.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ListProduct                                                     ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateListProductBrand(String brand) throws ModelException {
        if (brand != null && brand.length() > RestrictionsUtils.LISTPRODUCT_BRAND_MAX_LENGTH)
            throw new ModelException("Invalid brand.");
    }

    public static void validateListProductQuantity(Short quantity) throws ModelException {
        if (quantity == null || quantity < RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN)
            throw new ModelException("Invalid quantity.");
    }

    public static void validateStockItemStorageQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Quantity is required.");
        if (quantity < RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN)
            throw new ModelException("Invalid quantity.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserList                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateListShareable(Boolean shareable) throws ModelException {
        if (shareable == null)
            throw new ModelException("Shareable is required.");
    }
}
