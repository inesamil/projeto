package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.InputUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Service
public class StockItemMovementServiceImpl implements StockItemMovementService {

    private final StockItemMovementRepository stockItemMovementRepository;
    private final HouseRepository houseRepository;
    private final StorageRepository storageRepository;
    private final ProductRepository productRepository;
    private final StockItemRepository stockItemRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository, HouseRepository houseRepository, StorageRepository storageRepository, ProductRepository productRepository, StockItemRepository stockItemRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.stockItemMovementRepository = stockItemMovementRepository;
        this.houseRepository = houseRepository;
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
        this.stockItemRepository = stockItemRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
    }

    @Transactional
    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(String username, long houseId, MovementFilters filters, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        return stockItemMovementRepository.findMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Transactional
    @Override
    public StockItemMovement addStockItemMovement(long houseId, short storageId, boolean movementType, short quantity, String productName, String brand, String variety, String segment, String conservationConditions, String description, String date, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        checkStorageId(houseId, storageId, locale);
        checkProductName(productName, locale);
        // Split segmento
        String[] segmentSplitted = splitSegment(segment, locale);
        float segm = Float.parseFloat(segmentSplitted[0]);
        String segmUnit = segmentSplitted[1];
        if (!movementType) {
            // Cannot remove the stock item if doesn't exist or there is 0 quantity of it
            checkStockItem(houseId, productName, brand, variety, segm, segmUnit, locale);
        }
        try {
            return stockItemMovementRepository.insertStockItemMovement(
                    houseId,
                    storageId,
                    movementType,
                    quantity,
                    productName,
                    brand,
                    variety,
                    segm,
                    segmUnit,
                    description,
                    conservationConditions,
                    DateUtils.convertStringToDate(date)
            );
        } catch (ParseException e) {
            throw new EntityException("Wrong date format.", messageSource.getMessage("wrong_Date_Format", null, locale));
        }
    }

    private void checkStockItem(long houseId, String productName, String brand, String variety, float segm, String segmUnit, Locale locale) throws EntityNotFoundException {
        StockItem stockItem = stockItemRepository
                .findById_HouseIdAndProduct_ProductNameAndStockitemBrandAndStockitemVarietyAndStockitemSegmentAndStockitemSegmentunit(houseId, productName, brand, variety, segm, segmUnit)
                .orElseThrow(() -> new EntityNotFoundException("Stock Item does not exist.", messageSource.getMessage("stock_Item_Not_Exist", null, locale)));
        if (stockItem.getStockitemQuantity() == 0) {
            throw new EntityNotFoundException("Stock Item does not exist.", messageSource.getMessage("stock_Item_Not_Exist", null, locale));
        }
    }

    private void checkHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale));
    }

    private void checkProductName(String productName, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateProductName(productName);
        if (!productRepository.existsByProductName(productName))
            throw new EntityNotFoundException("Product does not exist.", messageSource.getMessage("product_Not_Exist", null, locale));
    }

    private void checkStorageId(long houseId, short storageId, Locale locale) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException("Storage does not exist.", messageSource.getMessage("storage_Not_Exist", null, locale));
    }

    private String[] splitSegment(String segment, Locale locale) throws EntityException {
        String[] segmentSplitted = InputUtils.splitNumbersFromLetters(segment);
        if (segmentSplitted.length != 2)
            throw new EntityException("Segment does not exist.", messageSource.getMessage("segment_Invalid", null, locale));
        return segmentSplitted;
    }
}
