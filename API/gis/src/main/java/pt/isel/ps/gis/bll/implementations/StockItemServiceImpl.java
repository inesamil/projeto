package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StockItemServiceImpl implements StockItemService {

    private final StockItemRepository stockItemRepository;

    public StockItemServiceImpl(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    @Override
    public boolean existsStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException {
        return stockItemRepository.existsById(new StockItemId(houseId, stockItemSku));
    }

    @Transactional
    @Override
    public Optional<StockItem> getStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException {
        Optional<StockItem> byId = stockItemRepository.findById(new StockItemId(houseId, stockItemSku));
        if (!byId.isPresent()) return byId;
        StockItem stockItem = byId.get();
        stockItem.getStockitemstorages().size();
        stockItem.getStockitemmovements().size();
        return byId;
    }

    @Override
    public List<StockItem> getStockItemsByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return stockItemRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return stockItemRepository.findStockItemsFiltered(houseId, filters.product, filters.brand, filters.variety,
                filters.segment, filters.storage);
    }

    @Override
    public StockItem addStockItem(StockItem stockItem) throws EntityException {
        if (stockItem.getId() != null && stockItemRepository.existsById(stockItem.getId()))
            throw new EntityException(String.format("Stock item with ID %sb in the house with ID %d already exists.",
                    stockItem.getId().getStockitemSku(), stockItem.getId().getHouseId()));
        return stockItemRepository.insertStockItem(stockItem);
    }

    @Override
    public void deleteStockItem(long houseId, String stockItemSku) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d.",
                    stockItemSku, houseId));
        // Remover item de stock bem como todas as relações das quais a casa seja parte integrante
        stockItemRepository.deleteStockItemById(id);
    }

    @Override
    public void decreaseStockItemQuantity(long houseId, String stockItemSku, short decreasingQuantity) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d.",
                    stockItemSku, houseId));
        stockItemRepository.decrementStockitemQuantity(id, decreasingQuantity);
    }

    @Override
    public void increaseStockItemQuantity(long houseId, String stockItemSku, short increasingQuantity) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d.",
                    stockItemSku, houseId));
        stockItemRepository.incrementStockitemQuantity(id, increasingQuantity);
    }


}
