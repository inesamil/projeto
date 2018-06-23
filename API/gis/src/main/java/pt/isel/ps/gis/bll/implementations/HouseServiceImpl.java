package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String USER_NOT_EXIST = "User does not exist.";

    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;
    private final UserHouseRepository userHouseRepository;

    public HouseServiceImpl(HouseRepository houseRepository, UsersRepository usersRepository, UserHouseRepository userHouseRepository) {
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
        this.userHouseRepository = userHouseRepository;
    }

    @Override
    public boolean existsHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.existsById(houseId);
    }

    @Override
    public House getHouseByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException(HOUSE_NOT_EXIST));
    }

    @Transactional
    @Override
    public List<House> getHousesByUserUsername(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsByUsersUsername(username))
            throw new EntityNotFoundException(String.format("The user with username %s does not exist.", username));
        return houseRepository.findAllByUsersUsername(username);
    }

    @Transactional
    @Override
    public House addHouse(String username, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House house = new House(name, characteristics);
        Users user = usersRepository.findByUsersUsername(username).orElseThrow(() -> new EntityNotFoundException(USER_NOT_EXIST));
        house = houseRepository.save(house);
        UserHouse userHouse = userHouseRepository.save(new UserHouse(house.getHouseId(), user.getUsersId(), true));
        userHouse.setUsersByUsersId(user);
        house.setUserhousesByHouseId(new ArrayList<>(Collections.singleton(userHouse)));
        return house;
    }

    @Transactional
    @Override
    public House updateHouse(
            long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber
    ) throws EntityNotFoundException, EntityException {
        House house = getHouseByHouseId(houseId);
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        house.setHouseName(name);
        house.setHouseCharacteristics(characteristics);
        return house;
    }

    @Transactional
    @Override
    public void deleteHouseByHouseId(long houseId) throws EntityNotFoundException, EntityException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
