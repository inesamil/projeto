package pt.isel.ps.gis.dal.repositories;

public interface UsersRepositoryCustom {

    /**
     * Delete specific user and all associated entities.
     *
     * @param username The id of the user
     */
    void deleteCascadeUserByUsername(String username);
}
