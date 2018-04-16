package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

public interface StorageRepository extends CrudRepository<Storage, StorageId>, StorageRepositoryCustom {
}
