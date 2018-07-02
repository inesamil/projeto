package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.outputModel.jsonObjects.CharacteristicsJsonObject;
import pt.isel.ps.gis.model.outputModel.jsonObjects.MemberJsonObject;
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
    private final Action[] actions;
    @JsonProperty
    private final Link[] links;

    // Ctor
    public HouseOutputModel(String username, House house) {
        this.klass = initKlass();
        this.properties = initProperties(house);
        this.actions = initActions(house);
        this.links = initLinks(username, house);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(House house) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", house.getHouseId());
        properties.put("house-name", house.getHouseName());
        properties.put("house-characteristics", new CharacteristicsJsonObject(house.getHouseCharacteristics()));
        properties.put("house-members",
                house.getUserhousesByHouseId()
                        .stream()
                        .map(member -> new MemberJsonObject(
                                member.getId().getHouseId(),
                                member.getUsersByUsersId().getUsersUsername(),
                                member.getUserhouseAdministrator())));
        return properties;
    }

    private Action[] initActions(House house) {
        long houseId = house.getHouseId();

        // Type
        String type = "application/json";

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
                        new Field("name", Field.Type.text, null, "Name"),
                        new Field("babies-number", Field.Type.number, null, "Number of Babies"),
                        new Field("children-number", Field.Type.number, null, "Number of Children"),
                        new Field("adults-number", Field.Type.number, null, "Number of Adults"),
                        new Field("seniors-number", Field.Type.number, null, "Number of Seniors")
                }
        );

        // DELETE house
        Action deleteHouse = new Action(
                "delete-house",
                "Delete House",
                Method.DELETE,
                houseUri,
                null,
                null
        );

        return new Action[]{putHouse, deleteHouse};
    }

    private Link[] initLinks(String username, House house) {
        long houseId = house.getHouseId();

        // Link-self
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        Link selfLink = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS}, houseUri);

        // Link-index
        String indexUri = UriBuilderUtils.buildIndexUri();
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);

        // Link-collection
        String housesUri = UriBuilderUtils.buildUserHousesUri(username);
        Link housesLink = new Link(new String[]{"related"}, new String[]{"houses", "collection"}, housesUri);

        // Link-items
        String itemsUri = UriBuilderUtils.buildStockItemsUri(houseId);
        Link itemsLink = new Link(new String[]{"related"}, new String[]{"items", "collection"}, itemsUri);

        // Link-movements
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);
        Link movementsLink = new Link(new String[]{"related"}, new String[]{"movements", "collection"}, movementsUri);

        // Link-allergies
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);
        Link allergiesLink = new Link(new String[]{"related"}, new String[]{"house-allergies", "collection"}, houseAllergiesUri);

        // Link-lists
        String listsUri = UriBuilderUtils.buildListsUri(houseId);
        Link listsLink = new Link(new String[]{"related"}, new String[]{"lists", "collection"}, listsUri);

        // Link-storages
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);
        Link storagesLink = new Link(new String[]{"related"}, new String[]{"storages", "collection"}, storagesUri);

        return new Link[]{selfLink, indexLink, housesLink, itemsLink, movementsLink, allergiesLink, listsLink, storagesLink};
    }
}
