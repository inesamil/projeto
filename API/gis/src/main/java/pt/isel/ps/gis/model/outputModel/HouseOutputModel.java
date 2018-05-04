package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class HouseOutputModel {

    private final static String ENTITY_CLASS = "house";

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
    public HouseOutputModel(House house) {
        this.klass = initKlass();
        this.properties = initProperties(house);
        this.entities = initEntities(house);
        this.actions = initActions(house);
        this.links = initLinks(house);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(House house) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", house.getHouseId());
        properties.put("house-name", house.getHouseName());
        properties.put("house-characteristics", house.getHouseCharacteristics());
        return properties;
    }

    private Entity[] initEntities(House house) {
        long houseId = house.getHouseId();

        // URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);
        String itemsUri = UriBuilderUtils.buildStockItemsUri(houseId);
        String householdUri = UriBuilderUtils.buildHouseholdUri(houseId);
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);
        String listsUri = UriBuilderUtils.buildListsUri(houseId);
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

        // Subentities
        Entity index = new Entity(new String[]{"index"}, new String[]{"index"}, indexUri);
        Entity movements = new Entity(new String[]{"movements", "collection"}, new String[]{"movements"}, movementsUri);
        Entity items = new Entity(new String[]{"items", "collection"}, new String[]{"items"}, itemsUri);
        Entity household = new Entity(new String[]{"household", "collection"}, new String[]{"household"}, householdUri);
        Entity houseAllergies = new Entity(new String[]{"house-allergies", "collection"},
                new String[]{"house-allergies"}, houseAllergiesUri);
        Entity lists = new Entity(new String[]{"lists", "collection"}, new String[]{"lists"}, listsUri);
        Entity storages = new Entity(new String[]{"storages", "collection"}, new String[]{"storages"}, storagesUri);

        return new Entity[]{index, movements, items, household, houseAllergies, lists, storages};
    }

    private Action[] initActions(House house) {
        long houseId = house.getHouseId();

        // Type
        String type = "application/x-www-form-urlencoded";

        // URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);

        // PUT house
        Action putHouse = new Action(
                "update-house",
                "Update House",
                Method.PUT,
                houseUri,
                type,
                new Field[]{
                        new Field("name", Field.Type.text, null),
                        new Field("babies-number", Field.Type.number, null),
                        new Field("children-number", Field.Type.number, null),
                        new Field("adults-number", Field.Type.number, null),
                        new Field("seniors-number", Field.Type.number, null)
                }
        );

        // DELETE house
        Action deleteHouse = new Action(
                "delete-house",
                "Delete House",
                Method.DELETE,
                houseUri,
                type,
                null
        );

        return new Action[]{putHouse, deleteHouse};
    }

    private Link[] initLinks(House house) {
        long houseId = house.getHouseId();

        // URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, houseUri);

        return new Link[]{self};
    }
}
