package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityAlreadyExistsException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.Invitation;

import java.util.List;
import java.util.Locale;

public interface InvitationService {

    /**
     * Obter os convites de todas as casas para as quais o utilizador com username @username enviou convite
     *
     * @param username identificador do utilizador
     * @return List<Invitation>
     */
    List<Invitation> getReceivedInvitationsByUserUsername(String username) throws EntityException, EntityNotFoundException;

    /**
     * Enviar um convite do utilizador com username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador
     * @param houseId  identificador da casa
     * @return Invitation
     */
    Invitation sentInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException, EntityAlreadyExistsException, InsufficientPrivilegesException;

    /**
     * Atualizar um convite, aceitar ou recusar consoante o paramêtro accept
     *
     * @param username identificador do utilizador
     * @param houseId  identificador da casa
     * @param accept   true para aceitar o convite, false para recusar o convite
     */
    void updateInvitation(String username, Long houseId, Boolean accept, Locale locale) throws EntityException, EntityNotFoundException;
}
