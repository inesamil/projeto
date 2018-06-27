package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Product;

import java.util.List;
import java.util.Locale;

public interface ProductService {

    /**
     * Verifica se um dado produto existe através do seu ID
     *
     * @param productId identificador do produto
     * @return true se o produto existir na categoria, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsProductByProductId(int productId) throws EntityException;

    /**
     * Obter um produto através do seu ID
     *
     * @param productId identificador do produto
     * @return Product
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Product getProductByCategoryIdAndProductId(int categoryId, int productId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Listar os produtos de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @return List<Product>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<Product> getProductsByCategoryId(int categoryId, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Listar os produtos filtrados de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @param filters    filtros para aplicar na filtragem dos resultados
     * @return List<ProductService>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<Product> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar um produto
     *
     * @param categoryId               identificador da categoria
     * @param productName              nome do produto
     * @param productEdible            indicador de produto comestível
     * @param productShelflife         tempo médio de vida do produto
     * @param productShelflifeTimeunit unidade do tempo médio de vida do produto
     * @return Product
     * @throws EntityException se um produto com o ID incluído já existir
     */
    // Product addProduct(int categoryId, String productName, boolean productEdible, short productShelflife, String productShelflifeTimeunit) throws EntityException;

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
