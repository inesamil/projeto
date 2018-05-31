package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

import java.util.List;

public interface UserListRepository extends CrudRepository<UserList, UserListId>, UserListRepositoryCustom {

    /**
     * Find all user lists associated with specific house and filtered by shareable
     *
     * @param houseId   The id of the house
     * @param shareable Search for list shareable
     * @return List with all UserList associated with param houseId and filtered.
     */
    List<UserList> findAllById_HouseIdAndListShareable(Long houseId, Boolean shareable);

    /**
     * Find all user lists associated with specific house and filtered by user id.
     *
     * @param houseId  The id of the house
     * @param username Search for user id
     * @return List with all UserList associated with param houseId and filtered.
     */
    List<UserList> findAllById_HouseIdAndUsersUsername(Long houseId, String username);

    /**
     * Find all user lists associated with specific user.
     *
     * @param username The id of the user
     * @return List with all UserList associated with param username.
     */
    List<UserList> findAllByUsersUsername(String username);
}
