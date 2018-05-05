package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.StockItemMovement;

import java.sql.Timestamp;
import java.util.List;

public interface StockItemMovementRepositoryCustom {

    /**
     * Find all movements associated with specific house filtered by stock item id, type of the movement, date of the
     * movement and storage id
     *
     * @param houseId   The id of the house
     * @param sku       Search for specific stock item
     * @param type      Search for movement type
     * @param date      Search for movement date
     * @param storageId Search for specific storage
     * @return List with all movements filtered associated with param houseId
     */
    List<StockItemMovement> findMovementsFiltered(Long houseId, String sku, Boolean type, Timestamp date, Short storageId);
}
