package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface StorageRepository extends CrudRepository<Storage, StorageId> {

    @Async
    CompletableFuture<Stream<Storage>> findAllById_HouseId(Long houseId);
}
