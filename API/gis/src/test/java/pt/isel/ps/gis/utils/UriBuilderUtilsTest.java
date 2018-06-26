package pt.isel.ps.gis.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UriBuilderUtilsTest {

    private final static String INDEX = "/v1",
            ALLERGIES = "/v1/allergies",
            CATEGORIES = "/v1/categories",
            CATEGORY = "/v1/categories/1",
            PRODUCTS_CATEGORY = "/v1/categories/1/products",
            PRODUCT = "/v1/categories/1/products/1",
            HOUSE = "/v1/houses/1",
            HOUSE_ALLERGIES = "/v1/houses/1/allergies",
            HOUSE_ALLERGY = "/v1/houses/1/allergies/lactose",
            STOCK_ITEMS_ALLERGEN = "/v1/houses/1/allergies/lactose/items",
            STOCK_ITEMS = "/v1/houses/1/items",
            STOCK_ITEM = "/v1/houses/1/items/C1P1-Mimosa-Magro-1l",
            ALLERGIES_STOCK_ITEM = "/v1/houses/1/items/C1P1-Mimosa-Magro-1l/allergies",
            LISTS = "/v1/houses/1/lists",
            LIST = "/v1/houses/1/lists/1",
            PRODUCTS_LIST = "/v1/houses/1/lists/1/products",
            PRODUCT_LIST = "/v1/houses/1/lists/1/products/1",
            MOVEMENTS = "/v1/houses/1/movements",
            STORAGES = "/v1/houses/1/storages",
            STORAGE = "/v1/houses/1/storages/1",
            HOUSEHOLD = "/v1/houses/1/users",
            USER_LIST = "/v1/houses/1/lists/1",
            USER = "/v1/users/test",
            USER_HOUSES = "/v1/users/test/houses";

    @Test
    public void test_build_index_uri() {
        assertEquals(INDEX, UriBuilderUtils.buildIndexUri());
    }

    @Test
    public void test_build_allergies_uri() {
        assertEquals(ALLERGIES, UriBuilderUtils.buildAllergiesUri());
    }

    @Test
    public void test_build_categories_uri() {
        assertEquals(CATEGORIES, UriBuilderUtils.buildCategoriesUri());
    }

    @Test
    public void test_build_category_uri() {
        assertEquals(CATEGORY, UriBuilderUtils.buildCategoryUri(1));
    }

    @Test
    public void test_build_products_category_uri() {
        assertEquals(PRODUCTS_CATEGORY, UriBuilderUtils.buildProductsCategoryUri(1));
    }

    @Test
    public void test_build_product_uri() {
        assertEquals(PRODUCT, UriBuilderUtils.buildProductUri(1, 1));
    }

    @Test
    public void test_build_house_uri() {
        assertEquals(HOUSE, UriBuilderUtils.buildHouseUri(1));
    }

    @Test
    public void test_build_house_allergies_uri() {
        assertEquals(HOUSE_ALLERGIES, UriBuilderUtils.buildHouseAllergiesUri(1));
    }

    @Test
    public void test_build_house_allergy_uri() {
        assertEquals(HOUSE_ALLERGY, UriBuilderUtils.buildHouseAllergyUri(1, "lactose"));
    }

    @Test
    public void test_build_stock_items_allergen_uri() {
        assertEquals(STOCK_ITEMS_ALLERGEN, UriBuilderUtils.buildStockItemsAllergenUri(1, "lactose"));
    }

    @Test
    public void test_build_stock_items_uri() {
        assertEquals(STOCK_ITEMS, UriBuilderUtils.buildStockItemsUri(1));
    }

    @Test
    public void test_build_stock_item_uri() {
        assertEquals(STOCK_ITEM, UriBuilderUtils.buildStockItemUri(1, "C1P1-Mimosa-Magro-1l"));
    }

    @Test
    public void test_build_allergies_stock_item_uri() {
        assertEquals(ALLERGIES_STOCK_ITEM, UriBuilderUtils.buildAllergiesStockItemUri(1, "C1P1-Mimosa-Magro-1l"));
    }

    @Test
    public void test_build_lists_uri() {
        assertEquals(LISTS, UriBuilderUtils.buildListsUri(1));
    }

    @Test
    public void test_build_list_uri() {
        assertEquals(LIST, UriBuilderUtils.buildListUri(1L, (short) 1));
    }

    @Test
    public void test_build_products_list_uri() {
        assertEquals(PRODUCTS_LIST, UriBuilderUtils.buildListProductstUri(1, (short) 1));
    }

    @Test
    public void test_build_product_list_uri() {
        assertEquals(PRODUCT_LIST, UriBuilderUtils.buildListProductUri(1, (short) 1, 1));
    }

    @Test
    public void test_build_movements_uri() {
        assertEquals(MOVEMENTS, UriBuilderUtils.buildMovementsUri(1));
    }

    @Test
    public void test_build_storages_uri() {
        assertEquals(STORAGES, UriBuilderUtils.buildStoragesUri(1));
    }

    @Test
    public void test_build_storage_uri() {
        assertEquals(STORAGE, UriBuilderUtils.buildStorageUri(1L, (short) 1));
    }

    @Test
    public void test_build_household_uri() {
        assertEquals(HOUSEHOLD, UriBuilderUtils.buildHouseholdUri(1));
    }

    /*@Test
    public void test_build_user_list_uri() {
        assertEquals(USER_LIST, UriBuilderUtils.buildUserListUri(1L, (short) 1));
    }*/

    @Test
    public void test_build_user_uri() {
        assertEquals(USER, UriBuilderUtils.buildUserUri("test"));
    }

    @Test
    public void test_build_user_houses_uri() {
        assertEquals(USER_HOUSES, UriBuilderUtils.buildUserHousesUri("test"));
    }
}
