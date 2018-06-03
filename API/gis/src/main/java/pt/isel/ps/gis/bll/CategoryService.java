package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Category;

import java.util.List;

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
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<Category>
     */
    List<Category> getCategoriesFiltered(CategoryFilters filters) throws EntityException;

    /**
     * Obter uma categoria através do seu ID
     *
     * @param categoryId identificador da categoria
     * @return Optional<Category>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Category getCategoryByCategoryId(int categoryId) throws EntityException, EntityNotFoundException;

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
