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
public class ListsOutputModel {

    private final static String ENTITY_CLASS = "lists";

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

    public ListsOutputModel(long houseId, java.util.List<List> lists) {
        this.klass = initKlass();
        this.properties = initProperties(lists);
        this.entities = initEntities(houseId, lists);
        this.actions = initActions(houseId);
        this.links = initLinks(houseId);
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

    private Entity[] initEntities(long houseId, java.util.List<List> lists) {
        Entity[] entities = new Entity[lists.size()];
        for (int i = 0; i < lists.size(); ++i) {
            List list = lists.get(i);
            ListId listId = list.getId();

            HashMap<String, Object> properties = new HashMap<>();
            String listType = list.getListType();
            properties.put("house-id", houseId);
            properties.put("house-name", list.getHouseByHouseId().getHouseName());
            properties.put("list-id", listId.getListId());
            properties.put("list-name", list.getListName());
            properties.put("list-type", listType);
            if (listType.equals("user")) {
                properties.put("user-username", list.getUserlist().getUsersId());
                properties.put("list-shareable", list.getUserlist().getListShareable());
            }


            String listUri = UriBuilderUtils.buildListUri(houseId, listId.getListId());
            entities[i] = new Entity(
                    new String[]{"list"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"list"}, listUri)});
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
        // Link-self
        String listsUri = UriBuilderUtils.buildListsUri(houseId);
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, listsUri);

        //Link-related-house
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        Link houseLink = new Link(new String[]{"related"}, new String[]{"house"}, houseUri);

        return new Link[]{self, houseLink};
    }
}
