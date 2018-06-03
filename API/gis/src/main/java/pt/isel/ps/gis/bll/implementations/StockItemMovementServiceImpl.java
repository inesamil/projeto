package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StockItemMovementServiceImpl implements StockItemMovementService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";

    private final StockItemMovementRepository stockItemMovementRepository;
    private final HouseRepository houseRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository, HouseRepository houseRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return stockItemMovementRepository.findMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Override
    public StockItemMovement addStockItemMovement(long houseId, String sku, short storageId, boolean movementType,
                                                  String movementDatetime, short movementQuantity) throws EntityException, EntityNotFoundException {
        StockItemMovement stockItemMovement = new StockItemMovement(
                houseId,
                sku,
                storageId,
                movementType,
                movementDatetime,
                movementQuantity
        );
        checkHouse(houseId);
        // TODO é preciso verificar se o sku existe nesta casa e se o storage tmb existe na casa houseId?
        // TODO
        return stockItemMovementRepository.save(stockItemMovement);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }
}
