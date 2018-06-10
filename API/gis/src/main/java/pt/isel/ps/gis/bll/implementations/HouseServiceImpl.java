package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UserHouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.utils.ValidationsUtils;

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

    @Override
    public List<House> getHousesByUserId(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(USER_NOT_EXIST);
        return houseRepository.findAllByUsersUsername(username);
    }

    @Override
    public House addHouse(String username, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber) throws EntityException {
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House house = new House(name, characteristics);
        house = houseRepository.save(house);
        UserHouse userHouse = userHouseRepository.save(new UserHouse(house.getHouseId(), username, true));
        house.getUserhousesByHouseId().add(userHouse);
        return house;
    }

    @Override
    public House updateHouse(
            long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber
    ) throws EntityNotFoundException, EntityException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House house = new House(houseId, name, characteristics);
        return houseRepository.save(house);
    }

    @Override
    public void deleteHouseByHouseId(long houseId) throws EntityNotFoundException, EntityException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
