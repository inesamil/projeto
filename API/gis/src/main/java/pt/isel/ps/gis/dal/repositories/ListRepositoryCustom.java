package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.List;

public interface ListRepositoryCustom {

    /**
     * Finds all lists filtered by all parameters.
     *
     * @param houseId  The id of the house
     * @param system   If you wants to filter system list then pass true, otherwise false
     * @param username Search by users lists.
     * @param shared   Search by lists shareable
     * @return java.util.List of List that match with filters
     */
    java.util.List<List> findListsFiltered(Long houseId, Boolean system, String username, Boolean shared);
}
