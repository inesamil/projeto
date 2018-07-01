package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class StockItemServiceImpl implements StockItemService {

    private final StockItemRepository stockItemRepository;
    private final HouseRepository houseRepository;

    private final MessageSource messageSource;

    public StockItemServiceImpl(StockItemRepository stockItemRepository, HouseRepository houseRepository, MessageSource messageSource) {
        this.stockItemRepository = stockItemRepository;
        this.houseRepository = houseRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException {
        return stockItemRepository.existsById(new StockItemId(houseId, stockItemSku));
    }

    @Transactional
    @Override
    public StockItem getStockItemByStockItemId(long houseId, String stockItemSku, Locale locale) throws EntityException, EntityNotFoundException {
        StockItem stockItem = stockItemRepository
                .findById(new StockItemId(houseId, stockItemSku))
                .orElseThrow(() -> new EntityNotFoundException("Stock item does not exist in this house.", messageSource.getMessage("stock_Item_In_House_Not_Exist", null, locale)));
        stockItem.getStockitemallergies().size();
        stockItem.getExpirationdates().size();
        stockItem.getStockitemstorages().size();
        stockItem.getStockitemmovements().size();
        return stockItem;
    }

    @Transactional
    @Override
    public List<StockItem> getStockItemsByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId, locale);
        return stockItemRepository.findAllById_HouseIdAndStockitemQuantityGreaterThan(houseId, (short) 0);
    }

    @Transactional
    @Override
    public List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId, locale);
        return stockItemRepository.findStockItemsFiltered(houseId, filters.product, filters.brand, filters.variety,
                filters.segment, filters.storage);
    }

    @Transactional
    @Override
    public void deleteStockItem(long houseId, String stockItemSku, Locale locale) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d .", stockItemSku, houseId), messageSource.getMessage("stock_Item_Id_Not_Exist", new Object[]{stockItemSku, houseId}, locale));
        // Remover item de stock bem como todas as relações das quais a casa seja parte integrante
        stockItemRepository.deleteStockItemById(id);
    }

    @Transactional
    @Override
    public void decreaseStockItemQuantity(long houseId, String stockItemSku, short decreasingQuantity, Locale locale) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        ValidationsUtils.validateStockItemQuantity(decreasingQuantity);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d .", stockItemSku, houseId), messageSource.getMessage("stock_Item_Id_Not_Exist", new Object[]{stockItemSku, houseId}, locale));
        stockItemRepository.decrementStockitemQuantity(id, decreasingQuantity);
    }

    @Transactional
    @Override
    public void increaseStockItemQuantity(long houseId, String stockItemSku, short increasingQuantity, Locale locale) throws EntityException, EntityNotFoundException {
        StockItemId id = new StockItemId(houseId, stockItemSku);
        ValidationsUtils.validateStockItemQuantity(increasingQuantity);
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Stock item with ID %s does not exist in the house with ID %d .", stockItemSku, houseId), messageSource.getMessage("stock_Item_Id_Not_Exist", new Object[]{stockItemSku, houseId}, locale));
        stockItemRepository.incrementStockitemQuantity(id, increasingQuantity);
    }

    private void checkHouse(long houseId, Locale locale) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale));
    }
}
