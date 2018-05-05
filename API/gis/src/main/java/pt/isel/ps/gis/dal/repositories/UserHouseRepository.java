package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;

import java.util.List;

public interface UserHouseRepository extends CrudRepository<UserHouse, UserHouseId> {

    /**
     * Find all household associated with specific house
     *
     * @param houseId The id of the house
     * @return List with all UserHouse associated with param houseId
     */
    List<UserHouse> findAllById_HouseId(Long houseId);
}
