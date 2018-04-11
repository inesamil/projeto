package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.models.Allergy;
import pt.isel.ps.gis.models.StockItem;

import java.util.List;

public interface StockItemAllergenService {

    /**
     * Obter as alergias de um StockItem duma casa
     *
     * @param houseId identificador da casa
     * @param sku     identificador do item
     * @return List<Allergy>
     */
    List<Allergy> getAllergiesByStockItemId(Long houseId, String sku);

    /**
     * Listar os itens com um determinado alergénio
     *
     * @param houseId  identificador da casa
     * @param allergen identificador do alergénio
     * @return List<StockItem>
     */
    List<StockItem> getStockItemsByHouseIdAndAllergyId(Long houseId, String allergen);
}
