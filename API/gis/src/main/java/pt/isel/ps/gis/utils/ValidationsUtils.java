package pt.isel.ps.gis.utils;

import com.sun.org.apache.xpath.internal.operations.Mod;
import pt.isel.ps.gis.exceptions.ModelException;
import sun.awt.ModalExclude;

public class ValidationsUtils {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            House                                                           ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateHouseId(Long houseId) throws IllegalArgumentException {
        if (houseId == null || houseId < RestrictionsUtils.HOUSE_ID_MIN)
            throw new IllegalArgumentException("Invalid house ID.");
    }

    public static void validateHouseName(String houseName) throws IllegalArgumentException {
        if (houseName == null || houseName.length() > RestrictionsUtils.HOUSE_NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid house name.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Allergy                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param allergyAllergen
     * @throws IllegalArgumentException
     */
    public static void validateAllergyAllergen(String allergyAllergen) throws IllegalArgumentException {
        if (allergyAllergen == null || allergyAllergen.length() > RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid allergen.");
    }

    public static void validateHouseAllergyAllergicsNum(Short alergicsNum) throws IllegalArgumentException {
        if (alergicsNum == null || alergicsNum < RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN)
            throw new IllegalArgumentException("Invalid allergics number.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            List                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateListId(Short listId) throws IllegalArgumentException {
        if (listId == null || listId < RestrictionsUtils.LIST_ID_MIN)
            throw new IllegalArgumentException("Invalid list ID.");
    }

    public static void validateListName(String listName) throws IllegalArgumentException {
        if (listName == null || listName.length() > RestrictionsUtils.LIST_NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid list name.");
    }

    public static void validateListType(String listType) throws IllegalArgumentException {
        if (listType == null)
            throw new IllegalArgumentException("Must specify a list type.");
        for (RestrictionsUtils.LIST_TYPE type : RestrictionsUtils.LIST_TYPE.values()) {
            if (listType.compareToIgnoreCase(type.name()) == 0)
                return;
        }
        throw new IllegalArgumentException("Invalid list type.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Category                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateCategoryId(Integer categoryId) throws IllegalArgumentException {
        if (categoryId == null || categoryId < RestrictionsUtils.CATEGORY_ID_MIN)
            throw new IllegalArgumentException("Invalid category ID.");
    }

    public static void validateCategoryName(String categoryName) throws IllegalArgumentException {
        if (categoryName == null || categoryName.length() > RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid category name.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Product                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateProductId(Integer productId) throws IllegalArgumentException {
        if (productId == null || productId < RestrictionsUtils.PRODUCT_ID_MIN)
            throw new IllegalArgumentException("Invalid product ID.");
    }

    public static void validateProductName(String productName) throws IllegalArgumentException {
        if (productName == null || productName.length() > RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid product name.");
    }

    public static void validateProductEdible(Boolean productEdible) throws IllegalArgumentException {
        if (productEdible == null)
            throw new IllegalArgumentException("Must specify the product edibility.");
    }

    public static void validateProductShelflife(Short productShelflife) throws IllegalArgumentException {
        if (productShelflife == null || productShelflife < RestrictionsUtils.PRODUCT_SHELFLIFE_MIN)
            throw new IllegalArgumentException("Invalid product shelflife.");
    }

    public static void validateProductShelflifeTimeunit(String productShelflifetimeunit) {
        if (productShelflifetimeunit == null)
            throw new IllegalArgumentException("Must specify a time unit for the product shelflife.");
        for (RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT shelflifetimeunit : RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.values()) {
            if (productShelflifetimeunit.compareToIgnoreCase(shelflifetimeunit.name()) == 0)
                return;
        }
        throw new IllegalArgumentException("Invalid product shelflife time unit.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Date                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateDate(String date) throws IllegalArgumentException {
        if (date == null || !DateUtils.isStringIsValidDate(date))
            throw new IllegalArgumentException("Invalid date.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItem                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateStockItemSku(String sku) throws IllegalArgumentException {
        if (sku == null || sku.length() > RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid stock item SKU.");
    }

    public static void validateStockItemBrand(String stockitemBrand) throws ModelException {
        if (stockitemBrand == null || stockitemBrand.length() > RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH)
            throw new ModelException("Invalid stock item brand.");
    }

    public static void validateStockItemSegment(String stockitemSegment) throws ModelException {
        if (stockitemSegment == null || stockitemSegment.length() > RestrictionsUtils.STOCKITEM_SEGMENT_MAX_LENGTH)
            throw new ModelException("Invalid stock item segment.");
    }

    public static void validateStockItemSegmentUnit(String stockitemSegmentUnit) throws ModelException {
        if (stockitemSegmentUnit == null)
            throw new ModelException("Stock item segment unit is mandatory.");
        for (RestrictionsUtils.STOCKITEM_SEGMENTUNIT unit : RestrictionsUtils.STOCKITEM_SEGMENTUNIT.values()) {
            if (stockitemSegmentUnit.compareToIgnoreCase(unit.toString()) == 0)
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
    ////                                            ExpirationDate                                                  ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateExpirationDateQuantity(Short quantity) throws IllegalArgumentException {
        if (quantity == null || quantity < RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN)
            throw new IllegalArgumentException("Invalid quantity.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ListProduct                                                     ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateListProductBrand(String listproductBrand) throws IllegalArgumentException {
        if (listproductBrand != null && listproductBrand.length() > RestrictionsUtils.LISTPRODUCT_BRAND_MAX_LENGTH)
            throw new IllegalArgumentException("Invalid brand.");
    }

    public static void validateListProductQuantity(Short quantity) throws IllegalArgumentException {
        if (quantity == null || quantity < RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN)
            throw new IllegalArgumentException("Invalid quantity.");
    }
}
