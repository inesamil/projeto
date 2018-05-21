package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.UserHouse;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class HouseholdOutputModel {

    private final static String ENTITY_CLASS = "household";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    public HouseholdOutputModel(long houseId, List<UserHouse> users) {
        this.klass = initKlass();
        this.properties = initProperties(users);
        this.entities = initEntities(houseId, users);
        this.links = initLinks(houseId);
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<UserHouse> users) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", users.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, List<UserHouse> users) {
        Entity[] entities = new Entity[users.size()];
        for (int i = 0; i < users.size(); ++i) {
            UserHouse userHouse = users.get(i);
            Users username = userHouse.getUsersByUsersUsername();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("user-username", username);
            properties.put("household-administrator", userHouse.getUserhouseAdministrator());

            String userUri = UriBuilderUtils.buildUserUri(username.getUsersUsername());
            entities[i] = new Entity(new String[]{"user"}, new String[]{"item"}, properties, null, userUri);
        }
        return entities;
    }

    private Link[] initLinks(long houseId) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String householdUri = UriBuilderUtils.buildHouseholdUri(houseId);

        //Link-related-house
        Link houseLink = new Link(new String[]{"house"}, new String[]{"related"}, houseUri);
        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, householdUri);

        return new Link[]{self, houseLink};
    }
}
