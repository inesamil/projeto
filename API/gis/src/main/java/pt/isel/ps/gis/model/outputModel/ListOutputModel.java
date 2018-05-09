package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class ListOutputModel {

    private final static String ENTITY_CLASS = "list";

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
    public ListOutputModel(List list) {
        this.klass = initKlass();
        this.properties = initProperties(list);
        this.entities = initEntities(list);
        this.actions = initActions(list);
        this.links = initLinks(list);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(List list) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", list.getId().getHouseId());
        properties.put("list-id", list.getId().getListId());
        properties.put("list-name", list.getListName());
        if (list.getListType().equals("system"))
            return properties;
        properties.put("user-username", list.getUserlist().getUsersUsername());
        properties.put("list-shareable", list.getUserlist().getListShareable());
        return properties;
    }

    private Entity[] initEntities(List list) {
        long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // URIs
        String listsUri = UriBuilderUtils.buildListsUri(houseId);
        String productsListUri = UriBuilderUtils.buildProductsListUri(houseId, listId);

        // Subentities
        Entity lists = new Entity(new String[]{"lists", "collection"}, new String[]{"lists"}, listsUri);
        Entity productsList = new Entity(new String[]{"products-list", "collection"}, new String[]{"products-list"},
                productsListUri);

        return new Entity[]{lists, productsList};
    }

    private Action[] initActions(List list) {
        if (list.getListType().equals("system"))
            return new Action[]{};

        Long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // Type
        String type = "application/x-www-form-urlencoded";

        // URIs
        String userListUri = UriBuilderUtils.buildUserListUri(houseId, listId);

        // PUT List
        Action putList = new Action(
                "update-list",
                "Update List",
                Method.PUT,
                userListUri,
                type,
                new Field[]{
                        new Field("list-name", Field.Type.number, null),
                        new Field("list-shareable", Field.Type.bool, null)
                }
        );

        // DELETE List
        Action deleteList = new Action(
                "delete-list",
                "Delete List",
                Method.DELETE,
                userListUri,
                type,
                null
        );

        return new Action[]{putList, deleteList};
    }

    private Link[] initLinks(List list) {
        Long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // URIs
        String listUri = UriBuilderUtils.buildListUri(houseId, listId);

        // Link-self
        Link self = new Link(new String[]{"self"}, listUri);

        return new Link[]{self};
    }
}
