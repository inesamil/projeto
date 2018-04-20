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
     */
    boolean existsHouseAllergyByHouseAllergyId(long houseId, String allergy) throws EntityException;

    /**
     * Listar as alergias de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<HouseAllergy>
     */
    List<HouseAllergy> getAllergiesByHouseId(long houseId);

    /**
     * Adicionar uma alergia a uma casa
     *
     * @param houseAllergy alergia a adicionar
     * @return HouseAllergy
     */
    HouseAllergy addHouseAllergy(HouseAllergy houseAllergy);

    /**
     * Atualizar uma alergia duma casa
     *
     * @param houseAllergy alergia atualizada
     * @return HouseAllergy
     */
    HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy) throws EntityException, EntityNotFoundException;

    /**
     * Desassociar uma alergia de uma casa
     *
     * @param houseId identificador da casa
     * @param allergen identificador da alergia
     */
    void deleteHouseAllergyByHouseAllergyId(long houseId, String allergen) throws EntityException, EntityNotFoundException;
}
