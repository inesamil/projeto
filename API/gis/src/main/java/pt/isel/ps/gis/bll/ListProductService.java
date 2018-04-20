package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListProduct;

import java.util.List;
import java.util.Optional;

public interface ListProductService {

    /**
     * Verifica se um dado produto está presente num lista através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param listId identificador da lista
     * @param categoryId identificador da categoria
     * @param productId identificador do produto
     * @return true se o produto existir na lista, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException;

    /**
     * Obter um dado produto de uma lista através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param listId identificador da lista
     * @param categoryId identificador da categoria
     * @param productId identificador do produto
     * @return Optional<ListProduct>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    Optional<ListProduct> getListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException;

    /**
     * Listar os produtos de uma lista
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @return List<ListProduct>
     */
    List<ListProduct> getListProductsByListId(long houseId, short listId);

    /**
     * Adicionar um produto a uma lista
     *
     * @param listProduct produto a adicionar
     * @return ListProduct
     */
    ListProduct addListProduct(ListProduct listProduct);

    /**
     * Atualizar um producto duma lista
     *
     * @param listProduct produto atualizado
     * @return ListProduct
     * @throws EntityNotFoundException se o produto especificado não existir na lista particularizada
     */
    ListProduct updateListProduct(ListProduct listProduct) throws EntityNotFoundException;

    /**
     * Remover um produto duma lista
     *
     * @param productId identificador do produto
     * @throws EntityException se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o produto especificado não existir na lista particularizada
     */
    void deleteListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException, EntityNotFoundException;

}
