package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.StockItem;

import java.util.List;
import java.util.Optional;

public interface StockItemService {

    /**
     * Verifica se um dado item existe em stock na casa através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item
     * @return true se o item em stock existir na casa, false caso contrário
     * @throws EntityException se os parâmetros forem inválidos
     */
    boolean existsStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException;

    /**
     * Obter um item em stock através do seu ID
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item
     * @return Optional<StockItem>
     * @throws EntityException se os parâmetros forem inválidos
     */
    Optional<StockItem> getStockItemByStockItemId(long houseId, String stockItemSku) throws EntityException;

    /**
     * Listar todos os itens em stock duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<StockItem>
     */
    List<StockItem> getStockItemsByHouseId(long houseId) throws EntityException;

    /**
     * Listar todos os itens em stock filtrados duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItem>
     * @throws EntityException se os parâmetros forem inválidos
     */
    List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters) throws EntityException;

    /**
     * Adicionar um item ao stock duma casa
     *
     * @param stockItem item a adicionar ao stock
     * @return StockItem
     * @throws EntityException se o item em stock já existir na casa
     */
    StockItem addStockItem(StockItem stockItem) throws EntityException;

    /**
     * Remover um item duma casa
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item em stock
     * @throws EntityException se os parâmetros forem inválidos
     * @throws EntityNotFoundException se item especificado não existir
     */
    void deleteStockItem(long houseId, String stockItemSku) throws EntityException, EntityNotFoundException;

    /**
     * Reduzir a quantidade em stock de um item em stock
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item em stock
     * @param quantityDecreasing quantidade a reduzir
     * @throws EntityException se os parâmetros forem inválidos
     * @throws EntityNotFoundException se item especificado não existir
     */
    void decreaseStockItemQuantity(long houseId, String stockItemSku, short quantityDecreasing) throws EntityException, EntityNotFoundException;

    /**
     * Reduzir a quantidade em stock de um item em stock
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item em stock
     * @param quantityIncreasing quantidade a incrementar
     * @throws EntityException se os parâmetros forem inválidos
     * @throws EntityNotFoundException se item especificado não existir
     */
    void increaseStockItemQuantity(long houseId, String stockItemSku, short quantityIncreasing) throws EntityException, EntityNotFoundException;


    /**
     * Filtros - filtragem dos itens em stock
     */
    class StockItemFilters {
        public final String product;
        public final String brand;
        public final String variety;
        public final float segment;
        public final short storage;

        public StockItemFilters(String product, String brand, String variety, float segment, short storage) {
            this.product = product;
            this.brand = brand;
            this.variety = variety;
            this.segment = segment;
            this.storage = storage;
        }
    }
}
