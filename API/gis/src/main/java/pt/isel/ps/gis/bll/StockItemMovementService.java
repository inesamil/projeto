package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;

import java.sql.Timestamp;
import java.util.List;

public interface StockItemMovementService {

    /**
     * Verifica se um dado movimento existe através do seu ID
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item
     * @param storageId identificador do local de armazenamento
     * @param type tipo do movimento
     * @param dateTime data do movimento
     * @return true se o movimento existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, Timestamp dateTime) throws EntityException;

    /**
     * Listar os movimentos dos itens de uma casa
     *
     * @param houseId identificador da casa
     * @return List<StockItemMovement>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<StockItemMovement> getStockItemMovementsByHouseId(long houseId) throws EntityException;

    /**
     * Listar os movimentos filtrados dos itens de uma casa
     *
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItemMovement>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(long houseId, MovementFilters filters) throws EntityException;

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
        public final boolean type;
        public final Timestamp dateTime;
        public final short storage;
        public final String item;   // SKU

        public MovementFilters(boolean type, Timestamp dateTime, Short storage, String item) {
            this.type = type;
            this.dateTime = dateTime;
            this.storage = storage;
            this.item = item;
        }
    }
}
