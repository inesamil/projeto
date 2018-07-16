package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    /**
     * Find categories by name that starts with param name
     *
     * @param name name of the category to search
     * @return List with categories that category.name starts with param name
     */
    @Query(value = "SELECT * FROM public.\"category\" WHERE category_name LIKE :name || '%';", nativeQuery = true)
    List<Category> findCategoriesByName(@Param("name") String name);
}
