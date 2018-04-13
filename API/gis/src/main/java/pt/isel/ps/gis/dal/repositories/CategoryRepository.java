package pt.isel.ps.gis.dal.repositories;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.isel.ps.gis.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Procedure(procedureName = "get_categories_by_name")
    Category functionGetCategoriesByName(@Param("name") String name);
}
