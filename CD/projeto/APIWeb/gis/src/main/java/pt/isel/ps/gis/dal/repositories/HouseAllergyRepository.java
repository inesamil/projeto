package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;

import java.util.List;

public interface HouseAllergyRepository extends CrudRepository<HouseAllergy, HouseAllergyId> {

    /**
     * Find all allergies of the house with house id
     *
     * @param houseId The id of the house
     * @return List with all allergies of the house
     */
    List<HouseAllergy> findAllById_HouseId(Long houseId);

    /**
     * Delete all allergies associated with specific house
     *
     * @param houseId The id of the house
     */
    void deleteAllById_HouseId(Long houseId);
}
