package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;
import java.util.Optional;

public class StockItemServiceImpl implements StockItemService {

    private final StockItemRepository stockItemRepository;

    public StockItemServiceImpl(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    @Override
    public boolean existsStockItemByStockItemId(long houseId, String stockItemSku) {
        return stockItemRepository.existsById(new StockItemId(houseId, stockItemSku));
    }

    @Override
    public Optional<StockItem> getStockItemByStockItemId(long houseId, String stockItemSku) {
        return stockItemRepository.findById(new StockItemId(houseId, stockItemSku));
    }

    @Override
    public List<StockItem> getStockItemsByHouseId(long houseId) {
        return stockItemRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters) {
        return stockItemRepository.getStockItemsFiltered(houseId, filters.product, filters.brand, filters.variety, filters.segment, filters.storage);
    }

    @Override
    public StockItem addStockItem(StockItem stockItem) {
        return stockItemRepository.insertStockItem(stockItem);
    }
}
