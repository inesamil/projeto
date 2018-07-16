package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.List;

public interface ListRepositoryCustom {

    /**
     * Finds all lists filtered by all parameters.
     *
     * @param username      The id of the user
     * @param houses        Array with ids of the houses to be filter.
     * @param systemLists   If you wants to filter system list then pass true, otherwise false
     * @param listsFromUser If you wants to filter user list then pass true, otherwise false
     * @param sharedLists   If you wants to see shareable lists then pass true, otherwise false
     * @return java.util.List of list that match with filters
     */
    java.util.List<List> findAvailableListsByUserUsername(String username, Long[] houses, Boolean systemLists,
                                                          Boolean listsFromUser, Boolean sharedLists);
}
