package pt.isel.ps.gis.bll;

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
     */
    boolean existsStockItemByStockItemId(long houseId, String stockItemSku);

    /**
     * Obter um item em stock através do seu ID
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item
     * @return StockItem
     */
    Optional<StockItem> getStockItemByStockItemId(long houseId, String stockItemSku);

    /**
     * Listar todos os itens em stock duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @return List<StockItem>
     */
    List<StockItem> getStockItemsByHouseId(long houseId);

    /**
     * Listar todos os itens em stock filtrados duma casa através do ID da casa
     *
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItem>
     */
    List<StockItem> getStockItemsByHouseIdFiltered(long houseId, StockItemFilters filters);

    /**
     * Adicionar um item ao stock duma casa
     *
     * @param stockItem item a adicionar ao stock
     * @return StockItem
     */
    StockItem addStockItem(StockItem stockItem);

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
