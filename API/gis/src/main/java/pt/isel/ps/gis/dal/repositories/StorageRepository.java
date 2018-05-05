package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

import java.util.List;

public interface StorageRepository extends CrudRepository<Storage, StorageId>, StorageRepositoryCustom {

    /**
     * Find all storages associated with specific house.
     *
     * @param houseId The id of the house
     * @return List with all storages associated with param houseId
     */
    List<Storage> findAllById_HouseId(Long houseId);
}
