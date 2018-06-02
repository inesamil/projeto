package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.UserHouseId;
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
    public boolean existsMemberByMemberId(long houseId, String username) throws EntityException {
        return userHouseRepository.existsById(new UserHouseId(houseId, username));
    }

    @Override
    public Optional<UserHouse> getMemberByMemberId(long houseId, String username) throws EntityException {
        return userHouseRepository.findById(new UserHouseId(houseId, username));
    }

    @Override
    public List<UserHouse> getMembersByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return userHouseRepository.findAllById_HouseId(houseId);
    }

    @Override
    public UserHouse associateMember(long houseId, String username, boolean administrator) throws EntityException, EntityNotFoundException {
        UserHouse member = new UserHouse(houseId, username, administrator);
        checkHouse(houseId);
        checkUser(username);
        userHouseRepository.save(member);
        return member;
    }

    @Override
    public void deleteMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException {
        if (!existsMemberByMemberId(houseId, username))
            throw new EntityNotFoundException(MEMBER_NOT_EXIST);
        UserHouseId id = new UserHouseId(houseId, username);
        userHouseRepository.deleteById(id);
    }

    private void checkHouse(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(HOUSE_NOT_EXIST);
    }

    private void checkUser(String username) throws EntityNotFoundException {
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(USER_NOT_EXIST);
    }
}
