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
        String nullBrand = null;
        String invalidBrand = RandomString.make(RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH + 1);
        String validBrand = "Mimosa";

        // Testar marca null
        try {
            ValidationsUtils.validateStockItemBrand(nullBrand);
            fail("No exception thrown when validating null brand. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item brand is required.", e.getMessage());
        }

        // Testar marca inválida
        try {
            ValidationsUtils.validateStockItemBrand(invalidBrand);
            fail("No eception thrown when validating invalid brand. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item brand. Brand must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH), e.getMessage());
        }

        // Testar marca válida
        try {
            ValidationsUtils.validateStockItemBrand(validBrand);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemSegment() {
        Float nullSegment = null;
        Float invalidSegment = RestrictionsUtils.STOCKITEM_SEGMENT_MIN - 1;
        Float validSegment = RestrictionsUtils.STOCKITEM_SEGMENT_MIN;

        // Testar segmento null
        try {
            ValidationsUtils.validateStockItemSegment(nullSegment);
            fail("No exception thrown when validating null segment. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item segment is required.", e.getMessage());
        }

        // Testar segmento inválido
        try {
            ValidationsUtils.validateStockItemSegment(invalidSegment);
            fail("No exception thrown when validating invalid segment. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item segment. Segment must be greater or equal to %f.",
                    RestrictionsUtils.STOCKITEM_SEGMENT_MIN), e.getMessage());
        }

        // Testar segmento válido
        try {
            ValidationsUtils.validateStockItemSegment(validSegment);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemSegmentUnit() {
        String nullSegmentUnit = null;
        String invalidSegmentUnit = "kilogramas";
        String validSegmentUnit = RestrictionsUtils.STOCKITEM_SEGMENTUNIT.kg.toString();

        // Testar unidade do segmento null
        try {
            ValidationsUtils.validateStockItemSegmentUnit(nullSegmentUnit);
            fail("No exception thrown when validating null segment unit. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item segment unit is required.", e.getMessage());
        }

        // Testar unidade do segmento inválida
        try {
            ValidationsUtils.validateStockItemSegmentUnit(invalidSegmentUnit);
            fail("No exception thrown when validating invalid segment unit. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid segment unit. Type must be in [%s].", RestrictionsUtils.STOCKITEM_SEGMENTUNIT.getAllUnits()), e.getMessage());
        }

        // Testar unidade do segmento válida
        try {
            ValidationsUtils.validateStockItemSegmentUnit(validSegmentUnit);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemVariety() {
        String nullVariety = null;
        String invalidVariety = RandomString.make(RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH + 1);
        String validVariety = "UHT Magro";

        // Testar variedade null
        try {
            ValidationsUtils.validateStockItemVariety(nullVariety);
            fail("No exception thrown when validating null variety. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item variety is required.", e.getMessage());
        }

        // Testar variedade inválida
        try {
            ValidationsUtils.validateStockItemVariety(invalidVariety);
            fail("No exception thrown when validating invalid variety. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item variety. Variety must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH), e.getMessage());
        }
        // Testar variedade válida
        try {
            ValidationsUtils.validateStockItemVariety(validVariety);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemQuantity() {
        Short nullQuantity = null;
        Short invalidQuantity = RestrictionsUtils.STOCKITEM_QUANTITY_MIN - 1;
        Short validQuantity = RestrictionsUtils.STOCKITEM_QUANTITY_MIN;

        // Testar quantidade null
        try {
            ValidationsUtils.validateStockItemQuantity(nullQuantity);
            fail("No exception thrown when validating null quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item quantity is required.", e.getMessage());
        }

        // Testar quantidade inválida
        try {
            ValidationsUtils.validateStockItemQuantity(invalidQuantity);
            fail("No exception thrown when validating invalid qunatity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.STOCKITEM_QUANTITY_MIN), e.getMessage());
        }
        // Testar quantidade válida
        try {
            ValidationsUtils.validateStockItemQuantity(validQuantity);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemDescription() {
        String invalidDescription = RandomString.make(RestrictionsUtils.STOCKITEM_DESCRIPTION_MAX_LENGTH + 1);
        String validDescription = "A ultra pasteurização consiste em aquecer o leite a uma elevada temperatura durante " +
                "alguns segundos e de seguida arrefece-lo bruscamente, sem alterar o seu sabor e valor nutritivo. O leite" +
                " Mimosa é recolhido diariamente nos melhores produtores de leite nacionais. ";

        // Testar descrição inválida
        try {
            ValidationsUtils.validateStockItemDescription(invalidDescription);
            fail("No exception thrown when validating invalid description. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Description is too long.", e.getMessage());
        }

        // Testar descrição válida
        try {
            ValidationsUtils.validateStockItemDescription(validDescription);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemConservationStorage() {
        String nullConservationStorage = null;
        String invalidConservationStorage = RandomString.make(RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH + 1);
        String validConservationStorage = RandomString.make(RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH / 2);

        // Testar local de armazenamento indicado null
        try {
            ValidationsUtils.validateStockItemConservationStorage(nullConservationStorage);
            fail("No exception thrown when validating null conservation storage. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item conservation storage is required.", e.getMessage());
        }

        // Testar local de armazenamento indicado inválido
        try {
            ValidationsUtils.validateStockItemConservationStorage(invalidConservationStorage);
            fail("No exception thrown when validating invalid conservation storage. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid stock item conservation storage. Conservation storage must contain a maximum of %d characters.",
                    RestrictionsUtils.STOCKITEM_CONSERVATIONSTORAGE_MAX_LENGTH), e.getMessage());
        }

        // Testar local de armazenamento indicado válido
        try {
            ValidationsUtils.validateStockItemConservationStorage(validConservationStorage);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemMovementDateTime() {
        String nullDateTime = null;
        String invalidDateTime = "2018/05/20 14:30:07";
        String validDateTime = "2018-05-20 14:30:07";

        // Testar data e hora null
        try {
            ValidationsUtils.validateStockItemMovementDateTime(nullDateTime);
            fail("No exception thrown when validating null DateTime. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Stock item movement DateTime is required.", e.getMessage());
        }

        // Testar data e hora inválida
        try {
            ValidationsUtils.validateStockItemMovementDateTime(invalidDateTime);
            fail("No exception thrown when validating invalid DateTime. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Invalid stock item movement DateTime. The format is: \"yyyy-MM-dd HH:mm:ss\".", e.getMessage());
        }

        // Testar data e hora válida
        try {
            ValidationsUtils.validateStockItemMovementDateTime(validDateTime);
        } catch (EntityException e) {
            fail("No exception expected. Thrown " + EntityException.class.getName() + ". Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemMovementType() {
        Boolean nullMovementType = null;
        Boolean validMovementTypeFalse = false;
        Boolean validMovementTypeTrue = false;

        // Testar administrador null
        try {
            ValidationsUtils.validateStockItemMovementType(nullMovementType);
            fail("No exception thrown when validating null movement type. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Movement type is required.", e.getMessage());
        }

        // Testar administrador válido (false)
        try {
            ValidationsUtils.validateStockItemMovementType(validMovementTypeFalse);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }

        // Testar administrador válido (true)
        try {
            ValidationsUtils.validateStockItemMovementType(validMovementTypeTrue);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemMovementQuantity() {
        Short nullQuantity = null;
        Short invalidQuantity = RestrictionsUtils.MOVEMENT_QUANTITY_MIN - 1;
        Short validQuantity = RestrictionsUtils.MOVEMENT_QUANTITY_MIN;

        // Testar quantidade null
        try {
            ValidationsUtils.validateStockItemMovementQuantity(nullQuantity);
            fail("No exception thrown when validating null quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Movement quantity is required.", e.getMessage());
        }

        // Testar quantidade inválida
        try {
            ValidationsUtils.validateStockItemMovementQuantity(invalidQuantity);
            fail("No exception thrown when validating invalid quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Movement quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.MOVEMENT_QUANTITY_MIN), e.getMessage());
        }

        // Testar quantidade válida
        try {
            ValidationsUtils.validateStockItemMovementQuantity(validQuantity);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateExpirationDateQuantity() {
        Short nullQuantity = null;
        Short invalidQuantity = RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN - 1;
        Short validQuantity = RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN;

        // Testar quantidade null
        try {
            ValidationsUtils.validateExpirationDateQuantity(nullQuantity);
            fail("No exception thrown when validating null quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Expiration Date quantity is required.", e.getMessage());
        }

        // Testar quantidade inválida
        try {
            ValidationsUtils.validateExpirationDateQuantity(invalidQuantity);
            fail("No exception thrown when validating invalid quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid Expiration Date quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.EXPIRATIONDATE_QUANTITY_MIN), e.getMessage());
        }

        // Testar quantidade válida
        try {
            ValidationsUtils.validateExpirationDateQuantity(validQuantity);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListProductBrand() {
        String invalidBrand = RandomString.make(RestrictionsUtils.LISTPRODUCT_BRAND_MAX_LENGTH + 1);
        String validBrand = "Mimosa";

        // Testar marca null
        try {
            ValidationsUtils.validateListProductBrand(invalidBrand);
            fail("No exception thrown when validating invalid brand. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Invalid brand.", e.getMessage());
        }

        // Testar marca válida
        try {
            ValidationsUtils.validateListProductBrand(validBrand);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListProductQuantity() {
        Short nullQuantity = null;
        Short invalidQuantity = RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN - 1;
        Short validQuantity = RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN;

        // Testar quantidade null
        try {
            ValidationsUtils.validateListProductQuantity(nullQuantity);
            fail("No exception thrown when validating null quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Quantity is required.", e.getMessage());
        }

        // Testar quantidade inválida
        try {
            ValidationsUtils.validateListProductQuantity(invalidQuantity);
            fail("No exception thrown when validating invalid quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.LISTPRODUCT_QUANTITY_MIN), e.getMessage());
        }

        // Testar quantidade válida
        try {
            ValidationsUtils.validateListProductQuantity(validQuantity);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateStockItemStorageQuantity() {
        Short nullQuantity = null;
        Short invalidQuantity = RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN - 1;
        Short validQuantity = RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN;

        // Testar quantidade null
        try {
            ValidationsUtils.validateStockItemStorageQuantity(nullQuantity);
            fail("No exception thrown when validating null quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals("Quantity is required.", e.getMessage());
        }

        // Testar quantidade inválida
        try {
            ValidationsUtils.validateStockItemStorageQuantity(invalidQuantity);
            fail("No exception thrown when validating invalid quantity. Expected: " + EntityException.class.getName());
        } catch (EntityException e) {
            assertEquals(String.format("Invalid quantity. Quantity must be greater or equal to %d.",
                    RestrictionsUtils.STOCKITEMSTORAGE_QUANTITY_MIN), e.getMessage());
        }

        // Testar quantidade válida
        try {
            ValidationsUtils.validateStockItemStorageQuantity(validQuantity);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }

    @Test
    public void validateListShareable() {
        Boolean nullListShareable = null;
        Boolean validListShareableFalse = false;
        Boolean validListShareableTrue = false;

        // Testar lista partilhável null
        try {
            ValidationsUtils.validateListShareable(nullListShareable);
            fail("No exception thrown when validating edible. Expected EntityException.");
        } catch (EntityException e) {
            assertEquals("Shareable is required.", e.getMessage());
        }

        // Testar lista partilhável válido (false)
        try {
            ValidationsUtils.validateListShareable(validListShareableFalse);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }

        // Testar lista partilhável válido (true)
        try {
            ValidationsUtils.validateListShareable(validListShareableTrue);
        } catch (EntityException e) {
            fail("No exception expected. Thrown EntityException. Message:" + e.getMessage());
        }
    }
}