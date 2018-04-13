package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.StockItemAllergy;
import pt.isel.ps.gis.model.StockItemAllergyId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface StockItemAllergyRepository extends CrudRepository<StockItemAllergy, StockItemAllergyId> {

    @Async
    CompletableFuture<Stream<StockItemAllergy>> findAllById_HouseIdAndId_StockitemSku(Long houseId, String sku);

    @Async
    CompletableFuture<Stream<StockItemAllergy>> findAllById_HouseIdAndId_AllergyAllergen(Long houseId, String allergen);
}
