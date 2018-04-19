package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;

import java.util.List;

public interface HouseAllergyRepository extends CrudRepository<HouseAllergy, HouseAllergyId> {

    List<HouseAllergy> findAllById_HouseId(Long houseId);
}
