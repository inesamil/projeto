package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.Allergy;

public interface AllergyRepository extends CrudRepository<Allergy, String> {
}
