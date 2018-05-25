package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class StockItemsOutputModel {

    private final static String ENTITY_CLASS = "stock-items";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Action[] actions;
    @JsonProperty
    private final Link[] links;

    public StockItemsOutputModel(long houseId, List<StockItem> stockItems) {
        this.klass = initKlass();
        this.properties = initProperties(stockItems);
        this.entities = initEntities(houseId, stockItems);
        this.actions = initActions(houseId);
        this.links = initLinks(houseId);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<StockItem> stockItems) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", stockItems.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, List<StockItem> stockItems) {
        Entity[] entities = new Entity[stockItems.size()];
        for (int i = 0; i < stockItems.size(); ++i) {
            StockItem stockItem = stockItems.get(i);

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("stock-item-id", stockItem.getId().getStockitemSku());
            properties.put("category-id", stockItem.getCategoryId());
            properties.put("product-id", stockItem.getProductId());
            properties.put("stock-item-brand", stockItem.getStockitemBrand());
            properties.put("stock-item-conservation-storage", stockItem.getStockitemConservationstorage());
            properties.put("stock-item-description", stockItem.getStockitemDescription());
            properties.put("stock-item-quantity", stockItem.getStockitemQuantity());
            properties.put("stock-item-segment", stockItem.getStockitemSegment() +
                    stockItem.getStockitemSegmentunit());
            properties.put("stock-item-variety", stockItem.getStockitemVariety());

            String stockItemUri = UriBuilderUtils.buildStockItemUri(houseId, stockItem.getId().getStockitemSku());

            entities[i] = new Entity(
                    new String[]{"stock-item"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"stock-item"}, stockItemUri)});
        }
        return entities;
    }

    private Action[] initActions(long houseId) {
        // Type
        String type = "application/json";

        // URIs
        String stockItemsUri = UriBuilderUtils.buildStockItemsUri(houseId);

        // POST List
        Action postStockItems = new Action(
                "add-stock-item",
                "Add Stock Item",
                Method.POST,
                stockItemsUri,
                type,
                new Field[]{
                        new Field("category-id", Field.Type.number, null, "Category ID"),
                        new Field("product-id", Field.Type.number, null, "Product ID"),
                        new Field("stock-item-brand", Field.Type.text, null, "Brand"),
                        new Field("stock-item-conservation-storage", Field.Type.text, null, "Conservation Storage"),
                        new Field("stock-item-description", Field.Type.text, null, "Description"),
                        new Field("stock-item-quantity", Field.Type.number, null, "Quantity"),
                        new Field("stock-item-segment", Field.Type.text, null, "Segment"),
                        new Field("stock-item-variety", Field.Type.text, null, "Variety")
                }
        );

        return new Action[]{postStockItems};
    }

    private Link[] initLinks(long houseId) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String stockItemsUri = UriBuilderUtils.buildStockItemsUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, stockItemsUri);
        //Link-related-house
        Link houseLink = new Link(new String[]{"related"}, new String[]{"house"}, houseUri);

        return new Link[]{self, houseLink};
    }
}
