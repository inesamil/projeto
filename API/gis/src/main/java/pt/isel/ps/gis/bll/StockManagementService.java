package pt.isel.ps.gis.bll;

import pt.isel.ps.gis.exceptions.EntityException;

public interface StockManagementService {

    /**
     * Aplicar o algoritmo para o item na casa @houseId e com sku @stockitemSku
     *
     * @param houseId      identificador da casa
     * @param stockitemSku identificador do stock item
     */
    void processOneItem(long houseId, String stockitemSku) throws EntityException;

    /**
     * Aplicar o algoritmo para todos os itens
     */
    void processAllItems();
}
