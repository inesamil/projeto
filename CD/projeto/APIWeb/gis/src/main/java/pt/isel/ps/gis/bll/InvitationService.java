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
     *
     * @param authenticatedUsername
     * @param username identificador do utilizador
     * @return List<Invitation>
     */
    List<Invitation> getReceivedInvitationsByUserUsername(String authenticatedUsername, String username) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Enviar um convite para ser membro da casa com ID @houseId do utilizador com username @from_username para o utilizador com username @to_username
     *
     * @param houseId       identificador da casa
     * @param from_username identificador do utilizador a enviar o convite
     * @param to_username   identificador do utilizador a receber o convite
     * @return Invitation
     */
    Invitation sendInvitation(Long houseId, String from_username, String to_username, Locale locale) throws EntityException, EntityNotFoundException, EntityAlreadyExistsException, InsufficientPrivilegesException;

    /**
     * Atualizar um convite, aceitar ou recusar consoante o paramêtro accept
     *
     * @param authenticatedUsername
     * @param houseId  identificador da casa
     * @param username identificador do utilizador
     * @param accept   true para aceitar o convite, false para recusar o convite
     */
    void updateInvitation(String authenticatedUsername, Long houseId, String username, Boolean accept, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;
}
