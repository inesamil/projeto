package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import java.util.List;
import java.util.Optional;

public interface StockItemRepository extends PagingAndSortingRepository<StockItem, StockItemId>, StockItemRepositoryCustom {

    /**
     * Find all stock items associated with specific house where stock item quantity is greater than param greaterThan
     *
     * @param houseId     The id of the house
     * @param greaterThan Value to indicate the minimum stock item quantity
     * @return List with all stock items associated with param houseId
     */
    List<StockItem> findAllById_HouseIdAndStockitemQuantityGreaterThan(Long houseId, Short greaterThan);

    /**
     * Find stock item by house id, product name, brand, variety, segment and segment unit
     *
     * @param houseId     The id of the house
     * @param productName The product name
     * @param brand       The stock item brand
     * @param variety     The stock item variety
     * @param segment     The stock item segment
     * @param segmentUnit The stock item segment unit
     * @return Optional with stock item if exists with all parameter, otherwise optional empty
     */
    Optional<StockItem>
    findById_HouseIdAndProduct_ProductNameAndStockitemBrandAndStockitemVarietyAndStockitemSegmentAndStockitemSegmentunit(
            Long houseId, String productName, String brand, String variety, Float segment, String segmentUnit);

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
