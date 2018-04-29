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
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<HouseAllergy> getAllergiesByHouseId(long houseId) throws EntityException;

    /**
     * Adicionar uma alergia a uma casa
     *
     * @param houseAllergy alergia a adicionar
     * @return HouseAllergy
     * @throws EntityException se a alergia especificada já existir na casa particularizada
     */
    HouseAllergy addHouseAllergy(HouseAllergy houseAllergy) throws EntityException;

    /**
     * Atualizar uma alergia duma casa
     *
     * @param houseAllergy alergia atualizada
     * @return HouseAllergy
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     */
    HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy) throws EntityNotFoundException;

    /**
     * Desassociar uma alergia de uma casa
     *
     * @param houseId identificador da casa
     * @param allergen identificador da alergia
     * @throws EntityException se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se não encontrar a alergia especificada na casa particularizada
     */
    void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen) throws EntityException, EntityNotFoundException;

    /**
     * Aumentar
     * @param houseId
     * @param allergen
     * @param numberOfAllergics
     * @return
     */
    HouseAllergy increaseHouseAllergyAllergicsNumber(long houseId, String allergen, short numberOfAllergics);
}
