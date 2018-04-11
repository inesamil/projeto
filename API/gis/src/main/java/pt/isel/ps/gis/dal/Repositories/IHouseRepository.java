package pt.isel.ps.gis.dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.dal.bdModel.House;

import java.util.List;

public interface IHouseRepository extends CrudRepository<House, Long> {

    List<House> findByName(String name);
}
