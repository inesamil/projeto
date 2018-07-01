package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class UsersOutputModel {

    private final static String ENTITY_CLASS = "users";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    public UsersOutputModel(List<Users> users) {
        this.klass = initKlass();
        this.properties = initProperties(users);
        this.entities = initEntities(users);
        this.links = initLinks();
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<Users> users) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", users.size());

        return properties;
    }

    private Entity[] initEntities(List<Users> users) {
        Entity[] entities = new Entity[users.size()];
        for (int i = 0; i < users.size(); ++i) {
            Users user = users.get(i);
            String username = user.getUsersUsername();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("user-username", user.getUsersUsername());
            properties.put("user-name", username);
            properties.put("user-email", user.getUsersEmail());
            properties.put("user-age", user.getUsersAge());

            // Type
            String type = "application/json";

            String userUri = UriBuilderUtils.buildUserUri(username);

            Action putUser = new Action(
                    "update-user",
                    "Update User",
                    Method.PUT,
                    userUri,
                    type,
                    new Field[]{
                            new Field("user-name", Field.Type.text, null, "Name"),
                            new Field("user-age", Field.Type.number, null, "Age")
                    }
            );

            // DELETE User
            Action deleteUser = new Action(
                    "delete-user",
                    "Delete User",
                    Method.DELETE,
                    userUri,
                    null,
                    null
            );

            entities[i] = new Entity(
                    new String[]{"user"},
                    new String[]{"item"},
                    properties,
                    new Action[]{putUser, deleteUser},
                    new Link[]{new Link(new String[]{"self"}, new String[]{"user"}, userUri)});
        }
        return entities;
    }

    private Link[] initLinks() {
        // Link-self
        String usersUri = UriBuilderUtils.buildUsersUri();
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, usersUri);

        //Link-index
        String indexUri = UriBuilderUtils.buildIndexUri();
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);

        return new Link[]{self, indexLink};
    }
}
