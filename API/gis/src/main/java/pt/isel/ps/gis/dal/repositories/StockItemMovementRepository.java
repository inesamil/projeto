package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;

import java.util.List;

public interface StockItemMovementRepository extends CrudRepository<StockItemMovement, StockItemMovementId>,
        StockItemMovementRepositoryCustom {

    List<StockItemMovement> findAllById_HouseId(Long houseId);
}
