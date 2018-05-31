package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class UserListsOutputModel {

    private final static String ENTITY_CLASS = "user-lists";

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

    public UserListsOutputModel(String username, List<UserList> lists) {
        this.klass = initKlass();
        this.properties = initProperties(lists);
        this.entities = initEntities(username, lists);
        this.actions = initActions(username);
        this.links = initLinks(username);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(java.util.List<UserList> userLists) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", userLists.size());

        return properties;
    }

    private Entity[] initEntities(String username, List<UserList> userLists) {
        Entity[] entities = new Entity[userLists.size()];
        for (int i = 0; i < userLists.size(); ++i) {
            UserList userList = userLists.get(i);
            UserListId id = userList.getId();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("list-id", id.getListId());
            properties.put("list-name", userList.getList().getListName());
            properties.put("user-username", username);
            properties.put("list-shareable", userList.getListShareable());

            String userListsUri = UriBuilderUtils.buildUserListsUri(username);
            entities[i] = new Entity(
                    new String[]{"user-list"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"user-list"}, userListsUri)});
        }
        return entities;
    }

    private Action[] initActions(String username) {
        // Type
        String type = "application/json";

        // URIs
        String userListsUri = UriBuilderUtils.buildUserListsUri(username);

        // POST List
        Action postUserList = new Action(
                "add-list",
                "Add List",
                Method.POST,
                userListsUri,
                type,
                new Field[]{
                        new Field("list-name", Field.Type.number, null, "Name"),
                        new Field("list-shareable", Field.Type.bool, null, "Shareable")
                }
        );

        return new Action[]{postUserList};
    }

    private Link[] initLinks(String username) {
        // URIs
        String userUri = UriBuilderUtils.buildUserUri(username);
        String listsUri = UriBuilderUtils.buildUserListsUri(username);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, listsUri);
        //Link-related-house
        Link houseLink = new Link(new String[]{"related"}, new String[]{"user"}, userUri);

        return new Link[]{self, houseLink};
    }
}
