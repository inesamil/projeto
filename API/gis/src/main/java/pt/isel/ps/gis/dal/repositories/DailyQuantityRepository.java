package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.DailyQuantity;
import pt.isel.ps.gis.model.DailyQuantityId;

public interface DailyQuantityRepository extends CrudRepository<DailyQuantity, DailyQuantityId>,
        DailyQuantityRepositoryCustom {
}
