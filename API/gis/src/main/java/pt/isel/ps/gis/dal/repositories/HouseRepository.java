package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.isel.ps.gis.model.House;

import java.util.List;

@Repository
public interface HouseRepository extends CrudRepository<House, Long> {
    
    @Query(value = "SELECT public.\"house\".house_id, public.\"house\".house_name, public.\"house\".house_characteristics " +
            "FROM public.\"house\" JOIN public.\"userhouse\" ON public.\"house\".house_id = public.\"userhouse\".house_id " +
            "WHERE public.\"userhouse\".users_username = :username", nativeQuery = true)
    List<House> findAllByUsersUsername(@Param("username") String username);

    //TODO: deleteHouseById(Long houseId) // Apaga a casa com ID = @houseId e todo o conteúdo associado à casa
}
