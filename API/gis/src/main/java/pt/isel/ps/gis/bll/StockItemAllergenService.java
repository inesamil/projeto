package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

public interface StockItemAllergenService {

    /**
     * Obter as alergias de um StockItem duma casa
     *
     * @param stockItemId identificador do item em stock
     * @return List<Allergy>
     */
    Iterable<Allergy> getAllergiesByStockItemId(StockItemId stockItemId);

    /**
     * Listar os itens com um determinado alergénio
     *
     * @param houseId  identificador da casa
     * @param allergen identificador do alergénio
     * @return List<StockItem>
     */
    Iterable<StockItem> getStockItemsByHouseIdAndAllergyId(Long houseId, String allergen);
}
