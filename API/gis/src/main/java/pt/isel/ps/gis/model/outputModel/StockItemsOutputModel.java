package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Template;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockItemsOutputModel {

    @JsonProperty
    private final Collection collection;

    public StockItemsOutputModel(long houseId, List<StockItem> stockItems) {
        this.collection = initCollection(houseId, stockItems);
    }

    private Collection initCollection(long houseId, List<StockItem> stockItems) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String stockItemsUri = UriBuilderUtils.buildStockItemsUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, stockItems);

        // Template
        Template template = new Template(
                new Data[]{
                        new Data("house-id", houseId, "House ID"),
                        new Data("category-id", null, "Category ID"),
                        new Data("product-id", null, "Product ID"),
                        new Data("stock-item-brand", null, "Brand"),
                        new Data("stock-item-conservation-storage", null, "Conservation Storage"),
                        new Data("stock-item-description", null, "Description"),
                        new Data("stock-item-quantity", null, "Quantity"),
                        new Data("stock-item-segment", null, "Segment"),
                        new Data("stock-item-variety", null, "Variety")
                });

        return new Collection(version, stockItemsUri, links, items, template);
    }

    private Item[] mapItems(long houseId, List<StockItem> stockItems) {
        int stockItemsSize = stockItems.size();
        Item[] items = new Item[stockItemsSize];
        for (int i = 0; i < stockItemsSize; i++) {
            StockItem stockItem = stockItems.get(i);
            String sku = stockItem.getId().getStockitemSku();
            items[i] = new Item(
                    UriBuilderUtils.buildStockItemUri(houseId, sku),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("stock-item-id", sku, "Stock Item ID"),
                            new Data("category-id", stockItem.getCategoryId(), "Category ID"),
                            new Data("product-id", stockItem.getProductId(), "Product ID"),
                            new Data("stock-item-brand", stockItem.getStockitemBrand(), "Brand"),
                            new Data("stock-item-conservation-storage", stockItem.getStockitemConservationstorage(),
                                    "Conservation Storage"),
                            new Data("stock-item-description", stockItem.getStockitemDescription(), "Description"),
                            new Data("stock-item-quantity", stockItem.getStockitemQuantity(), "Quantity"),
                            new Data("stock-item-segment", stockItem.getStockitemSegment() +
                                    stockItem.getStockitemSegmentunit(), "Segment"),
                            new Data("stock-item-variety", stockItem.getStockitemVariety(), "Variety")
                    },
                    new Link[]{
                            // TODO verificar se o nome da relacao está bom.
                            new Link("allergies-stock-item", UriBuilderUtils.buildAllergiesStockItemUri(houseId, sku))
                    }
            );
        }
        return items;
    }
}
