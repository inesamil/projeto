package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class UserOutputModel {

    private final static String ENTITY_CLASS = "user";

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
    public UserOutputModel(Users user) {
        this.klass = initKlass();
        this.properties = initProperties(user);
        this.entities = initEntities(user);
        this.actions = initActions(user);
        this.links = initLinks(user);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(Users user) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("user-username", user.getUsersUsername());
        properties.put("user-name", user.getUsersName());
        properties.put("user-email", user.getUsersEmail());
        properties.put("user-age", user.getUsersAge());
        return properties;
    }

    private Entity[] initEntities(Users user) {
        String username = user.getUsersUsername();

        // URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String userHousesUri = UriBuilderUtils.buildUserHousesUri(username);

        // Subentities
        Entity index = new Entity(new String[]{"index"}, new String[]{"index"}, indexUri);
        Entity userHouses = new Entity(new String[]{"user-houses", "collection"}, new String[]{"user-houses"},
                userHousesUri);
        return new Entity[]{index, userHouses};
    }

    private Action[] initActions(Users user) {
        String username = user.getUsersUsername();

        // Type
        String type = "application/x-www-form-urlencoded";

        // URIs
        String userUri = UriBuilderUtils.buildUserUri(username);

        // PUT User
        Action putUser = new Action(
                "update-user",
                "Update User",
                Method.PUT,
                userUri,
                type,
                new Field[]{
                        new Field("user-name", Field.Type.text, null),
                        new Field("user-age", Field.Type.number, null)
                }
        );

        // DELETE User
        Action deleteUser = new Action(
                "delete-user",
                "Delete User",
                Method.DELETE,
                userUri,
                type,
                null
        );

        return new Action[]{putUser, deleteUser};
    }

    private Link[] initLinks(Users user) {
        String username = user.getUsersUsername();

        // URIs
        String userUri = UriBuilderUtils.buildUserUri(username);

        // Link-self
        Link self = new Link(new String[]{"self"}, userUri);

        return new Link[]{self};
    }
}
