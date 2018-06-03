package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;

public interface ListRepository extends CrudRepository<List, ListId>, ListRepositoryCustom {

    /**
     * Finds all lists by house ID
     *
     * @param houseId The id of the house
     * @return java.util.List of list of the house with ID {houseId}
     */
    java.util.List<List> findAllById_HouseId(long houseId);
}
