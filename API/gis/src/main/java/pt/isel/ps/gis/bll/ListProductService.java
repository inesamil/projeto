package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.ListProduct;

import java.util.List;

public interface ListProductService {

    /**
     * Verifica se um dado produto está presente num lista através dos seus IDs
     *
     * @param houseId   identificador da casa
     * @param listId    identificador da lista
     * @param productId identificador do produto
     * @return true se o produto existir na lista, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsListProductByListProductId(long houseId, short listId, int productId) throws EntityException;

    /**
     * Obter um dado produto de uma lista através dos seus IDs
     *
     * @param houseId   identificador da casa
     * @param listId    identificador da lista
     * @param productId identificador do produto
     * @return Optional<ListProduct>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    ListProduct getListProductByListProductId(long houseId, short listId, int productId) throws EntityException;

    /**
     * Listar os produtos de uma lista
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @return List<ListProduct>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<ListProduct> getListProductsByListId(long houseId, short listId) throws EntityException, EntityNotFoundException;

    /**
     * Adicionar ou Atualizar um produto a uma lista
     *
     * @param houseId   identificador da casa
     * @param listId    identificador da lista
     * @param productId identificador do produto
     * @param brand     marca do produto
     * @param quantity  quantidade do produto
     * @return ListProduct
     */
    ListProduct associateListProduct(long houseId, short listId, Integer productId, String brand, Short quantity) throws EntityException, EntityNotFoundException;

    /**
     * Remover um produto de uma lista
     *
     * @param productId identificador do produto
     * @throws EntityException         se os parâmetros recebidos forem inválidos
     * @throws EntityNotFoundException se o produto especificado não existir na lista particularizada
     */
    void deleteListProductByListProductId(long houseId, short listId, int productId) throws EntityException, EntityNotFoundException;

}
