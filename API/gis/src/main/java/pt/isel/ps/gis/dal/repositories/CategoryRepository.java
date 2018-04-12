package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.models.Category;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Async
    CompletableFuture<Stream<Category>> findAllByCategoryName(String name);
}
