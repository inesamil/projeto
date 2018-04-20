package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.StockItemMovement;

import java.sql.Timestamp;
import java.util.List;

public interface StockItemMovementRepositoryCustom {

    List<StockItemMovement> findMovementsFiltered(Long houseId, String sku, Boolean type, Timestamp date, Short storageId);
}
