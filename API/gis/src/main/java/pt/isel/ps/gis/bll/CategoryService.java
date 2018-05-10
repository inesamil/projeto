package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /**
     * Verifica se uma dada categoria existe através do seu ID
     *
     * @param categoryId identificador da categoria
     * @return true se a categoria existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsCategoryByCategoryId(int categoryId) throws EntityException;

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
     * @return Optional<Category>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Optional<Category> getCategoryByCategoryId(int categoryId) throws EntityException;

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
