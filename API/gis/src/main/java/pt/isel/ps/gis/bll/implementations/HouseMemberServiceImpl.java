package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseMemberService;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
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

    private final UserHouseRepository userHouseRepository;
    private final HouseService houseService;

    public HouseMemberServiceImpl(UserHouseRepository userHouseRepository, HouseService houseService) {
        this.userHouseRepository = userHouseRepository;
        this.houseService = houseService;
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
    public List<UserHouse> getMembersByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        checkHouse(houseId);
        return userHouseRepository.findAllById_HouseId(houseId);
    }

    @Override
    public UserHouse addMember(UserHouse member) throws EntityException {
        if (member.getId() != null && userHouseRepository.existsById(member.getId()))
            throw new EntityException(String.format("Member with username %s in the house with ID %d already exists.",
                    member.getId().getUsersUsername(), member.getId().getHouseId()));
        return userHouseRepository.save(member);
    }

    @Override
    public UserHouse updateMember(UserHouse member) throws EntityNotFoundException {
        UserHouseId id = member.getId();
        if (userHouseRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Member with username %s does not exist in the house with ID %d",
                    id.getUsersUsername(), id.getHouseId()));
        return userHouseRepository.save(member);
    }

    @Override
    public UserHouse associateMember(long houseId, String username, boolean administrator) throws EntityException, EntityNotFoundException {
        // TODO é preciso fazer alguma verificacao?
        UserHouse member = new UserHouse(houseId, username, administrator);
        if (existsMemberByMemberId(houseId, username))
            member = updateMember(member);
        else
            member = addMember(member);
        return member;
    }

    @Override
    public void deleteMemberByMemberId(long houseId, String username) throws EntityException, EntityNotFoundException {
        UserHouseId id = new UserHouseId(houseId, username);
        if (!userHouseRepository.existsById(id))
            throw new EntityNotFoundException(String.format("Member with username %s does not exist in the house with ID %d",
                    username, houseId));
        userHouseRepository.deleteById(id);
    }

    private void checkHouse(long houseId) throws EntityException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new EntityException(HOUSE_NOT_EXIST);
    }
}
