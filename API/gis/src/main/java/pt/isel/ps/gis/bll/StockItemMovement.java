package pt.isel.ps.gis.bll;

import java.util.Date;
import java.util.List;

public interface StockItemMovement {

    class MovementFilters {
        public final String type;
        public final Date dateTime;
        public final Short storage;
        public final String item;   // SKU

        public MovementFilters(String type, Date dateTime, Short storage, String item) {
            this.type = type;
            this.dateTime = dateTime;
            this.storage = storage;
            this.item = item;
        }
    }

    /**
     * Listar os movimentos dos itens de uma casa
     * @param houseId identificador da casa
     * @return List<StockItemMovement>
     */
    List<StockItemMovement> getStockItemMovementsByHouseId(Long houseId);

    /**
     * Listar os movimentos filtrados dos itens de uma casa
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItemMovement>
     */
    List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(Long houseId, MovementFilters filters);

    /**
     * Adicionar um movimento à casa
     * @param stockItemMovement movimento a adicionar
     * @return StockItemMovement
     */
    StockItemMovement addStockItemMovement(StockItemMovement stockItemMovement);
}
