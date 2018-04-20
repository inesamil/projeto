package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;

import java.util.List;

public interface StockItemAllergenService {

    /**
     * Verifica se um dado alergénio está presente num dado item em stock da casa através dos seus IDs
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item
     * @param allergen identificador do alergénio
     * @return true se o alergénio estiver presente no item em stock da casa, false caso contrário
     */
    boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException;

    /**
     * Obter as alergias de um StockItem duma casa
     *
     * @param houseId identificador da casa
     * @param stockItemSku identificador do item em stock
     * @return List<Allergy>
     */
    List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku);

    /**
     * Listar os itens com um determinado alergénio
     *
     * @param houseId  identificador da casa
     * @param allergen identificador do alergénio
     * @return List<StockItem>
     */
    List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen);
}
