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
        this.actions = initActions();
        this.links = initLinks();
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(java.util.List<List> userLists) {
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
            properties.put("house-id", id.getHouseId());
            properties.put("list-id", id.getListId());
            properties.put("list-name", userList.getList().getListName());
            properties.put("user-username", userList.getUsersUsername());
            properties.put("list-shareable", userList.getListShareable());

            String userListUri = UriBuilderUtils.buildUserListsUri(username);
            entities[i] = new Entity(
                    new String[]{"user-list"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"user-list"}, userListUri)});
        }
        return entities;
    }

    private Action[] initActions(long houseId) {
        // Type
        String type = "application/json";

        // URIs
        String listsUri = UriBuilderUtils.buildListsUri(houseId);

        // POST List
        Action postList = new Action(
                "add-list",
                "Add List",
                Method.POST,
                listsUri,
                type,
                new Field[]{
                        new Field("list-name", Field.Type.number, null, "Name"),
                        new Field("list-shareable", Field.Type.bool, null, "Shareable")
                }
        );

        return new Action[]{postList};
    }

    private Link[] initLinks(long houseId) {
        // URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String listsUri = UriBuilderUtils.buildListsUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, listsUri);
        //Link-related-house
        Link houseLink = new Link(new String[]{"related"}, new String[]{"house"}, houseUri);

        return new Link[]{self, houseLink};
    }
}
