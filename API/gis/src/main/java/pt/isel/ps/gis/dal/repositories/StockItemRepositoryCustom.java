package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.StockItem;

import java.util.List;

public interface StockItemRepositoryCustom {

    List<StockItem> getStockItemsFiltered(Long houseId, String productName, String brand, String variety, Float segment, Short storageId);

    StockItem insertStockItem(StockItem stockItem);
}
