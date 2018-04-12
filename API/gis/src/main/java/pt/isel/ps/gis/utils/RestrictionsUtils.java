package pt.isel.ps.gis.utils;

/**
 * Classe utilitária com restrições de domínio
 */
public class RestrictionsUtils {

    /// House
    public static final long HOUSE_ID_MIN = 1;
    public static final int HOUSE_NAME_MAX_LENGTH = 35;

    /// Allergy
    public static final int ALLERGY_ALLERGEN_MAX_LENGTH = 75;

    /// HouseAllergy
    public static final int HOUSEALLERGY_ALERGICSNUM_MIN = 1;

    /// List
    public static final Short LIST_ID_MIN = 1;
    public static final int LIST_NAME_MAX_LENGTH = 35;
    /// ListProduct
    public static final int LISTPRODUCT_BRAND_MAX_LENGTH = 35;

    ;
    public static final int LISTPRODUCT_QUANTITY_MIN = 1;
    /// StockItem
    public static final int STOCKITEM_SKU_MAX_LENGTH = 128;
    /// Category
    public static final int CATEGORY_ID_MIN = 1;
    public static final int CATEGORY_NAME_MAX_LENGTH = 35;
    /// Product
    public static final int PRODUCT_ID_MIN = 1;
    /// ExpirationDate
    public static final short EXPIRATIONDATE_QUANTITY_MIN = 1;

    public enum LIST_TYPE {
        system, user
    }
}
