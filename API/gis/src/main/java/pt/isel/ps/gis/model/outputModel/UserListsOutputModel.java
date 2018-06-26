package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListId;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
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

    public UserListsOutputModel(String username, java.util.List<List> lists) {
        this.klass = initKlass();
        this.properties = initProperties(lists);
        this.entities = initEntities(lists);
        this.actions = initActions(username);
        this.links = initLinks(username);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(java.util.List<List> lists) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", lists.size());

        return properties;
    }

    private Entity[] initEntities(java.util.List<pt.isel.ps.gis.model.List> lists) {
        Entity[] entities = new Entity[lists.size()];
        for (int i = 0; i < lists.size(); ++i) {
            List list = lists.get(i);
            ListId id = list.getId();
            Long houseId = id.getHouseId();
            Short listId = id.getListId();

            HashMap<String, Object> properties = new HashMap<>();
            String listType = list.getListType();
            properties.put("house-id", houseId);
            properties.put("house-name", list.getHouseByHouseId().getHouseName());
            properties.put("list-id", listId);
            properties.put("list-name", list.getListName());
            properties.put("list-type", listType);
            if (listType.equals("user")) {
                properties.put("user-username", list.getUserlist().getUsersByUsersId().getUsersUsername());
                properties.put("list-shareable", list.getUserlist().getListShareable());
            }

            // Type
            String type = "application/json";

            String listUri = UriBuilderUtils.buildListUri(houseId, listId);
            String productsListUri = UriBuilderUtils.buildListProductstUri(houseId, listId);

            entities[i] = new Entity(
                    new String[]{"list"},
                    new String[]{"item"},
                    properties,
                    new Action[]{new Action("update-list-product",
                            "Update List Products",
                            Method.PUT,
                            productsListUri,
                            type,
                            new Field[]{
                                    new Field("product-id", Field.Type.number, null, "Product Id"),
                                    new Field("brand", Field.Type.text, null, "Brand"),
                                    new Field("quantity", Field.Type.number, null, "Quantity")
                            })},
                    new Link[]{new Link(new String[]{"self"}, new String[]{"list"}, listUri)});
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
                        new Field("house-id", Field.Type.number, null, "House ID"),
                        new Field("list-name", Field.Type.number, null, "Name"),
                        new Field("list-shareable", Field.Type.bool, null, "Shareable")
                }
        );

        return new Action[]{postUserList};
    }

    private Link[] initLinks(String username) {
        // Link-self
        String listsUri = UriBuilderUtils.buildUserListsUri(username);
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, listsUri);

        //Link-related-house
        String userUri = UriBuilderUtils.buildUserUri(username);
        Link userLink = new Link(new String[]{"related"}, new String[]{"user"}, userUri);

        return new Link[]{self, userLink};
    }
}
