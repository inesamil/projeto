package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.HouseAllergy;

import java.util.List;
import java.util.Locale;

public interface HouseAllergyService {

    /**
     * Verifica se uma dada alergia de uma casa existe através do seu ID
     *
     * @param houseId identificador da casa
     * @param allergy identificador da alergia
     * @return true se a alergia existir na casa, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException;

    /**
     * Listar as alergias de uma casa através do ID da casa
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return List<HouseAllergy>
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se não encontrar a casa especificada
     */
    List<HouseAllergy> getAllergiesByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Atualizar ou associar uma alergia a uma casa
     *
     * @param houseId   identificador da casa
     * @param allergies alergias a adicionar ou atualizar
     * @return List<HouseAllergy>
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     */
    List<HouseAllergy> associateHouseAllergies(long houseId, HouseAllergy[] allergies, Locale locale) throws EntityNotFoundException, EntityException;


    /**
     * Atualizar ou associar uma alergia a uma casa
     *
     * @param houseId      identificador da casa
     * @param allergen     identificador da alergia
     * @param allergicsNum número de pessoas com a alergia identificada pelo parametro allergen
     * @return HouseAllergy
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     */
    HouseAllergy associateHouseAllergy(long houseId, String allergen, Short allergicsNum, Locale locale) throws EntityNotFoundException, EntityException;

    /**
     * Desassociar uma alergia de uma casa
     *
     * @param houseId  identificador da casa
     * @param allergen identificador da alergia
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     */
    void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException;


    /**
     * Remover todas as alergias associadas a uma casa
     *
     * @param houseId identificador da casa
     * @throws EntityNotFoundException se não a casa especificada não existir
     */
    void deleteAllHouseAllergiesByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException;
}
