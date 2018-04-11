package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.Product;

import java.util.List;

public interface ProductService {

    /**
     * Obter um produto através do seu ID
     *
     * @param categoryId identificador da categoria
     * @param productId  identificador do produto
     * @return Product
     */
    Product getProductByProductId(Integer categoryId, Integer productId);

    /**
     * Listar os produtos de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @return List<ProductService>
     */
    List<ProductService> getProductsByCategoryId(Integer categoryId);

    /**
     * Listar os produtos filtrados de uma categoria através do ID da categoria
     *
     * @param categoryId identificador da categoria
     * @param filters    filtros para aplicar na filtragem dos resultados
     * @return List<ProductService>
     */
    List<ProductService> getProductsByCategoryIdFiltered(Integer categoryId, ProductFilters filters);

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
