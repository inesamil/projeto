package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockItemsAllergenOutputModel {

    @JsonProperty
    private final Collection collection;

    public StockItemsAllergenOutputModel(long houseId, String allergen, List<StockItem> stockItems) {
        this.collection = initCollection(houseId, allergen, stockItems);
    }

    private Collection initCollection(long houseId, String allergen, List<StockItem> stockItems) {
        //URIs
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);
        String stockItemsAllergenUri = UriBuilderUtils.buildStockItemsAllergen(houseId, allergen);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house-allergies", houseAllergiesUri)
        };

        // Items
        Item[] items = mapItems(houseId, allergen, stockItems);

        return new Collection(version, stockItemsAllergenUri, links, items, null);
    }

    private Item[] mapItems(long houseId, String allergen, List<StockItem> stockItems) {
        int stockItemsSize = stockItems.size();
        Item[] items = new Item[stockItemsSize];
        for (int i = 0; i < stockItemsSize; i++) {
            StockItem stockItem = stockItems.get(i);

            items[i] = new Item(
                    null,
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("allergy-allergen", allergen, "Allergen"),
                            new Data("stock-item-id", stockItem.getId().getStockitemSku(), "Stock Item ID"),
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
                    null
            );
        }
        return items;
    }
}
