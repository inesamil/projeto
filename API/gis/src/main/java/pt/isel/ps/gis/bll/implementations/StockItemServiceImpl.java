package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;
import pt.isel.ps.gis.utils.InputUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StockItemServiceImpl implements StockItemService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String STOCK_ITEM_NOT_EXIST = "Stock item does not exist in this house.";
    private static final String SEGMENT_INVALID = "Segment is invalid.";

    private final StockItemRepository stockItemRepository;
    private final HouseRepository houseRepository;

    public StockItemServiceImpl(StockItemRepository stockItemRepository, HouseRepository houseRepository) {
        this.stockItemRepository = stockItemRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean existsStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException {
        return stockItemRepository.existsById(new StockItemId(houseId, stockItemSku));
    }

    @Transactional
    @Override
    public StockItem getStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException, EntityNotFoundException {
        StockItem stockItem = stockItemRepository
                .findById(new StockItemId(houseId, stockItemSku))
                .orElseThrow(() -> new EntityNotFoundException(STOCK_ITEM_NOT_EXIST));
        stockItem.getStockitemallergies().size();
        stockItem.getExpirationdates().size();
        stockItem.getStockitemstorages().size();
        stockItem.getStockitemmovements().size();
        return stockItem;
    }

    @Override
    public List<StockItem> getStockItemsByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return stockItemRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return stockItemRepository.findStockItemsFiltered(houseId, filters.product, filters.brand, filters.variety,
                filters.segment, filters.storage);
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
        ValidationsUtils.validateStockItemQuantity(decreasingQuantity);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d.",
                    stockItemSku, houseId));
        stockItemRepository.decrementStockitemQuantity(id, decreasingQuantity);
    }

    @Override
    public void increaseStockItemQuantity(long houseId, String stockItemSku, short increasingQuantity) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        ValidationsUtils.validateStockItemQuantity(increasingQuantity);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d.",
                    stockItemSku, houseId));
        stockItemRepository.incrementStockitemQuantity(id, increasingQuantity);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }

    private String[] splitSegment(String segment) throws EntityException {
        String[] segmentSplitted = InputUtils.splitNumbersFromLetters(segment);
        if (segmentSplitted.length != 2)
            throw new EntityException(SEGMENT_INVALID);
        return segmentSplitted;
    }
}
