package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.utils.ValidationsUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Timestamp;
import java.util.List;

public class StockItemMovementServiceImpl implements StockItemMovementService {

    private final StockItemMovementRepository stockItemMovementRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, Timestamp dateTime) throws EntityException {
        //TODO: return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
        throw new NotImplementedException();
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return stockItemMovementRepository.findMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Override
    public StockItemMovement addStockItemMovement(StockItemMovement stockItemMovement) {
        return stockItemMovementRepository.save(stockItemMovement);
    }
}
