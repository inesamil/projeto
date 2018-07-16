package pt.isel.ps.gis.dal.repositories;

public interface HouseRepositoryCustom {

    /**
     * Delete specific house and all associated entities
     *
     * @param houseId The id of the house
     */
    void deleteCascadeHouseById(Long houseId);
}
