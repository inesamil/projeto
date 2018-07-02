package pt.isel.ps.gis.stockAlgorithm;

public interface StockManagementAlgorithm {

    /**
     * Previsão das quantidades existentes para a semana seguinte dado o histórico
     *
     * @param history Array<Item> contém histórico do item
     * @return Item[]
     */
    Item[] estimateNextWeek(Item[] history);
}
