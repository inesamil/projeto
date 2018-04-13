package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface UserHouseRepository extends CrudRepository<UserHouse, UserHouseId> {

    @Async
    CompletableFuture<Stream<UserHouse>> findAllById_UsersUsername(String username);

    @Async
    CompletableFuture<Stream<UserHouse>> findAllById_HouseId(Long houseId);
}
