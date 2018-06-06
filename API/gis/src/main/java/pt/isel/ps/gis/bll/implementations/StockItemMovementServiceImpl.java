package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.dal.repositories.StorageRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.model.StorageId;
import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.InputUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StockItemMovementServiceImpl implements StockItemMovementService {

    private static final String HOUSE_DOES_NOT_EXIST = "House does not exist.";
    private static final String PRODUCT_DOES_NOT_EXISTS = "Product does not exist.";
    private static final String STORAGE_DOES_NOT_EXIST = "Storage does not exist.";
    private static final String SEGMENT_INVALID = "Segment is invalid.";

    private final StockItemMovementRepository stockItemMovementRepository;
    private final HouseRepository houseRepository;
    private final StorageRepository storageRepository;
    private final ProductRepository productRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository, HouseRepository houseRepository, StorageRepository storageRepository, ProductRepository productRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
        this.houseRepository = houseRepository;
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouseId(houseId);
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouseId(houseId);
        return stockItemMovementRepository.findMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Override
    public StockItemMovement addStockItemMovement(long houseId, short storageId, boolean movementType, short quantity, String productName, String brand, String variety, String segment, String conservationConditions, String description, String date) throws EntityException, EntityNotFoundException {
        checkHouseId(houseId);
        checkStorageId(houseId, storageId);
        checkProductName(productName);
        //TODO: verificar movimento de saída de stock item que não existe ou que tem quantidade a 0
        // Split segmento
        String[] segmentSplitted = splitSegment(segment);
        float segm = Float.parseFloat(segmentSplitted[0]);
        String segmUnit = segmentSplitted[1];
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
    }

    private void checkHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_DOES_NOT_EXIST);
    }

    private void checkProductName(String productName) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateProductName(productName);
        /*TODO: if (!productRepository.existsByName(productName)) //Preciso desta função no repositório dos produtos
            throw new EntityNotFoundException(PRODUCT_DOES_NOT_EXISTS);*/
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
