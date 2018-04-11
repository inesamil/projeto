package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.dal.bdModel.HouseAllergy;

import java.util.List;

public interface HouseAllergyService {

    /**
     * Listar as alergias de uma casa através do ID da casa
     * @param houseId
     * @return
     */
    List<HouseAllergy> getAllergiesByHouseId(Long houseId);

    /**
     * Adicionar uma alergia a uma casa
     * @param houseAllergy alergia a adicionar
     * @return HouseAllergy
     */
    HouseAllergy addHouseAllergy(HouseAllergy houseAllergy);

    /**
     * Atualizar uma alergia duma casa
     * @param houseAllergy alergia atualizada
     * @return HouseAllergy
     */
    HouseAllergy updateHouseAllergy(HouseAllergy houseAllergy);

    /**
     * Desassociar uma alergia de uma casa
     * @param houseAllergy alergia a remover
     */
    void deleteHouseAllergy(HouseAllergy houseAllergy);
}
