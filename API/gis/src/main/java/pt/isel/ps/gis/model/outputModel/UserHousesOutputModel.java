package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.model.outputModel.jsonObjects.CharacteristicsJsonObject;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class UserHousesOutputModel {

    private final static String ENTITY_CLASS = "user-houses";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    public UserHousesOutputModel(String username, List<House> houses) {
        this.klass = initKlass();
        this.properties = initProperties(houses);
        this.entities = initEntities(username, houses);
        this.links = initLinks(username);
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<House> houses) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", houses.size());

        return properties;
    }

    private Entity[] initEntities(String username, List<House> houses) {
        Entity[] entities = new Entity[houses.size()];
        for (int i = 0; i < houses.size(); ++i) {
            House house = houses.get(i);
            Long houseId = house.getHouseId();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("house-name", house.getHouseName());
            properties.put("house-characteristics", new CharacteristicsJsonObject(house.getHouseCharacteristics()));

            String houseUri = UriBuilderUtils.buildHouseUri(houseId);
            String itemsUri = UriBuilderUtils.buildStockItemsUri(houseId);
            String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);
            String allergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);
            String listsUri = UriBuilderUtils.buildListsUri(houseId);
            String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

            entities[i] = new Entity(
                    new String[]{"house"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"house"}, houseUri),
                            new Link(new String[]{"related"}, new String[]{"items", "collection"}, itemsUri),
                            new Link(new String[]{"related"}, new String[]{"movements", "collection"}, movementsUri),
                            new Link(new String[]{"related"}, new String[]{"house-allergies", "collection"}, allergiesUri),
                            new Link(new String[]{"related"}, new String[]{"lists", "collection"}, listsUri),
                            new Link(new String[]{"related"}, new String[]{"storages", "collection"}, storagesUri)});
        }
        return entities;
    }

    private Link[] initLinks(String username) {
        //URIs
        String userUri = UriBuilderUtils.buildUserUri(username);
        String userHousesUri = UriBuilderUtils.buildUserHousesUri(username);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, userHousesUri);
        //Link-related-user
        Link userLink = new Link(new String[]{"related"}, new String[]{"user"}, userUri);

        return new Link[]{self, userLink};

    }
}
