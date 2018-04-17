package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface UserListRepository extends CrudRepository<UserList, UserListId>, UserListRepositoryCustom {

    @Async
    CompletableFuture<Stream<UserList>> findAllById_HouseIdAndListShareable(Long houseId, Boolean shareable);

    @Async
    CompletableFuture<Stream<UserList>> findAllById_HouseIdAndUsersUsername(Long houseId, String username);
}
