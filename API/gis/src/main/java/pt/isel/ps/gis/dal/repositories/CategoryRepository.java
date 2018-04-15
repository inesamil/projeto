package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import pt.isel.ps.gis.model.Category;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Async
    @Query(value = "SELECT * FROM public.\"category\" WHERE category_name LIKE :name || '%';", nativeQuery = true)
    CompletableFuture<Stream<Category>> getCategoriesByName(@Param("name") String name);
}
