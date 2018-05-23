package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.StockItemMovementId;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class MovementsOutputModel {

    private final static String ENTITY_CLASS = "movements";

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

    public MovementsOutputModel(long houseId, List<StockItemMovement> movements) {
        this.klass = initKlass();
        this.properties = initProperties(movements);
        this.entities = initEntities(houseId, movements);
        this.actions = initActions(houseId);
        this.links = initLinks(houseId);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<StockItemMovement> movements) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", movements.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, List<StockItemMovement> movements) {
        Entity[] entities = new Entity[movements.size()];
        for (int i = 0; i < movements.size(); ++i) {
            StockItemMovement stockItemMovement = movements.get(i);
            StockItemMovementId stockItemMovementId = stockItemMovement.getId();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("storage-id", stockItemMovement.getStorage().getId());
            properties.put("stock-item-id", stockItemMovement.getStockitem().getId());
            properties.put("movement-datetime", stockItemMovementId.getStockitemmovementDatetime());
            properties.put("movement-type", stockItemMovementId.getStockitemmovementType());
            properties.put("movement-quantity", stockItemMovementId.getStockitemmovementQuantity());

            entities[i] = new Entity(new String[]{"movement"}, new String[]{"item"}, properties, null, null);
        }
        return entities;
    }

    private Action[] initActions(long houseId) {
        // Type
        String type = "application/json";

        // URIs
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);

        // POST Movement
        Action postMovement = new Action(
                "add-movement",
                "Add Movement",
                Method.POST,
                movementsUri,
                type,
                new Field[]{
                        new Field("storage-id", Field.Type.number, null, "Storage ID"),
                        new Field("stock-item-id", Field.Type.number, null, "StockItem ID"),
                        new Field("movement-type", Field.Type.text, null, "Type"),
                        new Field("movement-quantity", Field.Type.number, null, "Quantity")
                }
        );

        return new Action[]{postMovement};
    }

    private Link[] initLinks(long houseId) {
        // URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, movementsUri);
        //Link-related-house
        Link houseLink = new Link(new String[]{"related"}, new String[]{"house"}, houseUri);

        return new Link[]{self, houseLink};
    }
}
