package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface StockItemRepository extends CrudRepository<StockItem, StockItemId>, StockItemRepositoryCustom {

    @Async
    CompletableFuture<Stream<StockItem>> findAllById_HouseId(Long houseId);
}
