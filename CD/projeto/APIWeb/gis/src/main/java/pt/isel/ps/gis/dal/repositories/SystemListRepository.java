package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.SystemList;
import pt.isel.ps.gis.model.SystemListId;

public interface SystemListRepository extends CrudRepository<SystemList, SystemListId>, SystemListRepositoryCustom {
}
