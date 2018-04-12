package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.HouseAllergy;
import pt.isel.ps.gis.models.HouseAllergyId;

import java.util.List;

public interface HouseAllergyService {

    /**
     * Listar as alergias de uma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<HouseAllergy>
     */
    List<HouseAllergy> getAllergiesByHouseId(Long houseId);

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
    HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy);

    /**
     * Desassociar uma alergia de uma casa
     *
     * @param houseAllergyId identificador da alergia a remover
     */
    void deleteHouseAllergy(HouseAllergyId houseAllergyId);
}
