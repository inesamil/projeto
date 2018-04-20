package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;

import java.sql.Timestamp;
import java.util.List;

public class StockItemMovementServiceImpl implements StockItemMovementService {

    private final StockItemMovementRepository stockItemMovementRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, Timestamp dateTime) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) {
        return stockItemMovementRepository.findAllById_HouseId(houseId);
    }

    @Override
    public List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) {
        return stockItemMovementRepository.getMovementsFiltered(houseId, filters.item, filters.type, filters.dateTime, filters.storage);
    }

    @Override
    public StockItemMovement addStockItemMovement(StockItemMovement stockItemMovement) {
        return stockItemMovementRepository.save(stockItemMovement);
    }
}
