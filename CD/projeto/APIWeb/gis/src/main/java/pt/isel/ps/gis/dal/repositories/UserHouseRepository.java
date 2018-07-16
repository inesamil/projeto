package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;

import java.util.List;
import java.util.Optional;

public interface UserHouseRepository extends CrudRepository<UserHouse, UserHouseId> {

    /**
     * Verify if house member exists in house with param houseId and with param username
     *
     * @param houseId  The id of the house
     * @param username The username of the user
     * @return true if user is member of the house, otherwise false
     */
    boolean existsById_HouseIdAndUsersByUsersId_UsersUsername(Long houseId, String username);

    /**
     * Find user house association by house id and username
     *
     * @param houseId  The id of the house
     * @param username The username of the user
     * @return If find returns optional with user house entity, otherwise returns Optional.Empty
     */
    Optional<UserHouse> findById_HouseIdAndUsersByUsersId_UsersUsername(Long houseId, String username);

    /**
     * Find all household associated with specific house
     *
     * @param houseId The id of the house
     * @return List with all UserHouse associated with param houseId
     */
    List<UserHouse> findAllById_HouseId(Long houseId);
}
