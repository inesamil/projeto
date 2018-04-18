package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.isel.ps.gis.model.House;

@Repository
public interface HouseRepository extends CrudRepository<House, Long> {

    //TODO: findAllByUsersUsername(String username) // Retorna todas as casas do utilizador com username = @username

    //TODO: deleteHouseById(Long houseId) // Apaga a casa com ID = @houseId e todo o conteúdo associado à casa
}
