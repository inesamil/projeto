package pt.isel.ps.gis.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.dal.repositories.HouseRepository;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.House;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean existsHouseByHouseId(Long houseId) {
        return houseRepository.existsById(houseId);
    }

    @Override
    public Optional<House> getHouseByHouseId(Long houseId) {
        return houseRepository.findById(houseId);
    }

    @Override
    public List<House> getHousesByUserId(String username) {
        //TODO: listar casas do utilizador com @username
        throw new NotImplementedException();
    }

    @Override
    public House addHouse(House house) throws EntityException {
        House newHouse = house;
        if (house.getHouseId() != null) {
            // É preciso garantir que houseId está a NULL, para ser feita inserção da nova casa.
            // Caso contrário, seria atualizada uma casa já existente.
            newHouse = new House(house.getHouseName(), house.getHouseCharacteristics());
        }
        return houseRepository.save(newHouse);
    }

    @Override
    public House updateHouse(House house) throws EntityNotFoundException {
        if (!existsHouseByHouseId(house.getHouseId()))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", house.getHouseId()));
        return houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long houseId) throws EntityNotFoundException {
        if (!existsHouseByHouseId(houseId))
            throw new EntityNotFoundException(String.format("House with ID %d does not exist.", houseId));
        houseRepository.deleteById(houseId);    //TODO: delete
    }
}
