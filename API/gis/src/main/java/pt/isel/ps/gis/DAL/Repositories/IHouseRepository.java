package pt.isel.ps.gis.DAL.Repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.DAL.bdModel.House;

import java.util.List;

public interface IHouseRepository extends CrudRepository<House, Long> {

    List<House> findByName(String name);
}
