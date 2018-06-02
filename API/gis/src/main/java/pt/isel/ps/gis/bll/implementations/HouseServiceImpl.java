package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Characteristics;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {

    private static final String HOUSE_NOT_EXIST = "House does not exist.";

    private final HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean existsHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.existsById(houseId);
    }

    @Override
    public Optional<House> getHouseByHouseId(long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        return houseRepository.findById(houseId);
    }

    @Override
    public List<House> getHousesByUserId(String username) throws EntityException {
        ValidationsUtils.validateUserUsername(username);
        return houseRepository.findAllByUsersUsername(username);
    }

    @Override
    public House addHouse(String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber) throws EntityException {
        Characteristics characteristics = new Characteristics(
                babiesNumber,
                childrenNumber,
                adultsNumber,
                seniorsNumber
        );
        House house = new House(name, characteristics);
        return houseRepository.save(house);
    }

    @Override
    public House updateHouse(
            long houseId, String name, Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber
    ) throws EntityNotFoundException, EntityException {
        if (!houseRepository.existsById(houseId))
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
    public void deleteHouseByHouseId(long houseId) throws EntityNotFoundException {
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        // Remover a casa bem como todas as relações das quais a casa seja parte integrante
        houseRepository.deleteCascadeHouseById(houseId);
    }
}
