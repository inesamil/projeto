package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.dal.repositories.InvitationRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.exceptions.EntityAlreadyExistsException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.InvitationId;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserHouseRepository userHouseRepository;

    private final MessageSource messageSource;

    public InvitationServiceImpl(InvitationRepository invitationRepository, UserHouseRepository userHouseRepository, MessageSource messageSource) {
        this.invitationRepository = invitationRepository;
        this.userHouseRepository = userHouseRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<Invitation> getSentInvitationsByUserUsername(String username) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return invitationRepository.findAllByUsersByUsersId_UsersUsername(username);
    }

    @Transactional
    @Override
    public Invitation sentInvitation(
            String username,
            Long houseId,
            Locale locale
    ) throws EntityException, EntityNotFoundException, EntityAlreadyExistsException, InsufficientPrivilegesException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        boolean existsInvitation = invitationRepository.existsAllById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
        if (existsInvitation)
            throw new EntityAlreadyExistsException(messageSource.getMessage("invitation_already_sent", null, locale));
        UserHouse member = userHouseRepository
                .findById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("member_Not_Exist", null, locale)));
        if (!member.getUserhouseAdministrator())
            throw new InsufficientPrivilegesException("You are not administrator of the house");
        Long userId = member.getId().getUsersId();
        return invitationRepository.save(new Invitation(userId, houseId));
    }

    @Transactional
    @Override
    public void acceptInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        Invitation invitation = invitationRepository
                .findById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("invitation_not_found", null, locale)));
        InvitationId id = invitation.getId();
        userHouseRepository.save(new UserHouse(houseId, id.getUsersId(), false));
        invitationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void declineInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        boolean existsInvitation = invitationRepository.existsAllById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
        if (!existsInvitation)
            throw new EntityNotFoundException(messageSource.getMessage("invitation_not_found", null, locale));
        invitationRepository.deleteById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
    }
}
