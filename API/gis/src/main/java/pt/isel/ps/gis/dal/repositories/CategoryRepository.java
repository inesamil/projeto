package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query(value = "SELECT * FROM public.\"category\" WHERE category_name LIKE :name || '%';", nativeQuery = true)
    Iterable<Category> findCategoriesByName(@Param("name") String name);
}
