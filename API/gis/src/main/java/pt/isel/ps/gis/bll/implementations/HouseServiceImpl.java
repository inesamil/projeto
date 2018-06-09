package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.dal.repositories.UsersRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String USER_NOT_EXIST = "User does not exist.";

    private final HouseRepository houseRepository;
    private final UsersRepository usersRepository;

    public HouseServiceImpl(HouseRepository houseRepository, UsersRepository usersRepository) {
        this.houseRepository = houseRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean existsHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.existsById(houseId);
    }

    @Transactional
    @Override
    public House getHouseByHouseId(long houseId) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        House house =  houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException(HOUSE_NOT_EXIST));
        house.getUserhousesByHouseId().size();
        return house;
    }

    @Transactional
    @Override
    public List<House> getHousesByUserId(String username) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateUserUsername(username);
        if (!usersRepository.existsById(username))
            throw new EntityNotFoundException(USER_NOT_EXIST);
        List<House> houses =  houseRepository.findAllByUsersUsername(username);
        houses.parallelStream().forEach(house -> house.getUserhousesByHouseId().size());
        return houses;
    }

    @Transactional
    @Override
    public House addHouse(String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber) throws EntityException {
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House houseToAdd = new House(name, characteristics);
        House house = houseRepository.save(houseToAdd);
        house.getUserhousesByHouseId().size();
        return house;
    }

    @Transactional
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
        house = houseRepository.save(house);
        house.getUserhousesByHouseId().size();
        return house;
    }

    @Override
    public void deleteHouseByHouseId(long houseId) throws EntityNotFoundException, EntityException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
