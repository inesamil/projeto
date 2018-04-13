package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.StockItem;
import pt.isel.ps.gis.models.StockItemId;

public interface StockItemRepository extends CrudRepository<StockItem, StockItemId> {
}
