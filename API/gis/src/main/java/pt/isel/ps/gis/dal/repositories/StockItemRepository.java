package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;

public interface StockItemRepository extends CrudRepository<StockItem, StockItemId>, StockItemRepositoryCustom {

    /**
     * Find all stock items associated with specific house
     *
     * @param houseId The id of the house
     * @return List with all stock items associated with param houseId
     */
    List<StockItem> findAllById_HouseId(Long houseId);

    /**
     * Find all stock items with specific allergen
     *
     * @param houseId  The id of the house
     * @param allergen The id of the allergy
     * @return List with all stock items with param allergen
     */
    @Query(value = "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, " +
            "public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, public.\"stockitem\".stockitem_segment, " +
            "public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, " +
            "public.\"stockitem\".stockitem_segmentUnit, public.\"stockitem\".stockitem_description, " +
            "public.\"stockitem\".stockitem_conservationstorage FROM public.\"stockitem\" JOIN public.\"stockitemallergy\" " +
            "ON public.\"stockitem\".house_id = public.\"stockitemallergy\".house_id AND " +
            "public.\"stockitem\".stockitem_sku = public.\"stockitemallergy\".stockitem_sku WHERE " +
            "public.\"stockitemallergy\".house_id = :houseId AND public.\"stockitemallergy\".allergy_allergen = :allergen;",
            nativeQuery = true)
    List<StockItem> findAllByHouseIdAndAllergyAllergen(@Param("houseId") Long houseId, @Param("allergen") String allergen);
}
