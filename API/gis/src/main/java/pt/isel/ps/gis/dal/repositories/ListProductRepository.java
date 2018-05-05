package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;

import java.util.List;

public interface ListProductRepository extends CrudRepository<ListProduct, ListProductId> {

    /**
     * Find all products of the specific list
     *
     * @param houseId The id of the house
     * @param listId  The id of the list
     * @return List with all products in specific list
     */
    List<ListProduct> findAllById_HouseIdAndId_ListId(Long houseId, Short listId);
}
