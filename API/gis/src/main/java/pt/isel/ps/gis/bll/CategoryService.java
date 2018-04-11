package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.dal.bdModel.Category;

import java.util.List;

public interface CategoryService {

    /**
     * Filtros - filtragem das categorias
     */
    class CategoryFilters {
        public final String name;

        public CategoryFilters(String name) {
            this.name = name;
        }
    }

    /**
     * Listar todas as categorias
     * @return List<Category>
     */
    List<Category> getCategories();

    /**
     * Listar as categorias filtradas
     * @param categoryFilters filtros para aplicar na filtragem dos resultados
     * @return List<Category>
     */
    List<Category> getCategoriesFiltered(CategoryFilters categoryFilters);

    /**
     * Obter uma categoria através do seu ID
     * @param categoryId identificador da categoria
     * @return Cateory
     */
    Category getCategoryByCategoryId(Integer categoryId);
}
