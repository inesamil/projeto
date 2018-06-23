package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.InputUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.text.ParseException;
import java.util.List;

@Service
public class StockItemMovementServiceImpl implements StockItemMovementService {

    private static final String HOUSE_DOES_NOT_EXIST = "House does not exist.";
    private static final String PRODUCT_DOES_NOT_EXISTS = "Product does not exist.";
    private static final String STOCK_ITEM_DOES_NOT_EXISTS = "Stock Item does not exist.";
    private static final String STORAGE_DOES_NOT_EXIST = "Storage does not exist.";
    private static final String SEGMENT_INVALID = "Segment is invalid.";

    private final StockItemMovementRepository stockItemMovementRepository;
    private final HouseRepository houseRepository;
    private final StorageRepository storageRepository;
    private final ProductRepository productRepository;
    private final StockItemRepository stockItemRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository, HouseRepository houseRepository, StorageRepository storageRepository, ProductRepository productRepository, StockItemRepository stockItemRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
        this.houseRepository = houseRepository;
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
        this.stockItemRepository = stockItemRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
    }

    @Transactional
    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouseId(houseId);
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouseId(houseId);
        return stockItemMovementRepository.findMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Transactional
    @Override
    public StockItemMovement addStockItemMovement(long houseId, short storageId, boolean movementType, short quantity, String productName, String brand, String variety, String segment, String conservationConditions, String description, String date) throws EntityException, EntityNotFoundException {
        checkHouseId(houseId);
        checkStorageId(houseId, storageId);
        checkProductName(productName);
        // Split segmento
        String[] segmentSplitted = splitSegment(segment);
        float segm = Float.parseFloat(segmentSplitted[0]);
        String segmUnit = segmentSplitted[1];
        if (!movementType) {
            // Cannot remove the stock item if doesn't exist or there is 0 quantity of it
            checkStockItem(houseId, productName, brand, variety, segm, segmUnit);
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
            throw new EntityException("Wrong date format.");
        }
    }

    private void checkStockItem(long houseId, String productName, String brand, String variety, float segm, String segmUnit) throws EntityNotFoundException {
        StockItem stockItem = stockItemRepository
                .findById_HouseIdAndProduct_ProductNameAndStockitemBrandAndStockitemVarietyAndStockitemSegmentAndStockitemSegmentunit(houseId, productName, brand, variety, segm, segmUnit)
                .orElseThrow(() -> new EntityNotFoundException(STOCK_ITEM_DOES_NOT_EXISTS));
        if (stockItem.getStockitemQuantity() == 0) {
            throw new EntityNotFoundException(STOCK_ITEM_DOES_NOT_EXISTS);
        }
    }

    private void checkHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_DOES_NOT_EXIST);
    }

    private void checkProductName(String productName) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateProductName(productName);
        if (!productRepository.existsByProductName(productName))
            throw new EntityNotFoundException(PRODUCT_DOES_NOT_EXISTS);
    }

    private void checkStorageId(long houseId, short storageId) throws EntityException, EntityNotFoundException {
        StorageId id = new StorageId(houseId, storageId);
        if (!storageRepository.existsById(id))
            throw new EntityNotFoundException(STORAGE_DOES_NOT_EXIST);
    }

    private String[] splitSegment(String segment) throws EntityException {
        String[] segmentSplitted = InputUtils.splitNumbersFromLetters(segment);
        if (segmentSplitted.length != 2)
            throw new EntityException(SEGMENT_INVALID);
        return segmentSplitted;
    }
}
