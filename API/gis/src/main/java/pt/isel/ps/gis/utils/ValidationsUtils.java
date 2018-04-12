package pt.isel.ps.gis.utils;

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
     *
     * @param allergyAllergen
     * @throws IllegalArgumentException
     */
    public static void validateAllergyAllergen(String allergyAllergen) throws IllegalArgumentException{
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
        if (listType == null || !listType.equals(RestrictionsUtils.LIST_TYPE.system)
                || !listType.equals(RestrictionsUtils.LIST_TYPE.user))
            throw new IllegalArgumentException("Invalid list type.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Category                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateCategoryId(Integer categoryId) throws IllegalArgumentException {
        if (categoryId == null || categoryId < RestrictionsUtils.CATEGORY_ID_MIN)
            throw new IllegalArgumentException("Invalid category ID.");
    }

    public static void validateCategoryName(String categoryName) throws IllegalArgumentException{
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Date                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateDate(String date) throws IllegalArgumentException{
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
