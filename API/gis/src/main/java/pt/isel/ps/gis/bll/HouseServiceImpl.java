package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.House;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class HouseServiceImpl implements HouseService {

    //TODO: campo para repo; ctor

    @Override
    public boolean existsHouseByHouseId(Long houseId) {
        //TODO: verificar se a casa existe e retornar
        throw new NotImplementedException();
    }

    @Override
    public House getHouseByHouseId(Long houseId) {
        //TODO: obter a casa com houseId e retornar
        throw new NotImplementedException();
    }

    @Override
    public List<House> getHousesByUserId(String username) {
        return null;
    }

    @Override
    public House addHouse(House house) {
        //TODO: verificar que houseId vai a null
        return null;
    }

    @Override
    public House updateHouse(House house) {
        if (!existsHouseByHouseId(house.getHouseId()))
            throw new IllegalArgumentException(String.format("House with id %d does not exist.", house.getHouseId()));
        //TODO: atualizar a casa
        throw new NotImplementedException();
    }

    @Override
    public void deleteHouse(Long houseId) {
        if (!existsHouseByHouseId(houseId))
            throw new IllegalArgumentException(String.format("House with id %d does not exist.", houseId));
        //TODO: apagar casa através do procedimento
    }
}
