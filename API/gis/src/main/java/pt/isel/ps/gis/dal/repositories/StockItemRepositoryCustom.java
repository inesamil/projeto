package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;

public interface StockItemRepositoryCustom {

    List<StockItem> findStockItemsFiltered(Long houseId, String productName, String brand, String variety, Float segment, Short storageId);

    StockItem insertStockItem(StockItem stockItem);

    StockItem decrementStockitemQuantity(StockItemId stockItemId, Short quantityToDecrement);

    StockItem incrementStockitemQuantity(StockItemId stockItemId, Short quantityToIncrement);

    void deleteStockItemById(StockItemId id);
}
