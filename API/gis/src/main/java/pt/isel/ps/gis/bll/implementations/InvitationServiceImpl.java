package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.InvitationRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityAlreadyExistsException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;
    private final UserHouseRepository userHouseRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public InvitationServiceImpl(InvitationRepository invitationRepository, HouseRepository houseRepository, UsersRepository usersRepository, UserHouseRepository userHouseRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.invitationRepository = invitationRepository;
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.userHouseRepository = userHouseRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Transactional
    @Override
    public List<Invitation> getReceivedInvitationsByUserUsername(String authenticatedUsername, String username) throws EntityException, InsufficientPrivilegesException {
        ValidationsUtils.validateUserUsername(username);
        authorizationProvider.checkUserAuthorizationToAccessResource(authenticatedUsername, username);
        List<Invitation> invitations = invitationRepository.findAllByUsersByUsersId_UsersUsername(username);
        invitations.forEach(invitation -> {
            invitation.getUsersByUsersId().getUsersUsername();
            invitation.getHouseByHouseId().getHouseName();
        });
        return invitations;
    }

    @Transactional
    @Override
    public Invitation sendInvitation(
            Long houseId,
            String from_username,
            String to_username,
            Locale locale
    ) throws EntityException, EntityNotFoundException, EntityAlreadyExistsException, InsufficientPrivilegesException {
        checkHouse(houseId, locale);
        checkUserUsername(from_username, locale);
        Users invitedUser = checkUserUsername(to_username, locale);

        // Verificar se o utilizador que está a convidar é administrador da casa
        authorizationProvider.checkUserAuthorizationToAdminHouse(from_username, houseId);

        // Verificar se o convite já existe
        boolean existsInvitation = invitationRepository.existsAllById_HouseIdAndUsersByUsersId_UsersUsername(houseId, to_username);
        if (existsInvitation)
            throw new EntityAlreadyExistsException("The invitation had already been sent.", messageSource.getMessage("invitation_already_sent", null, locale));

        // Verificar se o utilizador já é membro da casa
        Optional<UserHouse> invitedMember = userHouseRepository.findById_HouseIdAndUsersByUsersId_UsersUsername(houseId, to_username);
        if (invitedMember.isPresent())
            throw new EntityException("User is already member in the house.", messageSource.getMessage("member_already_exists", null, locale));

        return invitationRepository.save(new Invitation(invitedUser.getUsersId(), houseId));
    }

    @Transactional
    @Override
    public void updateInvitation(String authenticatedUsername, Long houseId, String username, Boolean accept, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        authorizationProvider.checkUserAuthorizationToAccessResource(authenticatedUsername, username);
        if (accept == null)
            throw new EntityException("You must specify the body correctly.", messageSource.getMessage("body_Error_Msg", null, locale));
        if (accept)
            acceptInvitation(username, houseId, locale);
        else
            declineInvitation(username, houseId, locale);
    }

    private void acceptInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        Invitation invitation = invitationRepository
                .findById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username)
                .orElseThrow(() -> new EntityNotFoundException("The invitation does not found.", messageSource.getMessage("invitation_not_found", null, locale)));
        InvitationId id = invitation.getId();
        userHouseRepository.save(new UserHouse(houseId, id.getUsersId(), false));
        Users user = usersRepository
                .findByUsersUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found.", messageSource.getMessage("user_Username_Not_Exist", new Object[]{username}, locale)));
        House house = houseRepository
                .findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale)));
        Characteristics characteristics = house.getHouseCharacteristics();
        Short age = user.getUsersAge();
        if (age >= RestrictionsUtils.CHARACTERISTICS_BABIES_MIN_AGE && age <= RestrictionsUtils.CHARACTERISTICS_BABIES_MAX_AGE)
            characteristics.setHouse_babiesNumber((short) (characteristics.getHouse_babiesNumber() + 1));
        else if (age >= RestrictionsUtils.CHARACTERISTICS_CHILDREN_MIN_AGE && age <= RestrictionsUtils.CHARACTERISTICS_CHILDREN_MAX_AGE)
            characteristics.setHouse_childrenNumber((short) (characteristics.getHouse_childrenNumber() + 1));
        else if (age >= RestrictionsUtils.CHARACTERISTICS_ADULTS_MIN_AGE && age <= RestrictionsUtils.CHARACTERISTICS_ADULTS_MAX_AGE)
            characteristics.setHouse_adultsNumber((short) (characteristics.getHouse_adultsNumber() + 1));
        else if (age >= RestrictionsUtils.CHARACTERISTICS_SENIORS_MIN_AGE && age <= RestrictionsUtils.CHARACTERISTICS_SENIORS_MAX_AGE)
            characteristics.setHouse_seniorsNumber((short) (characteristics.getHouse_seniorsNumber() + 1));
        houseRepository.save(house);
        invitationRepository.deleteById(id);
    }

    private void declineInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        boolean existsInvitation = invitationRepository.existsAllById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
        if (!existsInvitation)
            throw new EntityNotFoundException("The invitation does not found.", messageSource.getMessage("invitation_not_found", null, locale));
        invitationRepository.deleteById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
    }

    private void checkHouse(long houseId, Locale locale) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException("House does not exist.", messageSource.getMessage("house_Not_Exist", null, locale));
    }

    private Users checkUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        Optional<Users> user = usersRepository.findByUsersUsername(username);
        if (!user.isPresent())
            throw new EntityNotFoundException(String.format("Username %s does not exist.", username), messageSource.getMessage("user_Username_Not_Exist", new Object[]{username}, locale));
        return user.get();
    }
}
