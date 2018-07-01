package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.InvitationId;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, InvitationId> {

    /**
     * Verify if user in specific house has invitation
     *
     * @param houseId  The id of the user
     * @param username The username of the user
     * @return true if user in specific house has invitation, otherwise false
     */
    boolean existsAllById_HouseIdAndUsersByUsersId_UsersUsername(Long houseId, String username);

    /**
     * Find user's invitation in specific house
     *
     * @param houseId  The id of the house
     * @param username The username of the user
     * @return Optional with invitation if found invitation, otherwise Optional.Empty
     */
    Optional<Invitation> findById_HouseIdAndUsersByUsersId_UsersUsername(Long houseId, String username);

    /**
     * Find all user's invitations
     *
     * @param username The username of the user
     * @return List with all user's invitations
     */
    List<Invitation> findAllByUsersByUsersId_UsersUsername(String username);

    /**
     * Delete user's invitation from specific house
     *
     * @param houseId  The id of the house
     * @param username The username of the user
     */
    void deleteById_HouseIdAndUsersByUsersId_UsersUsername(Long houseId, String username);
}
