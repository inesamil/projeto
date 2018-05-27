package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StockItemMovementServiceImpl implements StockItemMovementService {

    private final StockItemMovementRepository stockItemMovementRepository;

    public StockItemMovementServiceImpl(StockItemMovementRepository stockItemMovementRepository) {
        this.stockItemMovementRepository = stockItemMovementRepository;
    }

    @Override
    public boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException {
        return stockItemMovementRepository.existsById(new StockItemMovementId(houseId, stockItemSku, storageId, type, dateTime));
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
        //TODO
        return stockItemMovementRepository.save(stockItemMovement);
    }
}
