package pt.isel.ps.gis.bll;

public interface StockManagementService {

    /**
     * Aplicar o algoritmo para o produto com ID @productId
     *
     * @param productId identificador do produto
     */
    void processOneItem(int productId);

    /**
     * Aplicar o algoritmo para todos os itens
     */
    void processAllItems();
}
