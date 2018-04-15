package pt.isel.ps.gis.utils;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.model.Numrange;

import java.io.Serializable;

public class ValidationsUtils {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            House                                                           ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param houseId ID a validar
     * @throws ModelException se o ID não for válido
     */
    public static void validateHouseId(Long houseId) throws ModelException {
        if (houseId == null)
            throw new ModelException("House ID is required.");
        if (houseId < RestrictionsUtils.HOUSE_ID_MIN)
            throw new ModelException("Invalid House ID.");
    }

    /**
     * Valida nome
     *
     * @param houseName nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateHouseName(String houseName) throws ModelException {
        if (houseName == null)
            throw new ModelException("House name is required.");
        if (houseName.length() > RestrictionsUtils.HOUSE_NAME_MAX_LENGTH)
            throw new ModelException("Invalid House name.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            User                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida username
     *
     * @param username username a validar
     * @throws ModelException se o username não for válido
     */
    public static void validateUserUsername(String username) throws ModelException {
        if (username == null)
            throw new ModelException("Username is required.");
        if (username.length() > RestrictionsUtils.USER_USERNAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid Username. Username must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_USERNAME_MAX_LENGTH));
    }

    /**
     * Valida email
     *
     * @param email email a validar
     * @throws ModelException se o email não for válido
     */
    public static void validateUserEmail(String email) throws ModelException {
        if (email == null)
            throw new ModelException("Email is required.");
        if (email.length() > RestrictionsUtils.USER_EMAIL_MAX_LENGTH)
            throw new ModelException("Invalid email.");
        if (!EmailUtils.isStringValidEmail(email))
            throw new ModelException("Invalid email.");
    }

    /**
     * Valida idade
     *
     * @param age idade a validar
     * @throws ModelException se a idade não for válida
     */
    public static void validateUserAge(Short age) throws ModelException {
        if (age == null)
            throw new ModelException("Age is required.");
        if (age < RestrictionsUtils.USER_AGE_MIN || age > RestrictionsUtils.USER_AGE_MAX)
            throw new ModelException("Invalid age.");
    }

    /**
     * Valida nome
     *
     * @param name nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateUserName(String name) throws ModelException {
        if (name == null)
            throw new ModelException("User's name is required.");
        if (name.length() > RestrictionsUtils.USER_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_NAME_MAX_LENGTH));
    }

    /**
     * Valida a password
     *
     * @param password password a validar
     * @throws ModelException se a password não for válida
     */
    public static void validateUserPassword(String password) throws ModelException {
        if (password == null)
            throw new ModelException("Password is required.");
        if (password.length() > RestrictionsUtils.USER_PASSWORD)
            throw new ModelException(String.format("Password is too long. Password must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_PASSWORD));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserHouse                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o administrador
     *
     * @param userhouseAdministrator booleano a validar
     * @throws ModelException se o administrador não for válido
     */
    public static void validateUserHouseAdministrator(Boolean userhouseAdministrator) throws ModelException {
        if (userhouseAdministrator == null)
            throw new ModelException("Administrator is required.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Allergy                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o alergénio
     *
     * @param allergyAllergen alergénio a validar
     * @throws ModelException se o alergénio não for válido
     */
    public static void validateAllergyAllergen(String allergyAllergen) throws ModelException {
        if (allergyAllergen == null)
            throw new ModelException("Allergen is required.");
        if (allergyAllergen.length() > RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH)
            throw new ModelException(String.format("Invalid allergen. Allergen must contain a maximum of %d characters.",
                    RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH));
    }

    /**
     * Valida o número de alérgicos
     *
     * @param alergicsNum número a validar
     * @throws ModelException se o número de alérgicos não for válido
     */
    public static void validateHouseAllergyAllergicsNum(Short alergicsNum) throws ModelException {
        if (alergicsNum == null)
            throw new ModelException("Allergics number is required.");
        if (alergicsNum < RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN)
            throw new ModelException(String.format("Invalid allergen. The number of allergic members must be greater or equal to %d.",
                    RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            List                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param listId ID a validar
     * @throws ModelException se o ID não for válido
     */
    public static void validateListId(Short listId) throws ModelException {
        if (listId == null)
            throw new ModelException("List ID is required.");
        if (listId < RestrictionsUtils.LIST_ID_MIN)
            throw new ModelException(String.format("Invalid List ID. List ID must be greater or equal to %d.",
                    RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN));
    }

    /**
     * Valida se o nome é uma string dentro dos limites aceites
     *
     * @param listName nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateListName(String listName) throws ModelException {
        if (listName == null)
            throw new ModelException("List name is required.");
        if (listName.length() > RestrictionsUtils.LIST_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid List name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.LIST_NAME_MAX_LENGTH));
    }

    /**
     * Valida se o tipo da lista é um dos valores possíveis
     *
     * @param listType tipo a validar
     * @throws ModelException se o tipo não for válido
     */
    public static void validateListType(String listType) throws ModelException {
        if (listType == null)
            throw new ModelException("List type is required.");
        if (listType.length() > RestrictionsUtils.LIST_TYPE_MAX_LENGTH)
            throw new ModelException(String.format("Invalid list type. Type must be in [%s].", RestrictionsUtils.LIST_TYPE.getAllTypes()));
        for (RestrictionsUtils.LIST_TYPE type : RestrictionsUtils.LIST_TYPE.values()) {
            if (listType.compareToIgnoreCase(type.name()) == 0)
                return;
        }
        throw new ModelException(String.format("Invalid list type. Type must be in [%s].", RestrictionsUtils.LIST_TYPE.getAllTypes()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Category                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param categoryId ID a validar
     * @throws ModelException se o ID não for válido
     */
    public static void validateCategoryId(Integer categoryId) throws ModelException {
        if (categoryId == null)
            throw new ModelException("Category ID is required.");
        if (categoryId < RestrictionsUtils.CATEGORY_ID_MIN)
            throw new ModelException(String.format("Invalid Category ID. Category ID must be greater or equal to %d.",
                    RestrictionsUtils.CATEGORY_ID_MIN));
    }

    /**
     * Valida se o nome da categoria é uma string dentro dos limites aceites
     *
     * @param categoryName nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateCategoryName(String categoryName) throws ModelException {
        if (categoryName == null)
            throw new ModelException("Category name is required.");
        if (categoryName.length() > RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid Category name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Product                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param productId ID a validar
     * @throws ModelException se o ID não for válido
     */
    public static void validateProductId(Integer productId) throws ModelException {
        if (productId == null)
            throw new ModelException("Product ID is required.");
        if (productId < RestrictionsUtils.PRODUCT_ID_MIN)
            throw new ModelException(String.format("Invalid Product ID. Product ID must be greater or equal to %d.",
                    RestrictionsUtils.PRODUCT_ID_MIN));
    }

    /**
     * Valida se o nome do produto é uma string dentro dos limites aceites
     *
     * @param productName nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateProductName(String productName) throws ModelException {
        if (productName == null)
            throw new ModelException("Product name is required.");
        if (productName.length() > RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid Product name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH));
    }

    /**
     * Valida se foi atrbuido valor ao booleano
     *
     * @param productEdible booleano a validar
     * @throws ModelException se o booleano for NULL
     */
    public static void validateProductEdible(Boolean productEdible) throws ModelException {
        if (productEdible == null)
            throw new ModelException("Product edibility is required.");
    }

    /**
     * Valida se o tempo de vida está dentro dos limites aceites
     *
     * @param productShelflife tempo de vida a validar
     * @throws ModelException se o tempo de vida não for válido
     */
    public static void validateProductShelflife(Short productShelflife) throws ModelException {
        if (productShelflife == null)
            throw new ModelException("Product shelflife is required.");
        if (productShelflife < RestrictionsUtils.PRODUCT_SHELFLIFE_MIN)
            throw new ModelException(String.format("Invalid Product shelflife. Shelflife must be greater or equal to %d.",
                    RestrictionsUtils.PRODUCT_SHELFLIFE_MIN));
    }

    /**
     * Valida se as unidades do tempo de vida tem um dos valores possíveis
     *
     * @param shelflifeTimeUnit unidade de tempo a validar
     * @throws ModelException se as unidades de tempo não forem válidas
     */
    public static void validateProductShelflifeTimeunit(String shelflifeTimeUnit) throws ModelException {
        if (shelflifeTimeUnit == null)
            throw new ModelException("Product shelflife timeunit is required.");
        if (shelflifeTimeUnit.length() > RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT_MAX_LENGTH)
            throw new ModelException(String.format("Invalid product shelflife. Type must be in [%s].", RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()));
        for (RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT shelflifetimeunit : RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.values()) {
            if (shelflifeTimeUnit.compareToIgnoreCase(shelflifetimeunit.name()) == 0)
                return;
        }
        throw new ModelException(String.format("Invalid product shelflife. Type must be in [%s].", RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Date                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida a data
     *
     * @param date data a validar
     * @throws ModelException se a data não for válida
     */
    public static void validateDate(String date) throws ModelException {
        if (date == null || !DateUtils.isStringValidDate(date))
            throw new ModelException("Invalid date.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Storage                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param storageId ID a validar
     * @throws ModelException se o ID não for válido
     */
    public static void validateStorageId(Short storageId) throws ModelException {
        if (storageId == null)
            throw new ModelException("Storage ID is required.");
        if (storageId < RestrictionsUtils.STORAGE_ID_MIN)
            throw new ModelException(String.format("Invalid Storage ID. Storage ID must be greater or equal to %d.",
                    RestrictionsUtils.STORAGE_ID_MIN));
    }

    /**
     * Valida se o nome do local de armazenamento é uma string dentro dos limites aceites
     *
     * @param storageName nome a validar
     * @throws ModelException se o nome não for válido
     */
    public static void validateStorageName(String storageName) throws ModelException {
        if (storageName == null)
            throw new ModelException("Storage name is required.");
        if (storageName.length() > RestrictionsUtils.STORAGE_NAME_MAX_LENGTH)
            throw new ModelException(String.format("Invalid Storage name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.STORAGE_NAME_MAX_LENGTH));
    }

    /**
     * Valida a temperatura
     *
     * @param storageTemperature temperatura a validar
     * @throws ModelException se a temperatura não for válida
     */
    public static void validateStorageTemperature(Numrange storageTemperature) throws ModelException {
        if (storageTemperature == null)
            throw new ModelException("Temperature is required.");
        //TODO: validations
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItem                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se o SKU é uma string dentro dos limites aceites
     *
     * @param sku sku a validar
     * @throws ModelException se o SKU não for válido
     */
    public static void validateStockItemSku(String sku) throws ModelException {
        if (sku == null)
            throw new ModelException("Stock item SKU is required.");
        if (sku.length() > RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item SKU. SKU must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH));
    }

    /**
     * Valida se a marca do Item é uma string dentro dos limites aceites
     *
     * @param stockitemBrand marca a validar
     * @throws ModelException se a marca não for válida
     */
    public static void validateStockItemBrand(String stockitemBrand) throws ModelException {
        if (stockitemBrand == null)
            throw new ModelException("Stock item brand is required.");
        if (stockitemBrand.length() > RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item brand. Brand must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH));
    }

    /**
     * Valida se o segmento tem um valor dentro dos limites aceites
     *
     * @param stockitemSegment valor a validar
     * @throws ModelException se o segmento não for válido
     */
    public static void validateStockItemSegment(Float stockitemSegment) throws ModelException {
        if (stockitemSegment == null)
            throw new ModelException("Stock item segment is required.");
        if (stockitemSegment < RestrictionsUtils.STOCKITEM_SEGMENT_MIN)
            throw new ModelException(String.format("Invalid stock item segment. Segment must be greater or equal to %f.",
                    RestrictionsUtils.STOCKITEM_SEGMENT_MIN));
    }

    /**
     * Valida se a unidade do segemento é um dos valores aceites
     *
     * @param segmentUnit valor a validar
     * @throws ModelException se a unidade do segmento não for válida
     */
    public static void validateStockItemSegmentUnit(String segmentUnit) throws ModelException {
        if (segmentUnit == null)
            throw new ModelException("Stock item segment unit is required.");
        if (segmentUnit.length() > RestrictionsUtils.STOCKITEM_SEGMENTUNIT_MAX_LENGTH)
            throw new ModelException(String.format("Invalid segment unit. Type must be in [%s].", RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()));
        for (RestrictionsUtils.STOCKITEM_SEGMENTUNIT unit : RestrictionsUtils.STOCKITEM_SEGMENTUNIT.values()) {
            if (segmentUnit.compareToIgnoreCase(unit.toString()) == 0)
                return;
        }
        throw new ModelException(String.format("Invalid segment unit. Type must be in [%s].", RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()));
    }

    /**
     * Valida se a variedade é uma string dentro dos limites aceites
     *
     * @param stockitemVariety string a validar
     * @throws ModelException se a variedade não for válida
     */
    public static void validateStockItemVariety(String stockitemVariety) throws ModelException {
        if (stockitemVariety == null)
            throw new ModelException("Stock item variety is required.");
        if (stockitemVariety.length() > RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item variety. Variety must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH));
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param stockitemQuantity quantidade a validar
     * @throws ModelException se a quantidade não for válida
     */
    public static void validateStockItemQuantity(Short stockitemQuantity) throws ModelException {
        if (stockitemQuantity == null)
            throw new ModelException("Stock item quantity is required.");
        if (stockitemQuantity < RestrictionsUtils.STOCKITEM_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid stock item quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.STOCKITEM_QUANTITY_MIN));
    }

    public static void validateStockItemDescription(String stockitemDescription) throws ModelException {
        if (stockitemDescription != null && stockitemDescription.length() > RestrictionsUtils.STOCKITEM_DESCRIPTION_MAX_LENGTH)
            throw new ModelException("Description is too long.");

    }

    /**
     * Valida se o local de conservação é uma string dentro dos limites
     *
     * @param stockitemConservationstorage string a validar
     * @throws ModelException se o local de armazenamento não for válido
     */
    public static void validateStockItemConservationStorage(String stockitemConservationstorage) throws ModelException {
        if (stockitemConservationstorage == null)
            throw new ModelException("Stock item conservation storage is required.");
        if (stockitemConservationstorage.length() < RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH)
            throw new ModelException(String.format("Invalid stock item conservation storage. Conservation storage must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItemMovement                                               ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o datetime
     *
     * @param datetime datetime a validar
     * @throws ModelException se o datetime não for válido
     */
    public static void validateStockItemMovementDateTime(String datetime) throws ModelException {
        if (datetime == null)
            throw new ModelException("Stock item movement DateTime is required.");
        if (!DateUtils.isStringValidDateTime(datetime))
            throw new ModelException("Invalid stock item movement DateTime. The format is: \"yyyy-MM-dd HH:mm:ss\".");
    }

    /**
     * Valida o tipo do movimento
     *
     * @param type tipo a validar
     * @throws ModelException se o tipo do movimento não for válido
     */
    public static void validateStockItemMovementType(Boolean type) throws ModelException {
        if (type == null)
            throw new ModelException("Movement type is required.");
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws ModelException se a quantidade não for válida
     */
    public static void validateStockItemMovementQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Movement quantity is required.");
        if (quantity < RestrictionsUtils.MOVEMENT_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid Movement quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.MOVEMENT_QUANTITY_MIN));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ExpirationDate                                                  ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws ModelException se a quantidade não for válida
     */
    public static void validateExpirationDateQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Expiration Date quantity is required.");
        if (quantity < RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid Expiration Date quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ListProduct                                                     ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a marca no caso de existir está dentro dos limites aceites
     *
     * @param brand marca a validar
     * @throws ModelException se a marca não for válida
     */
    public static void validateListProductBrand(String brand) throws ModelException {
        if (brand != null && brand.length() > RestrictionsUtils.LISTPRODUCT_BRAND_MAX_LENGTH)
            throw new ModelException("Invalid brand.");
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws ModelException se a quantidade não for válida
     */
    public static void validateListProductQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Quantity is required.");
        if (quantity < RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItemStorage                                                ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws ModelException se a quantidade não for válida
     */
    public static void validateStockItemStorageQuantity(Short quantity) throws ModelException {
        if (quantity == null)
            throw new ModelException("Quantity is required.");
        if (quantity < RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN)
            throw new ModelException(String.format("Invalid quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserList                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se o booleano não é NULL
     *
     * @param shareable booleno a validar
     * @throws ModelException se o booleano não for válido
     */
    public static void validateListShareable(Boolean shareable) throws ModelException {
        if (shareable == null)
            throw new ModelException("Shareable is required.");
    }
}
