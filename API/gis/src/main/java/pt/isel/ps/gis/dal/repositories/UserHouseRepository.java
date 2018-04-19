package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;

import java.util.List;

public interface UserHouseRepository extends CrudRepository<UserHouse, UserHouseId> {

    List<UserHouse> findAllById_UsersUsername(String username);

    List<UserHouse> findAllById_HouseId(Long houseId);
}
