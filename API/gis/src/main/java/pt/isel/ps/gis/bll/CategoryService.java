package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /**
     * Listar todas as categorias
     *
     * @return List<Category>
     */
    List<Category> getCategories();

    /**
     * Listar as categorias filtradas
     *
     * @param categoryFilters filtros para aplicar na filtragem dos resultados
     * @return List<Category>
     */
    List<Category> getCategoriesFiltered(CategoryFilters categoryFilters);

    /**
     * Obter uma categoria através do seu ID
     *
     * @param categoryId identificador da categoria
     * @return Cateory
     */
    Optional<Category> getCategoryByCategoryId(Integer categoryId);

    /**
     * Filtros - filtragem das categorias
     */
    class CategoryFilters {
        public final String name;

        public CategoryFilters(String name) {
            this.name = name;
        }
    }
}
