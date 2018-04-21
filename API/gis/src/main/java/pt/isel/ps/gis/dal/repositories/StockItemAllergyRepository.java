package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.StockItemAllergy;
import pt.isel.ps.gis.model.StockItemAllergyId;

public interface StockItemAllergyRepository extends CrudRepository<StockItemAllergy, StockItemAllergyId> {
}
