package pt.isel.ps.gis.utils;

public class UriBuilderUtils {

    private static final String
            VERSION = "v1",
            ALLERGIES = "allergies",
            CATEGORIES = "categories",
            HOUSES = "houses",
            ITEMS = "items",
            LISTS = "lists",
            MOVEMENTS = "movements",
            PRODUCTS = "products",
            STORAGES = "storages",
            USERS = "users";

    /**
     * URI Template: v1
     *
     * @return URI to index page
     */
    public static String buildIndexUri() {
        return VERSION;
    }

    /**
     * URI Template: v1/allergies
     *
     * @return URI to allergies
     */
    public static String buildAllergiesUri() {
        String uriTemplate = "%s/%s";
        return String.format(uriTemplate, VERSION, ALLERGIES);
    }

    /**
     * URI Template: v1/categories
     *
     * @return URI to all categories
     */
    public static String buildCategoriesUri() {
        String uriTemplate = "%s/%s";
        return String.format(uriTemplate, VERSION, CATEGORIES);
    }

    /**
     * URI Template: v1/categories/{category-id}
     *
     * @param categoryId The id of the category
     * @return URI to specific category
     */
    public static String buildCategoryUri(int categoryId) {
        String uriTemplate = "%s/%s/%d";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId);
    }

    /**
     * URI Template: v1/categories/{category-id}/products
     *
     * @param categoryId The id of the category
     * @return URI to products of the category
     */
    public static String buildProductsCategoryUri(int categoryId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId, PRODUCTS);
    }

    /**
     * URI Template: v1/categories/{category-id}/products/{product-id}
     *
     * @param categoryId The id of the category
     * @param productId  The id of the product
     * @return URI to specific product of the category
     */
    public static String buildProductUri(int categoryId, int productId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, CATEGORIES, categoryId, PRODUCTS, productId);
    }

    /**
     * URI Template: v1/houses/{house-id}
     *
     * @param houseId The id of the house
     * @return URI for a specific house
     */
    public static String buildHouseUri(long houseId) {
        String uriTemplate = "%s/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId);
    }

    /**
     * URI Template: v1/houses/{house-id}/allergies
     *
     * @param houseId The id of the house
     * @return URI to allergies of the house
     */
    public static String buildHouseAllergiesUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ALLERGIES);
    }

    /**
     * URI Template: v1/houses/{house-id}/allergies/{allergen}
     *
     * @param houseId  The id of the house
     * @param allergen The id of the allergy
     * @return URI to specific allergy of the house
     */
    public static String buildHouseAllergyUri(long houseId, String allergen) {
        String uriTemplate = "%s/%s/%d/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ALLERGIES, allergen);
    }

    /**
     * URI Template: v1/houses/{house-id}/allergies/{allergen}/items
     *
     * @param houseId  The id of the house
     * @param allergen The id of the allergy
     * @return URI to items with specific allergy of the house
     */
    public static String buildStockItemsAllergenUri(long houseId, String allergen) {
        String uriTemplate = "%s/%s/%d/%s/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ALLERGIES, allergen, ITEMS);
    }

    /**
     * URI Template: v1/houses/{house-id}/items
     *
     * @param houseId The id of the house
     * @return URI to items of the house
     */
    public static String buildStockItemsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS);
    }

    /**
     * URI Template: v1/houses/{house-id}/items/{stock-item-id}
     *
     * @param houseId The id of the house
     * @param sku     The id of the stock item
     * @return URI to specific stock item of the house
     */
    public static String buildStockItemUri(long houseId, String sku) {
        String uriTemplate = "%s/%s/%d/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS, sku);
    }

    /**
     * URI Template: v1/houses/{house-id}/items/{stock-item-id}/allergies
     *
     * @param houseId The id of the house
     * @param sku     The id of the stock item
     * @return URI to allergies in stock item of the house
     */
    public static String buildAllergiesStockItemUri(long houseId, String sku) {
        String uriTemplate = "%s/%s/%d/%s/%s/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, ITEMS, sku, ALLERGIES);
    }

    /**
     * URI Template: v1/houses/{house-id}/lists
     *
     * @param houseId The id of the house
     * @return URI to lists of the house
     */
    public static String buildListsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS);
    }

    /**
     * URI Template: v1/houses/{house-id}/lists/{list-id}
     *
     * @param houseId The id of the house
     * @param listId  The id of the list
     * @return URI to specific list of the house
     */
    public static String buildListUri(Long houseId, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS, listId);
    }

    /**
     * URI Template: v1/houses/{house-id}/lists/{list-id}/products
     *
     * @param houseId The id of the house
     * @param listId  The id of the list
     * @return URI to products in specific list of the house
     */
    public static String buildProductsListUri(long houseId, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS, listId, PRODUCTS);
    }

    /**
     * URI Template: v1/houses/{house-id}/lists/{list-id}/products/{product-id}
     *
     * @param houseId   The id of the house
     * @param listId    The id of the list
     * @param productId The id of the product
     * @return URI to specific product in specific list of the house
     */
    public static String buildProductListUri(long houseId, short listId, int productId) {
        String uriTemplate = "%s/%s/%d/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, LISTS, listId, PRODUCTS, productId);
    }

    /**
     * URI Template: v1/houses/{house-id}/movements
     *
     * @param houseId The id of the house
     * @return URI for a movements of the house
     */
    public static String buildMovementsUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, MOVEMENTS);
    }

    /**
     * URI Template: v1/houses/{house-id}/storages
     *
     * @param houseId The id of the house
     * @return URI to storages of the house
     */
    public static String buildStoragesUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, STORAGES);
    }

    /**
     * URI Template: v1/houses/{house-id}/storages/{storage-id}
     *
     * @param houseId   The id of the house
     * @param storageId The id of the storage
     * @return URI to specific storage of the house
     */
    public static String buildStorageUri(Long houseId, Short storageId) {
        String uriTemplate = "%s/%s/%d/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, STORAGES, storageId);
    }

    /**
     * URI Template: v1/houses/{house-id}/users
     *
     * @param houseId The id of the house
     * @return URI to household
     */
    public static String buildHouseholdUri(long houseId) {
        String uriTemplate = "%s/%s/%d/%s";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, USERS);
    }

    /**
     * URI Template: v1/houses/{house-id}/users/{username}/lists/{list-id}
     *
     * @param houseId  The id of the house
     * @param username The id of the user
     * @param listId   The id of the list
     * @return URI to specific list of specific user of the house
     */
    public static String buildUserListUri(Long houseId, String username, Short listId) {
        String uriTemplate = "%s/%s/%d/%s/%s/%s/%d";
        return String.format(uriTemplate, VERSION, HOUSES, houseId, USERS, username, LISTS, listId);
    }

    /**
     * URI Template: v1/users/{username}
     *
     * @param username The id of the user
     * @return The specific user
     */
    public static String buildUserUri(String username) {
        String uriTemplate = "%s/%s/%s";
        return String.format(uriTemplate, VERSION, USERS, username);
    }

    /**
     * URI Template: v1/users/{username}/houses
     *
     * @param username The id of the user
     * @return URI to houses of the user
     */
    public static String buildUserHousesUri(String username) {
        String uriTemplate = "%s/%s/%s/%s";
        return String.format(uriTemplate, VERSION, USERS, username, HOUSES);
    }
}
