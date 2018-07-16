package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;

import java.util.List;

public interface StockItemMovementRepository extends CrudRepository<StockItemMovement, StockItemMovementId>,
        StockItemMovementRepositoryCustom {

    /**
     * Find all movements associated with specific house
     *
     * @param houseId The id fo the house
     * @return List with all movements associated with param houseId
     */
    List<StockItemMovement> findAllById_HouseId(Long houseId);
}
