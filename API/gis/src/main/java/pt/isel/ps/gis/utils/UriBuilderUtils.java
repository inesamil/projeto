package pt.isel.ps.gis.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UriBuilderUtils {

    private static String HOST;

    static {
        /*try {
            HOST = String.format("http://%s:8081", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            HOST = "http://localhost:8081";
            e.printStackTrace();
        }*/
        HOST = "http://192.168.1.76:8081";
        //HOST = "http://10.10.4.173:8081";
    }

    private static final String
            VERSION = "v1",
            ALLERGIES = "allergies",
            CATEGORIES = "categories",
            HOUSES = "houses",
            ITEMS = "items",
            LISTS = "lists",
            INVITATIONS = "invitations",
            MOVEMENTS = "movements",
            PRODUCTS = "products",
            STORAGES = "storages",
            USERS = "users";

    /**
     * URI Template: http://10.0.2.2:8081/v1
     *
     * @return URI to index page
     */
    public static String buildIndexUri() {
        return String.format("%s/%s", HOST, VERSION);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/allergies
     *
     * @return URI to allergies
     */
    public static String buildAllergiesUri() {
        return String.format("%s/%s/%s", HOST, VERSION, ALLERGIES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/categories
     *
     * @return URI to all categories
     */
    public static String buildCategoriesUri() {
        return String.format("%s/%s/%s", HOST, VERSION, CATEGORIES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/categories/{category-id}
     *
     * @param categoryId The id of the category
     * @return URI to specific category
     */
    public static String buildCategoryUri(int categoryId) {
        return String.format("%s/%s/%s/%d", HOST, VERSION, CATEGORIES, categoryId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/categories/{category-id}/products
     *
     * @param categoryId The id of the category
     * @return URI to products of the category
     */
    public static String buildProductsCategoryUri(int categoryId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, CATEGORIES, categoryId, PRODUCTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/categories/{category-id}/products/{product-id}
     *
     * @param categoryId The id of the category
     * @param productId  The id of the product
     * @return URI to specific product of the category
     */
    public static String buildProductUri(int categoryId, int productId) {
        return String.format("%s/%s/%s/%d/%s/%d", HOST, VERSION, CATEGORIES, categoryId, PRODUCTS, productId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses
     *
     * @return URI to houses
     */
    public static String buildHousesUri() {
        return String.format("%s/%s/%s", HOST, VERSION, HOUSES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}
     *
     * @param houseId The id of the house
     * @return URI for a specific house
     */
    public static String buildHouseUri(long houseId) {
        return String.format("%s/%s/%s/%d", HOST, VERSION, HOUSES, houseId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/allergies
     *
     * @param houseId The id of the house
     * @return URI to allergies of the house
     */
    public static String buildHouseAllergiesUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, ALLERGIES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/allergies/{allergen}
     *
     * @param houseId  The id of the house
     * @param allergen The id of the allergy
     * @return URI to specific allergy of the house
     */
    public static String buildHouseAllergyUri(long houseId, String allergen) {
        return String.format("%s/%s/%s/%d/%s/%s", HOST, VERSION, HOUSES, houseId, ALLERGIES, allergen);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/allergies/{allergen}/items
     *
     * @param houseId  The id of the house
     * @param allergen The id of the allergy
     * @return URI to items with specific allergy of the house
     */
    public static String buildStockItemsAllergenUri(long houseId, String allergen) {
        return String.format("%s/%s/%s/%d/%s/%s/%s", HOST, VERSION, HOUSES, houseId, ALLERGIES, allergen, ITEMS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/items
     *
     * @param houseId The id of the house
     * @return URI to items of the house
     */
    public static String buildStockItemsUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, ITEMS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/items/{stock-item-id}
     *
     * @param houseId The id of the house
     * @param sku     The id of the stock item
     * @return URI to specific stock item of the house
     */
    public static String buildStockItemUri(long houseId, String sku) {
        return String.format("%s/%s/%s/%d/%s/%s", HOST, VERSION, HOUSES, houseId, ITEMS, sku);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/items/{stock-item-id}/allergies
     *
     * @param houseId The id of the house
     * @param sku     The id of the stock item
     * @return URI to allergies in stock item of the house
     */
    public static String buildAllergiesStockItemUri(long houseId, String sku) {
        return String.format("%s/%s/%s/%d/%s/%s/%s", HOST, VERSION, HOUSES, houseId, ITEMS, sku, ALLERGIES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/lists
     *
     * @param houseId The id of the house
     * @return URI to lists of the house
     */
    public static String buildListsUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, LISTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/lists/{list-id}
     *
     * @param houseId The id of the house
     * @param listId  The id of the list
     * @return URI to specific list of the house
     */
    public static String buildListUri(Long houseId, Short listId) {
        return String.format("%s/%s/%s/%d/%s/%d", HOST, VERSION, HOUSES, houseId, LISTS, listId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/lists/{list-id}/products
     *
     * @param houseId The id of the house
     * @param listId  The id of the list
     * @return URI to products in specific list of the house
     */
    public static String buildListProductstUri(long houseId, Short listId) {
        return String.format("%s/%s/%s/%d/%s/%d/%s", HOST, VERSION, HOUSES, houseId, LISTS, listId, PRODUCTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/lists/{list-id}/products/{product-id}
     *
     * @param houseId   The id of the house
     * @param listId    The id of the list
     * @param productId The id of the product
     * @return URI to specific product in specific list of the house
     */
    public static String buildListProductUri(long houseId, short listId, int productId) {
        return String.format("%s/%s/%s/%d/%s/%d/%s/%d", HOST, VERSION, HOUSES, houseId, LISTS, listId, PRODUCTS, productId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/movements
     *
     * @param houseId The id of the house
     * @return URI for a movements of the house
     */
    public static String buildMovementsUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, MOVEMENTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/storages
     *
     * @param houseId The id of the house
     * @return URI to storages of the house
     */
    public static String buildStoragesUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, STORAGES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/storages/{storage-id}
     *
     * @param houseId   The id of the house
     * @param storageId The id of the storage
     * @return URI to specific storage of the house
     */
    public static String buildStorageUri(Long houseId, Short storageId) {
        return String.format("%s/%s/%s/%d/%s/%d", HOST, VERSION, HOUSES, houseId, STORAGES, storageId);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/houses/{house-id}/users
     *
     * @param houseId The id of the house
     * @return URI to household
     */
    public static String buildHouseholdUri(long houseId) {
        return String.format("%s/%s/%s/%d/%s", HOST, VERSION, HOUSES, houseId, USERS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}
     *
     * @param username The id of the user
     * @return The specific user
     */
    public static String buildUserUri(String username) {
        return String.format("%s/%s/%s/%s", HOST, VERSION, USERS, username);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users
     *
     * @return The specific user
     */
    public static String buildUsersUri() {
        return String.format("%s/%s/%s", HOST, VERSION, USERS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}/houses
     *
     * @param username The id of the user
     * @return URI to houses of the user
     */
    public static String buildUserHousesUri(String username) {
        return String.format("%s/%s/%s/%s/%s", HOST, VERSION, USERS, username, HOUSES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}/lists
     *
     * @param username The id of the user
     * @return URI to user lists
     */
    public static String buildUserListsUri(String username) {
        return String.format("%s/%s/%s/%s/%s", HOST, VERSION, USERS, username, LISTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}
     *
     * @return URI template to user
     */
    public static String buildUserUriTemplate() {
        return String.format("%s/%s/%s/{username}", HOST, VERSION, USERS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}/lists
     *
     * @return URI template to user lists
     */
    public static String buildUserListsUriTemplate() {
        return String.format("%s/%s/%s/{username}/%s", HOST, VERSION, USERS, LISTS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}/houses
     *
     * @return URI template to user houses
     */
    public static String buildUserHousesUriTemplate() {
        return String.format("%s/%s/%s/{username}/%s", HOST, VERSION, USERS, HOUSES);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/users/{username}/invitations
     *
     * @return URI template to user lists
     */
    public static String buildUserInvitationsUriTemplate() {
        return String.format("%s/%s/%s/{username}/%s", HOST, VERSION, USERS, INVITATIONS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/invitations
     *
     * @return URI template invitations
     */
    public static String buildInvitationsUri() {
        return String.format("%s/%s/%s", HOST, VERSION, INVITATIONS);
    }

    /**
     * URI Template: http://10.0.2.2:8081/v1/invitations/houses/{house-id}/users/{username}
     *
     * @return URI to an invitation
     */
    public static String buildInvitationUri(Long houseId, String username) {
        return String.format("%s/%s/%s/%s/%d/%s/%s", HOST, VERSION, INVITATIONS, HOUSES, houseId, USERS, username);
    }
}
