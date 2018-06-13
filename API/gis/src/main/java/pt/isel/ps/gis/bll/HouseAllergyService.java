package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;

import java.util.List;

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
     * @param houseId identificador da casa
     * @return List<HouseAllergy>
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se não encontrar a casa especificada
     */
    List<HouseAllergy> getAllergiesByHouseId(long houseId) throws EntityException, EntityNotFoundException;

    /**
     * Atualizar ou associar uma alergia a uma casa
     *
     * @param houseId   identificador da casa
     * @param allergies alergias a adicionar ou atualizar
     * @return List<HouseAllergy>
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     */
    List<HouseAllergy> associateHouseAllergies(long houseId, HouseAllergy[] allergies) throws EntityNotFoundException, EntityException;


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
    HouseAllergy associateHouseAllergy(long houseId, String allergen, short allergicsNum) throws EntityNotFoundException, EntityException;

    /**
     * Desassociar uma alergia de uma casa
     *
     * @param houseId  identificador da casa
     * @param allergen identificador da alergia
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     */
    void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen) throws EntityException, EntityNotFoundException;

    /**
     * Aumentar
     *
     * @param houseId
     * @param allergen
     * @param numberOfAllergics
     * @return
     */
    HouseAllergy increaseHouseAllergyAllergicsNumber(long houseId, String allergen, short numberOfAllergics);
}
