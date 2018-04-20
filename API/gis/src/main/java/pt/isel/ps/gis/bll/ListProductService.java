package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListProduct;

import java.util.List;

public interface ListProductService {

    /**
     * Verifica se um dado produto está presente num lista através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param listId identificador da lista
     * @param categoryId identificador da categoria
     * @param productId identificador do produto
     * @return true se o produto existir na lista, false caso contrário
     */
    boolean existsListProductByListProductId(long houseId, short listId, int categoryId, int productId) throws EntityException;

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
     */
    ListProduct updateListProduct(ListProduct listProduct) throws EntityNotFoundException;

    /**
     * Remover um produto duma lista
     *
     * @param productId identificador do produto
     */
    void deleteListProduct(long houseId, short listId, int categoryId, int productId) throws EntityException, EntityNotFoundException;

}
