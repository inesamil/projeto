package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;

public interface ListRepository extends CrudRepository<List, ListId> {
}
