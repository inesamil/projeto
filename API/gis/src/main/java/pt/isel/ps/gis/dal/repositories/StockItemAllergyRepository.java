package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.StockItemAllergy;
import pt.isel.ps.gis.model.StockItemAllergyId;

import java.util.List;

public interface StockItemAllergyRepository extends CrudRepository<StockItemAllergy, StockItemAllergyId> {

    List<StockItemAllergy> findAllById_HouseIdAndId_StockitemSku(Long houseId, String sku);

    List<StockItemAllergy> findAllById_HouseIdAndId_AllergyAllergen(Long houseId, String allergen);
}
