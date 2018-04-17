package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.StockItemMovement;

import java.util.Date;

public interface StockItemMovementService {

    /**
     * Listar os movimentos dos itens de uma casa
     *
     * @param houseId identificador da casa
     * @return List<StockItemMovement>
     */
    Iterable<StockItemMovement> getStockItemMovementsByHouseId(Long houseId);

    /**
     * Listar os movimentos filtrados dos itens de uma casa
     *
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItemMovement>
     */
    Iterable<StockItemMovement> getStockItemMovementsByHouseIdFiltered(Long houseId, MovementFilters filters);

    /**
     * Adicionar um movimento à casa
     *
     * @param stockItemMovement movimento a adicionar
     * @return StockItemMovement
     */
    StockItemMovement addStockItemMovement(StockItemMovement stockItemMovement);

    /**
     * Filtros - filtragem dos movimentos
     */
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
}
