package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.ListProductId;

import java.util.List;

public interface ListProductService {

    /**
     * Listar os produtos de uma lista
     *
     * @param houseId identificador da casa
     * @param listId  identificador da lista
     * @return List<ListProduct>
     */
    List<ListProduct> getListProductsByListId(Long houseId, Short listId);

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
    ListProduct updateListProduct(ListProduct listProduct);

    /**
     * Remover um produto duma lista
     *
     * @param listProductId identificador do produto a remover
     */
    void deleteListProduct(ListProductId listProductId);

}
