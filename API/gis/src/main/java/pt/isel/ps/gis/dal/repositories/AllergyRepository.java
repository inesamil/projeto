package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Allergy;

import java.util.List;

public interface AllergyRepository extends CrudRepository<Allergy, String> {

    /**
     * Find all allergies by house id and stock item id
     *
     * @param houseId The id of the house
     * @param sku     The id of the stock item
     * @return List with allergies of stock item
     */
    @Query(value = "SELECT public.\"allergy\".allergy_allergen FROM public.\"allergy\" JOIN public.\"stockitemallergy\" " +
            "ON public.\"allergy\".allergy_allergen = public.\"stockitemallergy\".allergy_allergen WHERE " +
            "public.\"stockitemallergy\".house_id = :houseId AND public.\"stockitemallergy\".stockitem_sku = :sku",
            nativeQuery = true)
    List<Allergy> findAllByHouseIdAndStockitemSku(@Param("houseId") Long houseId, @Param("sku") String sku);
}
