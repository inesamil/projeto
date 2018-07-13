package pt.isel.ps.gis.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import pt.isel.ps.gis.components.MessageSourceHolder;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.Numrange;

import java.sql.Date;
import java.util.Locale;

public class ValidationsUtils {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            House                                                           ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param houseId ID a validar
     * @throws EntityException se o ID não for válido
     */
    public static void validateHouseId(Long houseId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (houseId == null)
            throw new EntityException("House ID is required.", messageSource.getMessage("house_Id_Required", null, locale));
        if (houseId < RestrictionsUtils.HOUSE_ID_MIN)
            throw new EntityException("Invalid House ID.", messageSource.getMessage("house_Id_Invalid", null, locale));
    }

    /**
     * Valida nome
     *
     * @param houseName nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateHouseName(String houseName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (houseName == null)
            throw new EntityException("House name is required.", messageSource.getMessage("house_Name_Required", null, locale));
        if (houseName.length() > RestrictionsUtils.HOUSE_NAME_MAX_LENGTH)
            throw new EntityException("Invalid House name.", messageSource.getMessage("house_Name_Invalid", null, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Characteristics                                                 ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida as caraterísticas
     *
     * @param characteristics caraterísticas a validar
     * @throws EntityException se as caraterísticas forem inválidas
     */
    public static void validateCharacteristics(Characteristics characteristics) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (characteristics == null)
            throw new EntityException("House characteristics is required.", messageSource.getMessage("house_Characteristics_Required", null, locale));

        short babiesNumber = characteristics.getHouse_babiesNumber();
        short childrenNumber = characteristics.getHouse_childrenNumber();
        short adultsNumber = characteristics.getHouse_adultsNumber();
        short seniorsNumber = characteristics.getHouse_seniorsNumber();

        validateCharacteristicsBabiesNumber(babiesNumber);
        validateCharacteristicsChildrenNumber(childrenNumber);
        validateCharacteristicsAdultsNumber(adultsNumber);
        validateCharacteristicsSeniorsNumber(seniorsNumber);

        if (babiesNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN && childrenNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN
                && adultsNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN && seniorsNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN)
            throw new EntityException("Must exist at least one person in the house.", messageSource.getMessage("must_Exist_One_Person", null, locale));

        if (adultsNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN || seniorsNumber == RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN)
            throw new EntityException("Must exist at least one adult or senior in the house.", messageSource.getMessage("must_Exist_One_Adult", null, locale));
    }

    /**
     * Validar o número de bebés
     *
     * @param babiesNumber número a validar
     * @throws EntityException se o número for inválido
     */
    public static void validateCharacteristicsBabiesNumber(Short babiesNumber) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (babiesNumber == null)
            throw new EntityException("Babies number is required.", messageSource.getMessage("babies_Number_Required", null, locale));
        if (babiesNumber < RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN || babiesNumber > RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX)
            throw new EntityException(String.format("Invalid number of babies. The numbers supported are between [%d, %d].", RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX), messageSource.getMessage("invalid_Babies_Number", new Object[]{RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX}, locale));
    }

    /**
     * Validar o número de crianças
     *
     * @param childrenNumber número a validar
     * @throws EntityException se o número for inválido
     */
    public static void validateCharacteristicsChildrenNumber(Short childrenNumber) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (childrenNumber == null)
            throw new EntityException("Children number is required.", messageSource.getMessage("children_Number_Required", null, locale));
        if (childrenNumber < RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN || childrenNumber > RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX)
            throw new EntityException(String.format("Invalid number of children. The numbers supported are between [%d, %d].", RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX), messageSource.getMessage("invalid_Children_Number", new Object[]{RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX}, locale));
    }

    /**
     * Validar o número de adultos
     *
     * @param adultsNumber número a validar
     * @throws EntityException se o número for inválido
     */
    public static void validateCharacteristicsAdultsNumber(Short adultsNumber) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (adultsNumber == null)
            throw new EntityException("Adults number is required.", messageSource.getMessage("adults_Number_Required", null, locale));
        if (adultsNumber < RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN || adultsNumber > RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX)
            throw new EntityException(String.format("Invalid number of adults. The numbers supported are between [%d, %d].", RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX), messageSource.getMessage("invalid_Adults_Number", new Object[]{RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX}, locale));
    }

    /**
     * Validar o número de séniores
     *
     * @param seniorsNumber número a validar
     * @throws EntityException se o número for inválido
     */
    public static void validateCharacteristicsSeniorsNumber(Short seniorsNumber) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (seniorsNumber == null)
            throw new EntityException("Seniors number is required.", messageSource.getMessage("seniors_Number_Required", null, locale));
        if (seniorsNumber < RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN || seniorsNumber > RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX)
            throw new EntityException(String.format("Invalid number of seniors. The numbers supported are between [%d, %d].", RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX), messageSource.getMessage("invalid_Seniors_Number", new Object[]{RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MIN, RestrictionsUtils.CHARACTERISTICS_AGE_GROUP_MAX}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            User                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Validates the id
     *
     * @param id id to validate
     * @throws EntityException if id is not valid
     */
    public static void validateUserId(Long id) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (id == null)
            throw new EntityException("User id is required.", messageSource.getMessage("user_Required", null, locale));
        if (id < RestrictionsUtils.USER_ID_MIN)
            throw new EntityException("Invalid user id.", messageSource.getMessage("invalid_User_Id", null, locale));
    }

    /**
     * Valida username
     *
     * @param username username a validar
     * @throws EntityException se o username não for válido
     */
    public static void validateUserUsername(String username) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (username == null)
            throw new EntityException("Username is required.", messageSource.getMessage("username_Required", null, locale));
        if (username.length() > RestrictionsUtils.USER_USERNAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid Username. Username must contain a maximum of %d characters.", RestrictionsUtils.USER_USERNAME_MAX_LENGTH), messageSource.getMessage("invalid_Username", new Object[]{RestrictionsUtils.USER_USERNAME_MAX_LENGTH}, locale));
    }

    /**
     * Valida email
     *
     * @param email email a validar
     * @throws EntityException se o email não for válido
     */
    public static void validateUserEmail(String email) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (email == null)
            throw new EntityException("Email is required.", messageSource.getMessage("email_Required", null, locale));
        if (email.length() > RestrictionsUtils.USER_EMAIL_MAX_LENGTH)
            throw new EntityException("Invalid email.", messageSource.getMessage("invalid_Email", null, locale));
        if (!EmailUtils.isStringValidEmail(email))
            throw new EntityException("Invalid email.", messageSource.getMessage("invalid_Email", null, locale));
    }

    /**
     * Valida idade
     *
     * @param age idade a validar
     * @throws EntityException se a idade não for válida
     */
    public static void validateUserAge(Short age) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (age == null)
            throw new EntityException("Age is required.", messageSource.getMessage("age_Required", null, locale));
        if (age < RestrictionsUtils.USER_AGE_MIN || age > RestrictionsUtils.USER_AGE_MAX)
            throw new EntityException("Invalid age.", messageSource.getMessage("invalid_Age", null, locale));
    }

    /**
     * Valida nome
     *
     * @param name nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateUserName(String name) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (name == null)
            throw new EntityException("User's name is required.", messageSource.getMessage("user_Name_Required", null, locale));
        if (name.length() > RestrictionsUtils.USER_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid name. Name must contain a maximum of %d characters.", RestrictionsUtils.USER_NAME_MAX_LENGTH), messageSource.getMessage("invalid_User_Name", new Object[]{RestrictionsUtils.USER_NAME_MAX_LENGTH}, locale));
    }

    /**
     * Valida a password
     *
     * @param password password a validar
     * @throws EntityException se a password não for válida
     */
    public static void validateUserPassword(String password) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (password == null)
            throw new EntityException("Password is required.", messageSource.getMessage("password_Required", null, locale));
        if (password.length() > RestrictionsUtils.USER_PASSWORD_MAX_LENGTH)
            throw new EntityException(String.format("Password is too long. Password must contain a maximum of %d characters.", RestrictionsUtils.USER_PASSWORD_MAX_LENGTH), messageSource.getMessage("invalid_Password", new Object[]{RestrictionsUtils.USER_PASSWORD_MAX_LENGTH}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserHouse                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o administrador
     *
     * @param userhouseAdministrator booleano a validar
     * @throws EntityException se o administrador não for válido
     */
    public static void validateUserHouseAdministrator(Boolean userhouseAdministrator) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (userhouseAdministrator == null)
            throw new EntityException("Administrator is required.", messageSource.getMessage("administrator_Required", null, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Role                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Validates the id
     *
     * @param roleId id to validate
     * @throws EntityException if id is not valid
     */
    public static void validateRoleId(Short roleId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (roleId == null)
            throw new EntityException("Role id is required.", messageSource.getMessage("role_Required", null, locale));
        if (roleId < RestrictionsUtils.ROLE_ID_MIN)
            throw new EntityException("Invalid role id.", messageSource.getMessage("invalid_Role", null, locale));
    }

    /**
     * Validates the name
     *
     * @param roleName name to validate
     * @throws EntityException if name is not valid
     */
    public static void validateRoleName(String roleName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (roleName == null)
            throw new EntityException("Role name is required.", messageSource.getMessage("role_Name_Required", null, locale));
        if (roleName.length() > RestrictionsUtils.ROLE_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid role name. Role name must contain a maximum of %d characters.", RestrictionsUtils.ROLE_NAME_MAX_LENGTH), messageSource.getMessage("invalid_Role_Name", new Object[]{RestrictionsUtils.ROLE_NAME_MAX_LENGTH}, locale));

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Allergy                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o alergénio
     *
     * @param allergyAllergen alergénio a validar
     * @throws EntityException se o alergénio não for válido
     */
    public static void validateAllergyAllergen(String allergyAllergen) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (allergyAllergen == null)
            throw new EntityException("Allergen is required.", messageSource.getMessage("allergen_Required", null, locale));
        if (allergyAllergen.length() > RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH)
            throw new EntityException(String.format("Invalid allergen. Allergen must contain a maximum of %d characters.", RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH), messageSource.getMessage("invalid_Allergen", new Object[]{RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH}, locale));
    }

    /**
     * Valida o número de alérgicos
     *
     * @param alergicsNum número a validar
     * @throws EntityException se o número de alérgicos não for válido
     */
    public static void validateHouseAllergyAllergicsNum(Short alergicsNum) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (alergicsNum == null)
            throw new EntityException("Allergics number is required.", messageSource.getMessage("allergics_Number_Required", null, locale));
        if (alergicsNum < RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN)
            throw new EntityException(String.format("Invalid allergic numbers. The number of allergic members must be greater or equal to %d.", RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN), messageSource.getMessage("invalid_Allergics_Number", new Object[]{RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            List                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param listId ID a validar
     * @throws EntityException se o ID não for válido
     */
    public static void validateListId(Short listId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (listId == null)
            throw new EntityException("List ID is required", messageSource.getMessage("list_Id_Required", null, locale));
        if (listId < RestrictionsUtils.LIST_ID_MIN)
            throw new EntityException(String.format("Invalid List ID. List ID must be greater or equal to %d.", RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN), messageSource.getMessage("invalid_List_Id", new Object[]{RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN}, locale));
    }

    /**
     * Valida se o nome é uma string dentro dos limites aceites
     *
     * @param listName nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateListName(String listName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (listName == null)
            throw new EntityException("List name is required.", messageSource.getMessage("list_Name_Required", null, locale));
        if (listName.length() > RestrictionsUtils.LIST_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid List name. Name must contain a maximum of %d characters.", RestrictionsUtils.LIST_NAME_MAX_LENGTH), messageSource.getMessage("invalid_List_Name", new Object[]{RestrictionsUtils.LIST_NAME_MAX_LENGTH}, locale));
    }

    /**
     * Valida se o tipo da lista é um dos valores possíveis
     *
     * @param listType tipo a validar
     * @throws EntityException se o tipo não for válido
     */
    public static void validateListType(String listType) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (listType == null)
            throw new EntityException("List type is required.", messageSource.getMessage("list_Type_Required", null, locale));
        if (listType.length() > RestrictionsUtils.LIST_TYPE_MAX_LENGTH)
            throw new EntityException(String.format("Invalid list type. Type must be in [%s].", RestrictionsUtils.LIST_TYPE.getAllTypes()), messageSource.getMessage("invalid_List_Type", new Object[]{RestrictionsUtils.LIST_TYPE.getAllTypes()}, locale));
        for (RestrictionsUtils.LIST_TYPE type : RestrictionsUtils.LIST_TYPE.values()) {
            if (listType.compareToIgnoreCase(type.name()) == 0)
                return;
        }
        throw new EntityException(String.format("Invalid list type. Type must be in [%s].", RestrictionsUtils.LIST_TYPE.getAllTypes()), messageSource.getMessage("invalid_List_Type", new Object[]{RestrictionsUtils.LIST_TYPE.getAllTypes()}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Category                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param categoryId ID a validar
     * @throws EntityException se o ID não for válido
     */
    public static void validateCategoryId(Integer categoryId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (categoryId == null)
            throw new EntityException("Category ID is required.", messageSource.getMessage("category_Id_Required", null, locale));
        if (categoryId < RestrictionsUtils.CATEGORY_ID_MIN)
            throw new EntityException(String.format("Invalid Category ID. Category ID must be greater or equal to %d.", RestrictionsUtils.CATEGORY_ID_MIN), messageSource.getMessage("invalid_Category_Id", new Object[]{RestrictionsUtils.CATEGORY_ID_MIN}, locale));
    }

    /**
     * Valida se o nome da categoria é uma string dentro dos limites aceites
     *
     * @param categoryName nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateCategoryName(String categoryName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (categoryName == null)
            throw new EntityException("Category name is required.", messageSource.getMessage("category_Name_Required", null, locale));
        if (categoryName.length() > RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid Category name. Name must contain a maximum of %d characters.", RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH), messageSource.getMessage("invalid_Category_Name", new Object[]{RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Product                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param productId ID a validar
     * @throws EntityException se o ID não for válido
     */
    public static void validateProductId(Integer productId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (productId == null)
            throw new EntityException("Product ID is required.", messageSource.getMessage("product_Id_Required", null, locale));
        if (productId < RestrictionsUtils.PRODUCT_ID_MIN)
            throw new EntityException(String.format("Invalid Product ID. Product ID must be greater or equal to %d.", RestrictionsUtils.PRODUCT_ID_MIN), messageSource.getMessage("invalid_Product_Id", new Object[]{RestrictionsUtils.PRODUCT_ID_MIN}, locale));
    }

    /**
     * Valida se o nome do produto é uma string dentro dos limites aceites
     *
     * @param productName nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateProductName(String productName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (productName == null)
            throw new EntityException("Product name is required.", messageSource.getMessage("product_Name_Required", null, locale));
        if (productName.length() > RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid Product name. Name must contain a maximum of %d characters.", RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH), messageSource.getMessage("invalid_Product_Name", new Object[]{RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH}, locale));
    }

    /**
     * Valida se foi atrbuido valor ao booleano
     *
     * @param productEdible booleano a validar
     * @throws EntityException se o booleano for NULL
     */
    public static void validateProductEdible(Boolean productEdible) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (productEdible == null)
            throw new EntityException("Product edibility is required.", messageSource.getMessage("product_Edibility_Required", null, locale));
    }

    /**
     * Valida se o tempo de vida está dentro dos limites aceites
     *
     * @param productShelflife tempo de vida a validar
     * @throws EntityException se o tempo de vida não for válido
     */
    public static void validateProductShelflife(Short productShelflife) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (productShelflife == null)
            throw new EntityException("Product shelflife is required.", messageSource.getMessage("product_Shelflife_Required", null, locale));
        if (productShelflife < RestrictionsUtils.PRODUCT_SHELFLIFE_MIN)
            throw new EntityException(String.format("Invalid Product shelflife. Shelflife must be greater or equal to %d.", RestrictionsUtils.PRODUCT_SHELFLIFE_MIN), messageSource.getMessage("invalid_Product_Shelflife", new Object[]{RestrictionsUtils.PRODUCT_SHELFLIFE_MIN}, locale));
    }

    /**
     * Valida se as unidades do tempo de vida tem um dos valores possíveis
     *
     * @param shelflifeTimeUnit unidade de tempo a validar
     * @throws EntityException se as unidades de tempo não forem válidas
     */
    public static void validateProductShelflifeTimeunit(String shelflifeTimeUnit) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (shelflifeTimeUnit == null)
            throw new EntityException("Product shelflife timeunit is required.", messageSource.getMessage("product_Shelflife_Timeunit_Required", null, locale));
        if (shelflifeTimeUnit.length() > RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT_MAX_LENGTH)
            throw new EntityException(String.format("Invalid product shelflife. Type must be in [%s].", RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()), messageSource.getMessage("invalid_Product_Shelflife_Timeunit", new Object[]{RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()}, locale));
        for (RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT shelflifetimeunit : RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.values()) {
            if (shelflifeTimeUnit.compareToIgnoreCase(shelflifetimeunit.name()) == 0)
                return;
        }
        throw new EntityException(String.format("Invalid product shelflife. Type must be in [%s].", RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()), messageSource.getMessage("invalid_Product_Shelflife_Timeunit", new Object[]{RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Date                                                            ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida a data
     *
     * @param date data a validar
     * @throws EntityException se a data não for válida
     */
    public static void validateDate(String date) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (date == null)
            throw new EntityException("Date is required.", messageSource.getMessage("date_Required", null, locale));
        if (!DateUtils.isStringValidDate(date))
            throw new EntityException("Invalid date.", messageSource.getMessage("invalid_Date", null, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Storage                                                         ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o ID
     *
     * @param storageId ID a validar
     * @throws EntityException se o ID não for válido
     */
    public static void validateStorageId(Short storageId) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (storageId == null)
            throw new EntityException("Storage ID is required.", messageSource.getMessage("storage_Id_Required", null, locale));
        if (storageId < RestrictionsUtils.STORAGE_ID_MIN)
            throw new EntityException(String.format("Invalid Storage ID. Storage ID must be greater or equal to %d.", RestrictionsUtils.STORAGE_ID_MIN), messageSource.getMessage("invalid_Storage_Id", new Object[]{RestrictionsUtils.STORAGE_ID_MIN}, locale));
    }

    /**
     * Valida se o nome do local de armazenamento é uma string dentro dos limites aceites
     *
     * @param storageName nome a validar
     * @throws EntityException se o nome não for válido
     */
    public static void validateStorageName(String storageName) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (storageName == null)
            throw new EntityException("Storage name is required.", messageSource.getMessage("storage_Name_Required", null, locale));
        if (storageName.length() > RestrictionsUtils.STORAGE_NAME_MAX_LENGTH)
            throw new EntityException(String.format("Invalid Storage name. Name must contain a maximum of %d characters.", RestrictionsUtils.STORAGE_NAME_MAX_LENGTH), messageSource.getMessage("invalid_Storage_Name", new Object[]{RestrictionsUtils.STORAGE_NAME_MAX_LENGTH}, locale));
    }

    /**
     * Valida a temperatura
     *
     * @param storageTemperature temperatura a validar
     * @throws EntityException se a temperatura não for válida
     */
    public static void validateStorageTemperature(Numrange storageTemperature) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (storageTemperature == null)
            throw new EntityException("Temperature is required.", messageSource.getMessage("temperature_Required", null, locale));
    }

    /**
     * Valida intervalo de temperaturas
     *
     * @param minimum temperatura mínima
     * @param maximum temperatura máxima
     * @throws EntityException se a temperatura não for válida
     */
    public static void validateRangeTemperature(Float minimum, Float maximum) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (minimum == null || maximum == null)
            throw new EntityException("Both temperature maximum and minimum are required.", messageSource.getMessage("maximum_Minimum_Temperature_Required", null, locale));
        if (minimum > maximum)
            throw new EntityException("Minimum temperature must be smaller than maximum temperature.", messageSource.getMessage("minimum_Smaller_Maximum_Temperature", null, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItem                                                       ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se o SKU é uma string dentro dos limites aceites
     *
     * @param sku sku a validar
     * @throws EntityException se o SKU não for válido
     */
    public static void validateStockItemSku(String sku) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (sku == null)
            throw new EntityException("Stock item SKU is required.", messageSource.getMessage("stockItem_Sku_Required", null, locale));
        if (sku.length() > RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH)
            throw new EntityException(String.format("Invalid stock item SKU. SKU must contain a maximum of %d characters.", RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH), messageSource.getMessage("invalid_StockItem_Sku", new Object[]{RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH}, locale));
    }

    /**
     * Valida se a marca do Item é uma string dentro dos limites aceites
     *
     * @param stockitemBrand marca a validar
     * @throws EntityException se a marca não for válida
     */
    public static void validateStockItemBrand(String stockitemBrand) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemBrand == null)
            throw new EntityException("Stock item brand is required.", messageSource.getMessage("stockItem_Brand_Required", null, locale));
        if (stockitemBrand.length() > RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH)
            throw new EntityException(String.format("Invalid stock item brand. Brand must contain a maximum of %d characters.", RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH), messageSource.getMessage("invalid_StockItem_Brand", new Object[]{RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH}, locale));
    }

    /**
     * Valida se o segmento tem um valor dentro dos limites aceites
     *
     * @param stockitemSegment valor a validar
     * @throws EntityException se o segmento não for válido
     */
    public static void validateStockItemSegment(Float stockitemSegment) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemSegment == null)
            throw new EntityException("Stock item segment is required.", messageSource.getMessage("stockItem_Segment_Required", null, locale));
        if (stockitemSegment < RestrictionsUtils.STOCKITEM_SEGMENT_MIN)
            throw new EntityException(String.format("Invalid stock item segment. Segment must be greater or equal to %d.", RestrictionsUtils.STOCKITEM_SEGMENT_MIN), messageSource.getMessage("invalid_StockItem_Segment", new Object[]{RestrictionsUtils.STOCKITEM_SEGMENT_MIN}, locale));
    }

    /**
     * Valida se a unidade do segemento é um dos valores aceites
     *
     * @param segmentUnit valor a validar
     * @throws EntityException se a unidade do segmento não for válida
     */
    public static void validateStockItemSegmentUnit(String segmentUnit) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (segmentUnit == null)
            throw new EntityException("Stock item segment unit is required.", messageSource.getMessage("stockItem_Segment_Unit_Required", null, locale));
        if (segmentUnit.length() > RestrictionsUtils.STOCKITEM_SEGMENTUNIT_MAX_LENGTH)
            throw new EntityException(String.format("Invalid segment unit. Type must be in [%s].", RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()), messageSource.getMessage("invalid_StockItem_Segment_Unit", new Object[]{RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()}, locale));
        for (RestrictionsUtils.STOCKITEM_SEGMENTUNIT unit : RestrictionsUtils.STOCKITEM_SEGMENTUNIT.values()) {
            if (segmentUnit.compareToIgnoreCase(unit.toString()) == 0)
                return;
        }
        throw new EntityException(String.format("Invalid segment unit. Type must be in [%s].", RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()), messageSource.getMessage("invalid_StockItem_Segment_Unit", new Object[]{RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()}, locale));
    }

    /**
     * Valida se a variedade é uma string dentro dos limites aceites
     *
     * @param stockitemVariety string a validar
     * @throws EntityException se a variedade não for válida
     */
    public static void validateStockItemVariety(String stockitemVariety) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemVariety == null)
            throw new EntityException("Stock item variety is required.", messageSource.getMessage("stockItem_Variety_Required", null, locale));
        if (stockitemVariety.length() > RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH)
            throw new EntityException(String.format("Invalid stock item variety. Variety must contain a maximum of %d characters.", RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH), messageSource.getMessage("invalid_StockItem_Variety", new Object[]{RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH}, locale));
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param stockitemQuantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateStockItemQuantity(Short stockitemQuantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemQuantity == null)
            throw new EntityException("Stock item quantity is required.", messageSource.getMessage("stockItem_Quantity_Required", null, locale));
        if (stockitemQuantity < RestrictionsUtils.STOCKITEM_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid stock item quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.STOCKITEM_QUANTITY_MIN), messageSource.getMessage("invalid_StockItem_Quantity", new Object[]{RestrictionsUtils.STOCKITEM_QUANTITY_MIN}, locale));
    }

    public static void validateStockItemDescription(String stockitemDescription) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemDescription != null && stockitemDescription.length() > RestrictionsUtils.STOCKITEM_DESCRIPTION_MAX_LENGTH)
            throw new EntityException("Description is too long.", messageSource.getMessage("description_To_Long", null, locale));

    }

    /**
     * Valida se o local de conservação é uma string dentro dos limites
     *
     * @param stockitemConservationstorage string a validar
     * @throws EntityException se o local de armazenamento não for válido
     */
    public static void validateStockItemConservationStorage(String stockitemConservationstorage) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (stockitemConservationstorage == null)
            throw new EntityException("Stock item conservation storage is required.", messageSource.getMessage("stockItem_Storage_Required", null, locale));
        if (stockitemConservationstorage.length() > RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH)
            throw new EntityException(String.format("Invalid stock item conservation storage. Conservation storage must contain a maximum of %d characters.", RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH), messageSource.getMessage("invalid_StockItem_Storage", new Object[]{RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItemMovement                                               ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida o datetime
     *
     * @param datetime datetime a validar
     * @throws EntityException se o datetime não for válido
     */
    public static void validateStockItemMovementDateTime(String datetime) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (datetime == null)
            throw new EntityException("Stock item movement DateTime is required.", messageSource.getMessage("movement_Datetime_Required", null, locale));
        if (!DateUtils.isStringValidDateTime(datetime))
            throw new EntityException("Invalid stock item movement DateTime. The format is: \"yyyy-MM-dd HH:mm:ss\".", messageSource.getMessage("invalid_Movement_Datetime", null, locale));
    }

    /**
     * Valida o tipo do movimento
     *
     * @param type tipo a validar
     * @throws EntityException se o tipo do movimento não for válido
     */
    public static void validateStockItemMovementType(Boolean type) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (type == null)
            throw new EntityException("Movement type is required.", messageSource.getMessage("movement_Type_Required", null, locale));
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateStockItemMovementQuantity(Short quantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (quantity == null)
            throw new EntityException("Movement quantity is required.", messageSource.getMessage("movement_Quantity_Required", null, locale));
        if (quantity < RestrictionsUtils.MOVEMENT_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid Movement quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.MOVEMENT_QUANTITY_MIN), messageSource.getMessage("invalid_Movement_Quantity", new Object[]{RestrictionsUtils.MOVEMENT_QUANTITY_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            DailyQuantity                                                   ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateDailyQuantityQuantity(Short quantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (quantity == null)
            throw new EntityException("Quantity is required.", messageSource.getMessage("movement_Quantity_Required", null, locale));
        if (quantity < RestrictionsUtils.MOVEMENT_FINAL_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid daily quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.MOVEMENT_FINAL_QUANTITY_MIN), messageSource.getMessage("invalid_Movement_Quantity", new Object[]{RestrictionsUtils.MOVEMENT_QUANTITY_MIN}, locale));
    }

    /**
     * Valida a data
     *
     * @param date data a validar
     * @throws EntityException se a data não for válida
     */
    public static void validateDailyQuantityDate(Date date) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (date == null)
            throw new EntityException("Date is required.", messageSource.getMessage("date_Required", null, locale));
        if (!DateUtils.isStringValidDate(date.toString()))
            throw new EntityException("Invalid date.", messageSource.getMessage("invalid_Date", null, locale));
    }

    public static void validateStockItemMovementFinalQuantity(Short finalQuantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (finalQuantity == null)
            throw new EntityException("Movement final quantity is required.", messageSource.getMessage("movement_Final_Quantity_Required", null, locale));
        if (finalQuantity < RestrictionsUtils.MOVEMENT_FINAL_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid movement final quantity. Final quantity must be greater or equal to %d.", RestrictionsUtils.MOVEMENT_FINAL_QUANTITY_MIN), messageSource.getMessage("invalid_Movement_Final_Quantity", new Object[]{RestrictionsUtils.MOVEMENT_FINAL_QUANTITY_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ExpirationDate                                                  ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateExpirationDateQuantity(Short quantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (quantity == null)
            throw new EntityException("Expiration Date quantity is required.", messageSource.getMessage("expirationDate_Quantity_Required", null, locale));
        if (quantity < RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid Expiration Date quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN), messageSource.getMessage("invalid_ExpirationDate_Quantity", new Object[]{RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            ListProduct                                                     ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a marca no caso de existir está dentro dos limites aceites
     *
     * @param brand marca a validar
     * @throws EntityException se a marca não for válida
     */
    public static void validateListProductBrand(String brand) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (brand != null && brand.length() > RestrictionsUtils.LISTPRODUCT_BRAND_MAX_LENGTH)
            throw new EntityException("Invalid brand.", messageSource.getMessage("invalid_Brand", null, locale));
    }

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateListProductQuantity(Short quantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (quantity == null)
            throw new EntityException("Quantity is required.", messageSource.getMessage("quantity_Required", null, locale));
        if (quantity < RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN), messageSource.getMessage("invalid_Quantity", new Object[]{RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            StockItemStorage                                                ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se a quantidade está dentro dos limites aceites
     *
     * @param quantity quantidade a validar
     * @throws EntityException se a quantidade não for válida
     */
    public static void validateStockItemStorageQuantity(Short quantity) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (quantity == null)
            throw new EntityException("Quantity is required.", messageSource.getMessage("quantity_Required", null, locale));
        if (quantity < RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN)
            throw new EntityException(String.format("Invalid quantity. Quantity must be greater or equal to %d.", RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN), messageSource.getMessage("invalid_Quantity", new Object[]{RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN}, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            UserList                                                        ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida se o booleano não é NULL
     *
     * @param shareable booleno a validar
     * @throws EntityException se o booleano não for válido
     */
    public static void validateListShareable(Boolean shareable) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (shareable == null)
            throw new EntityException("Shareable is required.", messageSource.getMessage("shareable_Required", null, locale));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////                                            Invitation                                                      ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void validateInvitationAccepted(Boolean invitationAccepted) throws EntityException {
        MessageSource messageSource = MessageSourceHolder.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (invitationAccepted == null)
            throw new EntityException("Invitation accepted is required.", messageSource.getMessage("invition_Accepted_Required", null, locale));
    }
}
