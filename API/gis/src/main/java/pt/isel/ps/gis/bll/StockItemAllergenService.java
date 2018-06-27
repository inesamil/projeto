package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;

import java.util.List;
import java.util.Locale;

public interface StockItemAllergenService {

    /**
     * Verifica se um dado alergénio está presente num dado item em stock da casa através dos seus IDs
     *
     * @param houseId      identificador da casa
     * @param stockItemSku identificador do item
     * @param allergen     identificador do alergénio
     * @return true se o alergénio estiver presente no item em stock da casa, false caso contrário
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException;

    /**
     * Obter as alergias de um StockItem duma casa
     *
     * @param houseId      identificador da casa
     * @param stockItemSku identificador do item em stock
     * @return List<Allergy>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku, Locale locale) throws EntityException, EntityNotFoundException;

    /**
     * Listar os itens com um determinado alergénio
     *
     * @param houseId  identificador da casa
     * @param allergen identificador do alergénio
     * @return List<StockItem>
     * @throws EntityException se os parâmetros recebidos forem inválidos
     */
    List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException;
}
