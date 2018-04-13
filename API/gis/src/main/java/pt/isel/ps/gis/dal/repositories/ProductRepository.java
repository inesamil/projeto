package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.models.Product;
import pt.isel.ps.gis.models.ProductId;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, ProductId> {

    @Async
    CompletableFuture<Stream<Product>> findAllByProductName(String name);
}