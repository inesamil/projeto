package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.ProductId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, ProductId> {

    @Async
    @Query(value = "SELECT * FROM public.\"product\" WHERE product_name LIKE :name || '%';", nativeQuery = true)
    CompletableFuture<Stream<Product>> getProductsByName(String name);
}
