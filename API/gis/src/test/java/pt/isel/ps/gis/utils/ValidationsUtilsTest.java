package pt.isel.ps.gis.utils;

import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.Numrange;

import static org.junit.Assert.*;

public class ValidationsUtilsTest {

    @Test
    public void validateHouseId() {
        Long nullId = null;
        Long invalidId = RestrictionsUtils.HOUSE_ID_MIN - 1;
        Long validId = RestrictionsUtils.HOUSE_ID_MIN;

        // Testar ID null
        try {
            ValidationsUtils.validateHouseId(nullId);
            fail("No exception thrown when validating null ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "House ID is required.");
        }

        // Testar ID inválido
        try {
            ValidationsUtils.validateHouseId(invalidId);
            fail("No exception thrown when validating invalid ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "Invalid House ID.");
        }

        // Testar ID válido
        try {
            ValidationsUtils.validateHouseId(validId);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateHouseName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.HOUSE_NAME_MAX_LENGTH + 1);
        String validName = "Smith's House";

        // Testar nome null
        try {
            ValidationsUtils.validateHouseName(nullName);
            fail("No exception thrown when validating null name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "House name is required.");
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateHouseName(invalidName);
            fail("No exception thrown when validating invalid name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(e.getMessage(), "Invalid House name.");
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateHouseName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateCharacteristics() {
        Characteristics nullCharacteristics = null;
        Characteristics invalidCharacteristics_emptyHouse = null;
        Characteristics invalidCharacteristics_noAdultsNorSeniors = null;
        Characteristics validCharacteristics = null;

        // Setup das características a testar
        try {
            invalidCharacteristics_emptyHouse = new Characteristics((short)0, (short)0, (short)0, (short)0);
            invalidCharacteristics_noAdultsNorSeniors = new Characteristics((short)0, (short)2, (short)0, (short)0);
            validCharacteristics = new Characteristics((short)0, (short)2, (short)2, (short)1);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }

        // Testar caraterísticas null
        try {
            ValidationsUtils.validateCharacteristics(nullCharacteristics);
            fail("No exception thrown when validating null characteristics. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("House characteristics is required.", e.getMessage());
        }

        // Testar caraterísticas inválidas (Casa Vazia)
        try {
            ValidationsUtils.validateCharacteristics(invalidCharacteristics_emptyHouse);
            fail("No exception thrown when validating invalid characteristics (empty house). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Must exist at least one person in the house.", e.getMessage());
        }

        // Testar caraterísticas inválidas (Casa sem pelo menos um adulto ou sénior)
        try {
            ValidationsUtils.validateCharacteristics(invalidCharacteristics_noAdultsNorSeniors);
            fail("No exception thrown when validating invalid characteristics (no adults nor seniors). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Must exist at least one adult or senior in the house.", e.getMessage());
        }

        // Testar caraterísticas válidas
        try {
            ValidationsUtils.validateCharacteristics(validCharacteristics);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserUsername() {
        String nullUsername = null;
        String invalidUsername = RandomString.make(RestrictionsUtils.USER_USERNAME_MAX_LENGTH + 1);
        String validUsername = RandomString.make(RestrictionsUtils.USER_USERNAME_MAX_LENGTH);

        // Testar username null
        try {
            ValidationsUtils.validateUserUsername(nullUsername);
            fail("No exception thrown when validating null username. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Username is required.", e.getMessage());
        }

        // Testar username inválido
        try {
            ValidationsUtils.validateUserUsername(invalidUsername);
            fail("No exception thrown when validating invalid username. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Username. Username must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_USERNAME_MAX_LENGTH), e.getMessage());
        }

        // Testar username válido
        try {
            ValidationsUtils.validateUserUsername(validUsername);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserEmail() {
        String nullEmail = null;
        String invalidEmailTooLong = RandomString.make(RestrictionsUtils.USER_EMAIL_MAX_LENGTH + 1);
        String invalidEmail = "@licesmiththecoolgirl!@@some123email?invalidemailformat&.com";
        String validEmail = "alicesmith@someemail.com";

        // Testar email null
        try {
            ValidationsUtils.validateUserEmail(nullEmail);
            fail("No exception thrown when validating null email. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Email is required.", e.getMessage());
        }

        // Testar email inválido (limite excedido)
        try {
            ValidationsUtils.validateUserEmail(invalidEmailTooLong);
            fail("No exception thrown when validating invalid email (too long). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Invalid email.", e.getMessage());
        }

        // Testar email inválido (formato inválido)
        try {
            ValidationsUtils.validateUserEmail(invalidEmail);
            fail("No exception thrown when validating invalid email (wrong format). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Invalid email.", e.getMessage());
        }

        // Testar email válido
        try {
            ValidationsUtils.validateUserEmail(validEmail);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserAge() {
        Short nullAge = null;
        Short invalidAgeUnderMinLimit = RestrictionsUtils.USER_AGE_MIN - 1;
        Short invalidAgeAboveMaxLimit = RestrictionsUtils.USER_AGE_MAX + 1;
        Short validAge = (RestrictionsUtils.USER_AGE_MIN + RestrictionsUtils.USER_AGE_MAX) / 2;

        // Testar idade null
        try {
            ValidationsUtils.validateUserAge(nullAge);
            fail("No exception thrown when validating null age. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Age is required.", e.getMessage());
        }

        // Testar idade inválida (abaixo limite mínimo)
        try {
            ValidationsUtils.validateUserAge(invalidAgeUnderMinLimit);
            fail("No exception thrown when validating invalid age (under min limit). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Invalid age.", e.getMessage());
        }

        // Testar idade inválida (acima limite máximo)
        try {
            ValidationsUtils.validateUserAge(invalidAgeAboveMaxLimit);
            fail("No exception thrown when validating invalid age (above max limit). Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Invalid age.", e.getMessage());
        }

        // Testar idade válida
        try {
            ValidationsUtils.validateUserAge(validAge);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.USER_NAME_MAX_LENGTH + 1);
        String validName = "Alice Smith";

        // Testar nome null
        try {
            ValidationsUtils.validateUserName(nullName);
            fail("No exception thrown when validating null name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("User's name is required.", e.getMessage());
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateUserName(invalidName);
            fail("No exception thrown when validating invalid name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_NAME_MAX_LENGTH), e.getMessage());
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateUserName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserPassword() {
        String nullPassword = null;
        String invalidPassword = RandomString.make(RestrictionsUtils.USER_PASSWORD_MAX_LENGTH + 1);
        String validPassword = RandomString.make(RestrictionsUtils.USER_PASSWORD_MAX_LENGTH / 2);

        // Testar password null
        try {
            ValidationsUtils.validateUserPassword(nullPassword);
            fail("No exception thrown when validating null password. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Password is required.", e.getMessage());
        }

        // Testar password inválida
        try {
            ValidationsUtils.validateUserPassword(invalidPassword);
            fail("No exception thrown when validating invalid password. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Password is too long. Password must contain a maximum of %d characters.",
                    RestrictionsUtils.USER_PASSWORD_MAX_LENGTH), e.getMessage());
        }

        // Testar password válida
        try {
            ValidationsUtils.validateUserPassword(validPassword);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateUserHouseAdministrator() {
        Boolean nullAdministrator = null;
        Boolean validAdministratorFalse = false;
        Boolean validAdministratorTrue = false;

        // Testar administrador null
        try {
            ValidationsUtils.validateUserHouseAdministrator(nullAdministrator);
            fail("No exception thrown when validating administrator. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Administrator is required.", e.getMessage());
        }

        // Testar administrador válido (false)
        try {
            ValidationsUtils.validateUserHouseAdministrator(validAdministratorFalse);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }

        // Testar administrador válido (true)
        try {
            ValidationsUtils.validateUserHouseAdministrator(validAdministratorTrue);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateAllergyAllergen() {
        String nullAllergen = null;
        String invalidAllergen = RandomString.make(RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH + 1);
        String validAllergen = RandomString.make(RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH / 2);

        // Testar alergénio null
        try {
            ValidationsUtils.validateAllergyAllergen(nullAllergen);
            fail("No exception thrown when validating null allergen. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Allergen is required.", e.getMessage());
        }

        // Testar alergénio inválido
        try {
            ValidationsUtils.validateAllergyAllergen(invalidAllergen);
            fail("No exception thrown when validating invalid allergen. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid allergen. Allergen must contain a maximum of %d characters.",
                    RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH), e.getMessage());
        }

        // Testar alergénio válido
        try {
            ValidationsUtils.validateAllergyAllergen(validAllergen);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateHouseAllergyAllergicsNum() {
        Short nullNum = null;
        Short invalidNum = RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN - 1;
        Short validNum = RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN;

        // Testar número de alérgicos null
        try {
            ValidationsUtils.validateHouseAllergyAllergicsNum(nullNum);
            fail("No exception thrown when validating null number of allergics. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Allergics number is required.", e.getMessage());
        }

        // Testar número de alérgicos inválido
        try {
            ValidationsUtils.validateHouseAllergyAllergicsNum(invalidNum);
            fail("No exception thrown when validating invalid number of allergics. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid allergen. The number of allergic members must be greater or equal to %d.",
                    RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN), e.getMessage());
        }

        // Testar número de alérgicos válido
        try {
            ValidationsUtils.validateHouseAllergyAllergicsNum(validNum);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListId() {
        Short nullId = null;
        Short invalidId = RestrictionsUtils.LIST_ID_MIN - 1;
        Short validId = RestrictionsUtils.LIST_ID_MIN;

        // Testar ID null
        try {
            ValidationsUtils.validateListId(nullId);
            fail("No exception thrown when validating null ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("List ID is required.", e.getMessage());
        }

        // Testar ID inválido
        try {
            ValidationsUtils.validateListId(invalidId);
            fail("No exception thrown when validating invalid ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid List ID. List ID must be greater or equal to %d.",
                    RestrictionsUtils.HOUSEALLERGY_ALERGICSNUM_MIN), e.getMessage());
        }

        // Testar ID válido
        try {
            ValidationsUtils.validateListId(validId);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.LIST_NAME_MAX_LENGTH + 1);
        String validName = RandomString.make(RestrictionsUtils.LIST_NAME_MAX_LENGTH / 2);

        // Testar nome null
        try {
            ValidationsUtils.validateListName(nullName);
            fail("No exception thrown when validating null name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("List name is required.", e.getMessage());
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateListName(invalidName);
            fail("No exception thrown when validating invalid name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid List name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.LIST_NAME_MAX_LENGTH), e.getMessage());
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateListName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListType() {
        String nullType = null;
        String invalidType = "sistema";
        String validType = RestrictionsUtils.LIST_TYPE.system.name();

        // Testar tipo null
        try {
            ValidationsUtils.validateListType(nullType);
            fail("No exception thrown when validating null type. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("List type is required.", e.getMessage());
        }

        // Testar tipo inválido
        try {
            ValidationsUtils.validateListType(invalidType);
            fail("No exception thrown when validating invalid type. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid list type. Type must be in [%s].", RestrictionsUtils.LIST_TYPE.getAllTypes()), e.getMessage());
        }

        // Testar tipo válido
        try {
            ValidationsUtils.validateListType(validType);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateCategoryId() {
        Integer nullId = null;
        Integer invalidId = RestrictionsUtils.CATEGORY_ID_MIN - 1;
        Integer validId = RestrictionsUtils.CATEGORY_ID_MIN;

        // Testar ID null
        try {
            ValidationsUtils.validateCategoryId(nullId);
            fail("No exception thrown when validating null ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Category ID is required.", e.getMessage());
        }

        // Testar ID inválido
        try {
            ValidationsUtils.validateCategoryId(invalidId);
            fail("No exception thrown when validating invalid ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Category ID. Category ID must be greater or equal to %d.",
                    RestrictionsUtils.CATEGORY_ID_MIN), e.getMessage());
        }

        // Testar ID válido
        try {
            ValidationsUtils.validateCategoryId(validId);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateCategoryName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH + 1);
        String validName = RandomString.make(RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH / 2);

        // Testar nome null
        try {
            ValidationsUtils.validateCategoryName(nullName);
            fail("No exception thrown when validating null name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Category name is required.", e.getMessage());
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateCategoryName(invalidName);
            fail("No exception thrown when validating invalid name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Category name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH), e.getMessage());
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateCategoryName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateProductId() {
        Integer nullId = null;
        Integer invalidId = RestrictionsUtils.PRODUCT_ID_MIN - 1;
        Integer validId = RestrictionsUtils.PRODUCT_ID_MIN;

        // Testar ID null
        try {
            ValidationsUtils.validateProductId(nullId);
            fail("No exception thrown when validating null ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Product ID is required.", e.getMessage());
        }

        // Testar ID inválido
        try {
            ValidationsUtils.validateProductId(invalidId);
            fail("No exception thrown when validating invalid ID. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Product ID. Product ID must be greater or equal to %d.",
                    RestrictionsUtils.PRODUCT_ID_MIN), e.getMessage());
        }

        // Testar ID válido
        try {
            ValidationsUtils.validateProductId(validId);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateProductName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH + 1);
        String validName = RandomString.make(RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH / 2);

        // Testar nome null
        try {
            ValidationsUtils.validateProductName(nullName);
            fail("No exception thrown when validating null name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Product name is required.", e.getMessage());
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateProductName(invalidName);
            fail("No exception thrown when validating invalid name. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Product name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH), e.getMessage());
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateProductName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateProductEdible() {
        Boolean nullEdible = null;
        Boolean validEdibleFalse = false;
        Boolean validEdibleTrue = false;

        // Testar atributo comestível null
        try {
            ValidationsUtils.validateProductEdible(nullEdible);
            fail("No exception thrown when validating edible. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Product edibility is required.", e.getMessage());
        }

        // Testar atributo comestível válido (false)
        try {
            ValidationsUtils.validateProductEdible(validEdibleFalse);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }

        // Testar atributo comestível válido (true)
        try {
            ValidationsUtils.validateProductEdible(validEdibleTrue);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateProductShelflife() {
        Short nullShelflife = null;
        Short invalidShelflife = RestrictionsUtils.PRODUCT_SHELFLIFE_MIN - 1;
        Short validShelflife = RestrictionsUtils.PRODUCT_SHELFLIFE_MIN;

        // Testar tempo médio de vida null
        try {
            ValidationsUtils.validateProductShelflife(nullShelflife);
            fail("No exception thrown when validating null shelflife. Expected EntityException");
        } catch (EntityException e) {
            assertEquals("Product shelflife is required.", e.getMessage());
        }

        // Testar tempo médio de vida inválido
        try {
            ValidationsUtils.validateProductShelflife(invalidShelflife);
            fail("No exception thrown when validating invalid shelflife. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Product shelflife. Shelflife must be greater or equal to %d.",
                    RestrictionsUtils.PRODUCT_SHELFLIFE_MIN), e.getMessage());
        }

        // Testar tempo médio de vida válido
        try {
            ValidationsUtils.validateProductShelflife(validShelflife);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateProductShelflifeTimeunit() {
        String nullShelflifeTimeunit = null;
        String invalideShelflifeTimeunit = "dia";
        String validShelfllifeTimeunit = RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.day.name();

        // Testar unidade do tempo médio de vida null
        try {
            ValidationsUtils.validateProductShelflifeTimeunit(nullShelflifeTimeunit);
            fail("No exception thrown when validating null shelflife timeunit. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Product shelflife timeunit is required.", e.getMessage());
        }

        // Testar unidade do tempo médio de vida inválida
        try {
            ValidationsUtils.validateProductShelflifeTimeunit(invalideShelflifeTimeunit);
            fail("No exception thrown when validating invalid shelflife timeunit. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals(String.format("Invalid product shelflife. Type must be in [%s].", RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT.getAllUnits()), e.getMessage());
        }

        // Testar unidade do tempo médio de vida válida
        try {
            ValidationsUtils.validateProductShelflifeTimeunit(validShelfllifeTimeunit);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateDate() {
        String nullDate = null;
        String invalidDate = "2018/05/20";
        String validDate = "2018-05-20";

        // Testar data null
        try {
            ValidationsUtils.validateDate(nullDate);
            fail("No exception thrown when  validating null date. Expected " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Date is required.", e.getMessage());
        }

        // Testar data inválida
        try {
            ValidationsUtils.validateDate(invalidDate);
            fail("No exception thrown when validating invalid date. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Invalid date.", e.getMessage());
        }

        // Testar data válida
        try {
            ValidationsUtils.validateDate(validDate);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStorageId() {
        Short nullId = null;
        Short invalidId = RestrictionsUtils.STORAGE_ID_MIN - 1;
        Short validId = RestrictionsUtils.STORAGE_ID_MIN;

        // Testar ID null
        try {
            ValidationsUtils.validateStorageId(nullId);
            fail("No exception thrown when validating null ID. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Storage ID is required.", e.getMessage());
        }

        // Testar ID inválido
        try {
            ValidationsUtils.validateStorageId(invalidId);
            fail("No exception thrown when validating invalid ID. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Storage ID. Storage ID must be greater or equal to %d.",
                    RestrictionsUtils.STORAGE_ID_MIN), e.getMessage());
        }

        // Testar ID válido
        try {
            ValidationsUtils.validateStorageId(validId);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStorageName() {
        String nullName = null;
        String invalidName = RandomString.make(RestrictionsUtils.STORAGE_NAME_MAX_LENGTH + 1);
        String validName = "Fridge";

        // Testar nome null
        try {
            ValidationsUtils.validateStorageName(nullName);
            fail("No exception thrown when validating null name. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Storage name is required.", e.getMessage());
        }

        // Testar nome inválido
        try {
            ValidationsUtils.validateStorageName(invalidName);
            fail("No exception thrown when validating invalid name. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Storage name. Name must contain a maximum of %d characters.",
                    RestrictionsUtils.STORAGE_NAME_MAX_LENGTH), e.getMessage());
        }

        // Testar nome válido
        try {
            ValidationsUtils.validateStorageName(validName);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStorageTemperature() {
        Numrange nullNumRange = null;
        Numrange invalidNumRangeNullValues;
        Numrange invalidNumRange = null;
        Numrange validNumRange = null;

        // Testar intervalo null
        try {
            ValidationsUtils.validateStorageTemperature(nullNumRange);
            fail("No exception thrown when validating null NumRange. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Temperature is required.", e.getMessage());
        }

        // Testar intervalo com valores null
        try {
            invalidNumRangeNullValues = new Numrange(null, null);
            fail("No exception thrown when instanciating an ivalid NumRange (null values).");
        } catch (EntityException e) {
            assertEquals("Both temperature maximum and minimum are required.", e.getMessage());
        }

        // Testar intervalo inválido
        try {
            invalidNumRange = new Numrange(5f, 1f);
            fail("No exception thrown when instanciating an ivalid NumRange (min > max).");
        } catch (EntityException e) {
            assertEquals("Minimum temperature must be smaller than maximum temperature.", e.getMessage());
        }

        // Testar intervalo válido
        try {
            validNumRange = new Numrange(1f, 5f);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }

    }

    @Test
    public void validateStockItemSku() {
        String nullSku = null;
        String invalidSku = RandomString.make(RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH + 1);
        String validSku = "C1-P1-Mimosa-UHT Magro-1L";

        // Testar SKU null
        try {
            ValidationsUtils.validateStockItemSku(nullSku);
            fail("No exception thrown when validating null SKU. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item SKU is required.", e.getMessage());
        }

        // Testar SKU inválido
        try {
            ValidationsUtils.validateStockItemSku(invalidSku);
            fail("No exception thrown when validating invalid SKU. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item SKU. SKU must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH), e.getMessage());
        }

        // Testar SKU válido
        try {
            ValidationsUtils.validateStockItemSku(validSku);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemBrand() {
    }

    @Test
    public void validateStockItemSegment() {
    }

    @Test
    public void validateStockItemSegmentUnit() {
    }

    @Test
    public void validateStockItemVariety() {
    }

    @Test
    public void validateStockItemQuantity() {
    }

    @Test
    public void validateStockItemDescription() {
    }

    @Test
    public void validateStockItemConservationStorage() {
    }

    @Test
    public void validateStockItemMovementDateTime() {
    }

    @Test
    public void validateStockItemMovementType() {
    }

    @Test
    public void validateStockItemMovementQuantity() {
    }

    @Test
    public void validateExpirationDateQuantity() {
    }

    @Test
    public void validateListProductBrand() {
    }

    @Test
    public void validateListProductQuantity() {
    }

    @Test
    public void validateStockItemStorageQuantity() {
    }

    @Test
    public void validateListShareable() {
    }
}