package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.ProductId;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, ProductId>, ProductRepositoryCustom {

    @Query(value = "SELECT * FROM public.\"product\" WHERE product_name LIKE :name || '%';", nativeQuery = true)
    List<Product> getProductsByName(String name);
}
