package pt.isel.ps.gis.utils;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class UriBuilderUtilsTest {

    private static String HOST;

    static {
        try {
            HOST = String.format("http://%s:8081", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            HOST = "http://localhost:8081";
            e.printStackTrace();
        }
    }

    private final static String INDEX = HOST + "/v1",
            ALLERGIES = HOST + "/v1/allergies",
            CATEGORIES = HOST + "/v1/categories",
            CATEGORY = HOST + "/v1/categories/1",
            PRODUCTS_CATEGORY = HOST + "/v1/categories/1/products",
            PRODUCT = HOST + "/v1/categories/1/products/1",
            HOUSES = HOST + "/v1/houses",
            HOUSE = HOST + "/v1/houses/1",
            HOUSE_ALLERGIES = HOST + "/v1/houses/1/allergies",
            HOUSE_ALLERGY = HOST + "/v1/houses/1/allergies/lactose",
            STOCK_ITEMS_ALLERGEN = HOST + "/v1/houses/1/allergies/lactose/items",
            STOCK_ITEMS = HOST + "/v1/houses/1/items",
            STOCK_ITEM = HOST + "/v1/houses/1/items/C1P1-Mimosa-Magro-1l",
            ALLERGIES_STOCK_ITEM = HOST + "/v1/houses/1/items/C1P1-Mimosa-Magro-1l/allergies",
            LISTS = HOST + "/v1/houses/1/lists",
            LIST = HOST + "/v1/houses/1/lists/1",
            PRODUCTS_LIST = HOST + "/v1/houses/1/lists/1/products",
            PRODUCT_LIST = HOST + "/v1/houses/1/lists/1/products/1",
            MOVEMENTS = HOST + "/v1/houses/1/movements",
            STORAGES = HOST + "/v1/houses/1/storages",
            STORAGE = HOST + "/v1/houses/1/storages/1",
            HOUSEHOLD = HOST + "/v1/houses/1/users",
            HOUSE_INVITATIONS_TEMPLATE = HOST + "/v1/invitations/houses/{house-id}",
            INVITATION = HOST + "/v1/invitations/houses/1/users/test",
            USER_INVITATIONS_TEMPLATE = HOST + "/v1/invitations/users/{username}",
            USER_INVITATIONS = HOST + "/v1/invitations/users/test",
            USERS = HOST + "/v1/users",
            USERS_QUERY_STRING = HOST + "/v1/users?search={username}",
            USER_TEMPLATE = HOST + "/v1/users/{username}",
            USER = HOST + "/v1/users/test",
            USER_HOUSES_TEMPLATE = HOST + "/v1/users/{username}/houses",
            USER_HOUSES = HOST + "/v1/users/test/houses",
            USER_LISTS_TEMPLATE = HOST + "/v1/users/{username}/lists",
            USER_LISTS = HOST + "/v1/users/test/lists";

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
    public void test_build_houses_uri() {
        assertEquals(HOUSES, UriBuilderUtils.buildHousesUri());
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

    @Test
    public void test_build_house_invitations_uri_template() {
        assertEquals(HOUSE_INVITATIONS_TEMPLATE, UriBuilderUtils.buildHouseInvitationsUriTemplate());
    }

    @Test
    public void test_build_invitation_uri() {
        assertEquals(INVITATION, UriBuilderUtils.buildInvitationUri(1L, "test"));
    }

    @Test
    public void test_build_user_invitations_uri_template() {
        assertEquals(USER_INVITATIONS_TEMPLATE, UriBuilderUtils.buildUserInvitationsUriTemplate());
    }

    @Test
    public void test_build_user_invitations_uri() {
        assertEquals(USER_INVITATIONS, UriBuilderUtils.buildUserInvitationsUri("test"));
    }

    @Test
    public void test_build_users_uri() {
        assertEquals(USERS, UriBuilderUtils.buildUsersUri());
    }

    @Test
    public void test_build_users_uri_template() {
        assertEquals(USERS_QUERY_STRING, UriBuilderUtils.buildUsersUriTemplate());
    }

    @Test
    public void test_build_user_uri_template() {
        assertEquals(USER_TEMPLATE, UriBuilderUtils.buildUserUriTemplate());
    }

    @Test
    public void test_build_user_uri() {
        assertEquals(USER, UriBuilderUtils.buildUserUri("test"));
    }

    @Test
    public void test_build_user_houses_uri_template() {
        assertEquals(USER_HOUSES_TEMPLATE, UriBuilderUtils.buildUserHousesUriTemplate());
    }

    @Test
    public void test_build_user_houses_uri() {
        assertEquals(USER_HOUSES, UriBuilderUtils.buildUserHousesUri("test"));
    }

    @Test
    public void test_build_user_lists_uri_template() {
        assertEquals(USER_LISTS_TEMPLATE, UriBuilderUtils.buildUserListsUriTemplate());
    }

    @Test
    public void test_build_user_lists_uri() {
        assertEquals(USER_LISTS, UriBuilderUtils.buildUserListsUri("test"));
    }
}
