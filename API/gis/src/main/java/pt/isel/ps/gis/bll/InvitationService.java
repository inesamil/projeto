package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Invitation;

import java.util.List;
import java.util.Locale;

public interface InvitationService {

    /**
     * Obter o convite enviado pelo utilizador com o username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador que enviou o convite
     * @param houseId identificador da casa
     * @return Invitation
     */
    Invitation getInvitationByInvitationId(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Obter os convites de todas as casas das quais o utilizador com username @username é administrador
     *
     * @param username identificador do utilizador
     * @return List<Invitation>
     */
    List<Invitation> getReceivedInvitationsByUserUsername(String username, Locale locale);

    /**
     * Obter os convites de todas as casas para as quais o utilizador com username @username enviou convite
     *
     * @param username identificador do utilizador
     * @return List<Invitation>
     */
    List<Invitation> getSentInvitationsByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Enviar um convite do utilizador com username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return Invitation
     */
    Invitation sentInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Aceitar o convite do utilizador com username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return Invitation
     */
    Invitation acceptInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Recusar o convite do utilizador com username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return Invitation
     */
    Invitation declineInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Apagar o convite do utilizador com username @username para a casa com ID @houseId
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     */
    void deleteInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException;
}
