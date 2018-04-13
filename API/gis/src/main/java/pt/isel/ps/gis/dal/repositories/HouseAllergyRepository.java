package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.HouseAllergyId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface HouseAllergyRepository extends CrudRepository<HouseAllergy, HouseAllergyId> {

    @Async
    CompletableFuture<Stream<HouseAllergy>> findAllById_HouseId(Long houseId);
}
