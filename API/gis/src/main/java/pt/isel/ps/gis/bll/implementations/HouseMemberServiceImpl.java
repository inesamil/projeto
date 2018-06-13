package pt.isel.ps.gis.bll.implementations;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

@Service
public class HouseMemberServiceImpl implements HouseMemberService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String MEMBER_NOT_EXIST = "Member does not exist.";
    private static final String USER_NOT_EXIST = "User does not exist.";

    private final UserHouseRepository userHouseRepository;
    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;

    public HouseMemberServiceImpl(UserHouseRepository userHouseRepository, HouseRepository houseRepository, UsersRepository usersRepository) {
        this.userHouseRepository = userHouseRepository;
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean existsMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException {
        checkUserUsername(username);
        return userHouseRepository.existsById_HouseIdAndUsersByUsersId_UsersUsername(houseId, username);
    }

    @Override
    public UserHouse getMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException {
        checkUserUsername(username);
        return userHouseRepository
                .findById(new UserHouseId(houseId, username))
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_EXIST));
    }

    @Override
    public List<UserHouse> getMembersByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return userHouseRepository.findAllById_HouseId(houseId);
    }

    @Override
    public UserHouse associateMember(long houseId, String username, boolean administrator) throws EntityException, EntityNotFoundException {
        checkUserUsername(username);
        Optional<Users> user = usersRepository.findByUsersUsername(username);
        UserHouse member = new UserHouse(houseId, user.get().getUsersId(), administrator);
        checkHouse(houseId);
        checkUser(username);
        userHouseRepository.save(member);
        return member;
    }

    @Override
    public void deleteMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException {
        if (!existsMemberByMemberId(houseId, username))
            throw new EntityNotFoundException(MEMBER_NOT_EXIST);
        Optional<Users> user = usersRepository.findByUsersUsername(username);
        UserHouseId id = new UserHouseId(houseId, user.get().getUsersId());
        userHouseRepository.deleteById(id);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }

    private void checkUser(String username) throws EntityNotFoundException {
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(USER_NOT_EXIST);
    }

    private void checkUserUsername(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(String.format("The user with username %s does not exist.", username));
    }
}
