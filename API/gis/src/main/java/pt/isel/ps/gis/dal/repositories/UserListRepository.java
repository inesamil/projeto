package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

import java.util.List;

public interface UserListRepository extends CrudRepository<UserList, UserListId>, UserListRepositoryCustom {

    List<UserList> findAllById_HouseIdAndListShareable(Long houseId, Boolean shareable);

    List<UserList> findAllById_HouseIdAndUsersUsername(Long houseId, String username);
}
