package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    /**
     * Find products by name that starts with param name
     *
     * @param name name of the product to search
     * @return List with all products that product.name starts with param name
     */
    @Query(value = "SELECT * FROM public.\"product\" WHERE product_name LIKE :name || '%';", nativeQuery = true)
    List<Product> findProductsByName(String name);

    /**
     * Find all products in specific category
     *
     * @param categoryId The id of the category
     * @return List with all products in specific category
     */
    List<Product> findAllByCategoryId(Integer categoryId);

    /**
     * Find products that starts with param name and belong to specific category
     *
     * @param categoryId The id of the category
     * @param name       Name of the product to search
     * @return List with all products in specific list that product.name starts with param name
     */
    @Query(value = "SELECT * FROM public.\"product\" " +
            "WHERE public.\"product\".category_id = :categoryId AND public.\"product\".product_name LIKE :name || '%';",
            nativeQuery = true)
    List<Product> findProductsByNameAndCategoryId(@Param("categoryId") Integer categoryId, @Param("name") String name);

    /**
     * Find specific product in specific category
     *
     * @param categoryId The id of the category
     * @param productId  The id of the product
     * @return Optional<Product> with productId in category with categoryId if isPresent() returns true
     */
    Optional<Product> findByCategoryIdAndProductId(Integer categoryId, Integer productId);
}
