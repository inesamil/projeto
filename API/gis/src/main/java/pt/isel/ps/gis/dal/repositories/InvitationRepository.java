package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.InvitationId;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, InvitationId> {
}
