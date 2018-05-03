package pt.isel.ps.gis.utils;

public class UriBuilderUtils {

    private static final String
            VERSION = "v1",
            HOUSES = "houses",
            MOVEMENTS = "movements",
            ITEMS = "items",
            USERS = "users",
            ALLERGIES = "allergies",
            LISTS = "lists",
            STORAGES = "storages",
            CATEGORIES = "categories",
            PRODUCTS = "products";

    /**
     * URI Template: v1/houses/{id}
     *
     * @param houseId The id of the house
     * @return URI for a specific house resource
     */
    public static String buildHouseUri(long houseId) {
        String uriTemplate = "%s/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId);
    }

    /**
     * URI Template: v1/houses/{id}/movements
     *
     * @param houseId The id of the house
     * @return URI for a movements resource
     */
    public static String buildMovementsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, MOVEMENTS);
    }

    public static String buildIndexUri() {
        return VERSION;
    }

    public static String buildItemsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS);
    }

    public static String buildHouseholdUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, USERS);
    }

    public static String buildHouseAllergiesUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ALLERGIES);
    }

    public static String buildListsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS);
    }

    public static String buildStoragesUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, STORAGES);
    }

    public static String buildCategoriesUri() {
        String uriTemplate = "%s/%s";
        return String.format(uriTemplate, VERSION, CATEGORIES);
    }

    public static String buildCategoryUri(int categoryId) {
        String uriTemplate = "%s/%s/%d";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId);
    }

    public static String buildProductsCategoryUri(int categoryId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId, PRODUCTS);
    }

    public static String buildProductUri(int categoryId, int productId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId, PRODUCTS, productId);
    }

    public static String buildStockItemsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS);
    }

    public static String buildStockItemUri(long houseId, String sku) {
        String uriTemplate = "%s/%s/%d/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS, sku);
    }

    public static String buildAllergiesItemUri(long houseId, String sku) {
        String uriTemplate = "%s/%s/%d/%s/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS, sku, ALLERGIES);
    }

    public static String buildUserHousesUri(String username) {
        String uriTemplate = "%s/%s/%s/%s";
        return String.format(uriTemplate, VERSION, USERS, username, HOUSES);
    }

    public static String buildUserUri(String username) {
        String uriTemplate = "%s/%s/%s";
        return String.format(uriTemplate, VERSION, USERS, username);
    }

    public static String buildStorageUri(Long houseId, Short storageId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, STORAGES, storageId);
    }

    public static String buildAllergyUri(String allergen) {
        String uriTemplate = "%s/%s/%s";
        return String.format(uriTemplate, VERSION, ALLERGIES, allergen);
    }

    public static String buildProductsListUri(long houseId, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS, listId, PRODUCTS);
    }

    public static String buildListUri(Long houseId, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS, listId);
    }

    public static String buildMovementUri(long houseId, short storageId, String sku, String datetime, boolean type) {
        String uriTemplate = "%s/%s/%d/%s/%d/%s/%s/%b";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, MOVEMENTS, storageId, sku, datetime, type);
    }

    public static String buildUserListUri(Long houseId, String username, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%s/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, USERS, username, LISTS, listId);
    }

    public static String buildAllergiesUri() {
        String uriTemplate = "%s/%s";
        return String.format(uriTemplate, VERSION, ALLERGIES);
    }
}
