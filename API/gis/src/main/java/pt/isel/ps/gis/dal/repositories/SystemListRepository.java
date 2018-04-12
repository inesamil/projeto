package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.SystemList;
import pt.isel.ps.gis.models.SystemListId;

public interface SystemListRepository extends CrudRepository<SystemList, SystemListId> {
}
