package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.models.List;
import pt.isel.ps.gis.models.ListId;

public interface ListRepository extends CrudRepository<List, ListId> {
}
