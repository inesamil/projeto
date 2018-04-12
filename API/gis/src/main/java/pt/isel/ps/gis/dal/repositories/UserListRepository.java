package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.models.UserList;
import pt.isel.ps.gis.models.UserListId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface UserListRepository extends CrudRepository<UserList, UserListId> {

    @Async
    CompletableFuture<Stream<UserList>> findAllById_HouseIdAndListShareable(Long houseId, Boolean shareable);

    @Async
    CompletableFuture<Stream<UserList>> findAllById_HouseIdAndUsersUsername(Long houseId, String username);
}
