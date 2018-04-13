package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Allergy;

public interface AllergyRepository extends CrudRepository<Allergy, String> {
}
