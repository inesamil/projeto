package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;

public interface StockItemRepositoryCustom {

    /**
     * Find all stock items associated with specific house filtered by productName, stock item brand, variety, segment
     * and specific storage.
     *
     * @param houseId     The id of the house
     * @param productName Search for product name
     * @param brand       Search for stock item brand
     * @param variety     Search for stock item variety
     * @param segment     Search for stock item segment (stockItem.segment + stockItem.segmentUnit)
     * @param storageId   Search for specific storage
     * @return List with all stock items associated with param houseId and filtered
     */
    List<StockItem> findStockItemsFiltered(Long houseId, String productName, String brand, String variety, String segment, Short storageId);

    /**
     * Insert stock item
     *
     * @param stockItem instance of StockItem to insert.
     * @return inserted stock item with sku set.
     */
    StockItem insertStockItem(StockItem stockItem);

    /**
     * Decrement stock item quantity and return stock item updated.
     *
     * @param stockItemId         The id of the stock item
     * @param quantityToDecrement quantity to decrement
     * @return Stock item updated.
     */
    StockItem decrementStockitemQuantity(StockItemId stockItemId, Short quantityToDecrement);

    /**
     * Increment stock item quantity and return stock item updated.
     *
     * @param stockItemId         The id of the stock item
     * @param quantityToIncrement quantity to increment
     * @return Stock item updated.
     */
    StockItem incrementStockitemQuantity(StockItemId stockItemId, Short quantityToIncrement);

    /**
     * Delete specific stock item
     *
     * @param id The id of the stock item
     */
    void deleteStockItemById(StockItemId id);
}
