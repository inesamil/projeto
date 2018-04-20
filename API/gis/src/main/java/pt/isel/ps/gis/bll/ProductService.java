package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Verifica se um dado produto existe numa dada categoria através dos seus IDs
     *
     * @param categoryId identificador da categoria
     * @param productId identificador do produto
     * @return true se o produto existir na categoria, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsProductByProductId(int categoryId, int productId) throws EntityException;

    /**
     * Obter um produto através do seu ID
     *
     * @param categoryId  identificador da categoria
     * @param productId identificador do produto
     * @return Optional<Product>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Optional<Product> getProductByProductId(int categoryId, int productId) throws EntityException;

    /**
     * Listar os produtos de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @return List<ProductService>
     */
    List<ProductService> getProductsByCategoryId(int categoryId);

    /**
     * Listar os produtos filtrados de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @param filters    filtros para aplicar na filtragem dos resultados
     * @return List<ProductService>
     */
    List<ProductService> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters);

    /**
     * Filtros - filtragem das categorias
     */
    class ProductFilters {
        public final String name;

        public ProductFilters(String name) {
            this.name = name;
        }
    }
}
