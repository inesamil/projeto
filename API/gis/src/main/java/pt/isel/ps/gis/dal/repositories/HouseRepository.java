package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.House;

public interface HouseRepository extends CrudRepository<House, Long> {
}
