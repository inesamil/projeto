package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class StockItemOutputModel {

    private final static String ENTITY_CLASS = "stock-item";

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

    // Ctor
    public StockItemOutputModel(StockItem stockItem) {
        this.klass = initKlass();
        this.properties = initProperties(stockItem);
        this.entities = initEntities(stockItem);
        this.actions = initActions(stockItem);
        this.links = initLinks(stockItem);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(StockItem stockItem) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", stockItem.getId().getHouseId());
        properties.put("stock-item-sku", stockItem.getId().getStockitemSku());
        properties.put("category-id", stockItem.getCategoryId());
        properties.put("product-id", stockItem.getProductId());
        properties.put("stock-item-brand", stockItem.getStockitemBrand());
        properties.put("stock-item-conservation-storage", stockItem.getStockitemConservationstorage());
        properties.put("stock-item-description", stockItem.getStockitemDescription());
        properties.put("stock-item-quantity", stockItem.getStockitemQuantity());
        properties.put("stock-item-segment", stockItem.getStockitemSegment() + stockItem.getStockitemSegmentunit());
        properties.put("stock-item-variety", stockItem.getStockitemVariety());
        return properties;
    }

    private Entity[] initEntities(StockItem stockItem) {
        long houseId = stockItem.getId().getHouseId();
        String sku = stockItem.getId().getStockitemSku();

        // URIs
        String stockItemsUri = UriBuilderUtils.buildStockItemsUri(houseId);
        String itemAllergiesUri = UriBuilderUtils.buildAllergiesItemUri(houseId, sku);

        // Subentities
        Entity stockItems = new Entity(new String[]{"stock-items", "collection"}, new String[]{"stock-items"},
                stockItemsUri);
        Entity allergiesItem = new Entity(new String[]{"allergies-item", "collection"}, new String[]{"allergies-item"},
                itemAllergiesUri);

        return new Entity[]{stockItems, allergiesItem};
    }

    private Action[] initActions(StockItem stockItem) {
        return new Action[]{};
    }

    private Link[] initLinks(StockItem stockItem) {
        long houseId = stockItem.getId().getHouseId();
        String sku = stockItem.getId().getStockitemSku();

        // URIs
        String stockItemUri = UriBuilderUtils.buildStockItemUri(houseId, sku);

        // Link-self
        Link self = new Link(new String[]{"self"}, stockItemUri);

        return new Link[]{self};
    }
}
