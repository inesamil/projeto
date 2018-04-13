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
    public static final int LIST_ID_MIN = 1;
    public static final int LIST_NAME_MAX_LENGTH = 35;

    public enum LIST_TYPE {
        system, user
    }

    /// ListProduct
    public static final int LISTPRODUCT_BRAND_MAX_LENGTH = 35;
    public static final int LISTPRODUCT_QUANTITY_MIN = 1;

    /// Storage
    public static final short STORAGE_ID_MIN = 1;

    /// StockItem
    public static final int STOCKITEM_SKU_MAX_LENGTH = 128;
    public static final int STOCKITEM_BRAND_MAX_LENGTH = 35;
    public static final float STOCKITEM_SEGMENT_MIN = 0.1f;
    public static final int STOCKITEM_VARIETY_MAX_LENGTH = 35;
    public static final int STOCKITEM_QUANTITY_MIN = 1;
    public static final int STOCKITEM_DESCRIPTION_MAX_LENGTH = 10485760;
    public static final int STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH = 128;
    
    public enum STOCKITEM_SEGMENTUNIT {
        kg("kg"), dag("dag"), hg("hg"), g("g"), dg("dg"), cg("cg"), mg("mg"),
        kl("kl"), hl("hl"), dal("dal"), l("l"), dl("dl"), cl("cl"), ml("ml"),
        oz("oz"), lb("lb"), pt("pt"), fl_oz("fl oz"), 
        units("units");

        private final String unit;
        
        STOCKITEM_SEGMENTUNIT(String unit) {
            this.unit = unit;
        }

        @Override
        public String toString() {
            return unit;
        }
    }


    /// StockItemStorage
    public static final short STOCKITEMSTORAGE_QUANTITY_MIN = 1;

    /// Category
    public static final int CATEGORY_ID_MIN = 1;
    public static final int CATEGORY_NAME_MAX_LENGTH = 35;

    /// Product
    public static final int PRODUCT_ID_MIN = 1;
    public static final int PRODUCT_NAME_MAX_LENGTH = 35;
    public static final Short PRODUCT_SHELFLIFE_MIN = 1;

    public enum PRODUCT_SHELFLIFETIMEUNIT {
        day, week, month, year
    }

    /// ExpirationDate
    public static final short EXPIRATIONDATE_QUANTITY_MIN = 1;
}
