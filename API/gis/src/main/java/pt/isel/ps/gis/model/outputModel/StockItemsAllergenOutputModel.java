package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class StockItemsAllergenOutputModel {

    private final static String ENTITY_CLASS = "stock-items";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    public StockItemsAllergenOutputModel(long houseId, String allergen, List<StockItem> stockItems) {
        this.klass = initKlass();
        this.properties = initProperties(stockItems);
        this.entities = initEntities(houseId, allergen, stockItems);
        this.links = initLinks(houseId, allergen);
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

    private Entity[] initEntities(long houseId, String allergen, List<StockItem> stockItems) {
        Entity[] entities = new Entity[stockItems.size()];
        for (int i = 0; i < stockItems.size(); ++i) {
            StockItem stockItem = stockItems.get(i);

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("allergy-allergen", allergen);
            properties.put("stock-item-id", stockItem.getId().getStockitemSku());
            properties.put("product-id", stockItem.getProductId());
            properties.put("stock-item-brand", stockItem.getStockitemBrand());
            properties.put("stock-item-conservation-storage", stockItem.getStockitemConservationstorage());
            properties.put("stock-item-description", stockItem.getStockitemDescription());
            properties.put("stock-item-quantity", stockItem.getStockitemQuantity());
            properties.put("stock-item-segment", stockItem.getStockitemSegment() +
                    stockItem.getStockitemSegmentunit());
            properties.put("stock-item-variety", stockItem.getStockitemVariety());

            entities[i] = new Entity(new String[]{"stock-item"}, new String[]{"item"}, properties, null, null);
        }
        return entities;
    }

    private Link[] initLinks(long houseId, String allergen) {
        //URIs
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);
        String stockItemsAllergenUri = UriBuilderUtils.buildStockItemsAllergenUri(houseId, allergen);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, stockItemsAllergenUri);
        //Link-related-houseAllergies
        Link houseAllergiesLink = new Link(new String[]{"related"}, new String[]{"house-allergies", "collection"}, houseAllergiesUri);

        return new Link[]{self, houseAllergiesLink};
    }
}
