package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class StockItemMovementOutputModel {

    private final static String ENTITY_CLASS = "movement";

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
    public StockItemMovementOutputModel(StockItemMovement movement) {
        this.klass = initKlass();
        this.properties = initProperties(movement);
        this.entities = initEntities(movement);
        this.actions = initActions(movement);
        this.links = initLinks(movement);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(StockItemMovement movement) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", movement.getId().getHouseId());
        properties.put("storage-id", movement.getId().getStorageId());
        properties.put("stock-item-id", movement.getId().getStockitemSku());
        properties.put("movement-datetime", movement.getId().getStockitemmovementDatetime());
        properties.put("movement-type", movement.getId().getStockitemmovementType());
        properties.put("movement-quantity", movement.getId().getStockitemmovementQuantity());
        return properties;
    }

    private Entity[] initEntities(StockItemMovement movement) {
        long houseId = movement.getId().getHouseId();

        // URIs
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);

        // Subentities
        Entity movements = new Entity(new String[]{"movements", "collection"}, new String[]{"movements"}, movementsUri);

        return new Entity[]{movements};
    }

    private Action[] initActions(StockItemMovement movement) {
        return new Action[]{};
    }

    private Link[] initLinks(StockItemMovement movement) {
        long houseId = movement.getId().getHouseId();
        short storageId = movement.getId().getStorageId();
        String sku = movement.getId().getStockitemSku();
        String datetime = movement.getId().getStockitemmovementDatetime();
        boolean type = movement.getId().getStockitemmovementType();

        // URIs
        String movementUri = UriBuilderUtils.buildMovementUri(houseId, storageId, sku, datetime, type);

        // Link-self
        Link self = new Link(new String[]{"self"}, movementUri);

        return new Link[]{self};
    }
}
