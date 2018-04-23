package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.utils.ValidationsUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {

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
    public House addHouse(House house) throws EntityException {
        House newHouse = house;
        if (house.getHouseId() != null) {
            if (houseRepository.existsById(house.getHouseId()))
                throw new EntityException(String.format("House with ID %d already exists.", house.getHouseId()));
            // É preciso garantir que houseId está a NULL, para ser feita inserção da nova casa.
            // Caso contrário, poderia ser atualizada uma casa já existente.
            newHouse = new House(house.getHouseName(), house.getHouseCharacteristics());
        }
        return houseRepository.save(newHouse);
    }

    @Override
    public House updateHouse(House house) throws EntityNotFoundException {
        if (house.getHouseId() != null && !houseRepository.existsById(house.getHouseId()))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", house.getHouseId()));
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
