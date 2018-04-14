package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface StockItemMovementRepository extends CrudRepository<StockItemMovement, StockItemMovementId> {

    @Async
    CompletableFuture<Stream<StockItemMovement>> findAllById_HouseId(Long houseId);
}
