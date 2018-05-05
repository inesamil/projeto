package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.ProductId;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, ProductId>, ProductRepositoryCustom {

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
    List<Product> findAllById_CategoryId(Integer categoryId);

    /**
     * Find products that starts with param name and belong to specific category
     *
     * @param categoryId The id of the category
     * @param name       Name of the product to search
     * @return List with all products in specific list that product.name starts with param name
     */
    @Query(value = "SELECT * FROM public.\"product\" JOIN public.\"category\" ON " +
            "public.\"product\".category_id = public.\"category\".category_id " +
            "WHERE public.\"category\".category_id = :categoryId AND public.\"product\".product_name LIKE :name || '%';",
            nativeQuery = true)
    List<Product> findProductsByNameAndCategoryId(@Param("categoryId") Integer categoryId, @Param("name") String name);
}
