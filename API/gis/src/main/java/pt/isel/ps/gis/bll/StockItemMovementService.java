package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.StockItemMovement;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

public interface StockItemMovementService {

    /**
     * Verifica se um dado movimento existe através do seu ID
     *
     * @param houseId      identificador da casa
     * @param stockItemSku identificador do item
     * @param storageId    identificador do local de armazenamento
     * @param type         tipo do movimento
     * @param dateTime     data do movimento
     * @param quantity     quantidade
     * @return true se o movimento existir, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsStockItemMovementByStockItemMovementId(long houseId, String stockItemSku, short storageId, boolean type, String dateTime, short quantity) throws EntityException;

    /**
     * Listar os movimentos dos itens de uma casa
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @return List<StockItemMovement>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<StockItemMovement> getStockItemMovementsByHouseId(String username, long houseId, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Listar os movimentos filtrados dos itens de uma casa
     *
     * @param username identificador do utilizador
     * @param houseId identificador da casa
     * @param filters filtros para aplicar na filtragem dos resultados
     * @return List<StockItemMovement>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<StockItemMovement> getStockItemMovementsByHouseIdFiltered(String username, long houseId, MovementFilters filters, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Adicionar um movimento à casa
     *
     *
     * @param username
     * @param houseId                identificador da casa
     * @param storageId              identificador do local de armazenamento
     * @param movementType           indicador do tipo de movimento (true - entrada; false - saída)
     * @param quantity               quantidade de entrada ou de saída
     * @param productName            nome do produto
     * @param brand                  marca
     * @param variety                variedade
     * @param segment                segmento
     * @param conservationConditions condições de conservação (Opcional)
     * @param description            descrição (Opcional)
     * @param date                   data de validade
     * @return StockItemMovement
     * @throws EntityException
     * @throws EntityNotFoundException
     */
    StockItemMovement addStockItemMovement(String username, long houseId, short storageId, boolean movementType, short quantity, String productName, String brand, String variety, String segment,
                                           String conservationConditions, String description, String date, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException;

    /**
     * Filtros - filtragem dos movimentos
     */
    class MovementFilters {
        public final Boolean type;
        public final Timestamp dateTime;
        public final Short storage;
        public final String item;   // SKU

        public MovementFilters(Boolean type, Timestamp dateTime, Short storage, String item) {
            this.type = type;
            this.dateTime = dateTime;
            this.storage = storage;
            this.item = item;
        }
    }
}
