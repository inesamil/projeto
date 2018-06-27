package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class HouseMemberServiceImpl implements HouseMemberService {

    private final UserHouseRepository userHouseRepository;
    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;

    private final MessageSource messageSource;

    public HouseMemberServiceImpl(UserHouseRepository userHouseRepository, HouseRepository houseRepository, UsersRepository usersRepository, MessageSource messageSource) {
        this.userHouseRepository = userHouseRepository;
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsMemberByMemberId(long houseId, String username) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        return userHouseRepository.existsById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
    }

    @Override
    public UserHouse getMemberByMemberId(long houseId, String username, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateUserUsername(username);
        return userHouseRepository
                .findById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("member_Not_Exist", null, locale)));
    }

    @Transactional
    @Override
    public List<UserHouse> getMembersByHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId, locale);
        return userHouseRepository.findAllById_HouseId(houseId);
    }

    @Transactional
    @Override
    public UserHouse associateMember(long houseId, String username, Boolean administrator, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        Users user = usersRepository.findByUsersUsername(username).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user_Not_Exist", null, locale)));
        UserHouse member = new UserHouse(houseId, user.getUsersId(), administrator);
        checkHouse(houseId, locale);
        userHouseRepository.save(member);
        return member;
    }

    @Transactional
    @Override
    public void deleteMemberByMemberId(long houseId, String username, Locale locale) throws EntityException, EntityNotFoundException {
        UserHouse member = getMemberByMemberId(houseId, username, locale);
        userHouseRepository.deleteById(member.getId());
    }

    private void checkHouse(long houseId, Locale locale) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(messageSource.getMessage("house_Not_Exist", null, locale));
    }
}
