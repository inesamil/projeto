package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;

public interface StockItemRepository extends CrudRepository<StockItem, StockItemId>, StockItemRepositoryCustom {

    List<StockItem> findAllById_HouseId(Long houseId);
}
