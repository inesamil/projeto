package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.models.ListProduct;
import pt.isel.ps.gis.models.ListProductId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ListProductRepository extends CrudRepository<ListProduct, ListProductId> {

    @Async
    CompletableFuture<Stream<ListProduct>> findAllById_HouseIdAndId_ListId(Long houseId, Short listId);
}
