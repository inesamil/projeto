package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;

import java.util.List;

public interface ListProductRepository extends CrudRepository<ListProduct, ListProductId> {

    List<ListProduct> findAllById_HouseIdAndId_ListId(Long houseId, Short listId);
}
