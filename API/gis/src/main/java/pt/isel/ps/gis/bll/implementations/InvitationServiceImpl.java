package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.InvitationRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.InvitationId;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;
    private final UserHouseRepository userHouseRepository;

    private final MessageSource messageSource;

    public InvitationServiceImpl(InvitationRepository invitationRepository, HouseRepository houseRepository, UsersRepository usersRepository, UserHouseRepository userHouseRepository, MessageSource messageSource) {
        this.invitationRepository = invitationRepository;
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.userHouseRepository = userHouseRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Invitation getInvitationByInvitationId(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        long userId = checkUserUsername(username, locale);
        return invitationRepository.findById(new InvitationId(userId, houseId))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("invitation_not_found", null, locale)));    //TODO: adicionar esta mensagem
    }

    @Override
    public List<Invitation> getReceivedInvitationsByUserUsername(String username, Locale locale) {
        return invitationRepository.findAllByAdminUserUsername();//todo: fazer esta função c/ a query enviada pelo messenger
    }

    @Override
    public List<Invitation> getSentInvitationsByUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException {
        long userId = checkUserUsername(username, locale);
        return invitationRepository.findByUserUserId(username)  //TODO: fazer esta função
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("invitation_not_found", null, locale)));
    }

    @Override
    public Invitation sentInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        long userId = checkUserUsername(username, locale);
        checkHouseId(houseId, locale);
        return invitationRepository.save(new Invitation(userId, houseId));
    }

    @Transactional
    @Override
    public Invitation acceptInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        long userId = checkUserUsername(username, locale);
        checkHouseId(houseId, locale);
        InvitationId id = new InvitationId(userId, houseId);
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("invitation_not_found", null, locale)));
        userHouseRepository.save(new UserHouse(houseId, userId, false));
        invitationRepository.deleteById(id);
        return invitation;
    }

    @Override
    public void declineInvitation(String username, Long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        long userId = checkUserUsername(username, locale);
        checkHouseId(houseId, locale);
        invitationRepository.deleteById(new InvitationId(userId, houseId));
    }

    private void checkHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(messageSource.getMessage("house_Id_Not_Exist", new Object[]{houseId}, locale));
    }

    private Long checkUserUsername(String username, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        Users user = usersRepository.findByUsersUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user_Username_Not_Exist", new Object[]{username}, locale)))
        return user.getUsersId();
    }
}
