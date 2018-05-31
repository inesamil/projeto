package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.outputModel.jsonObjects.ListProductJsonObject;
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
        // Products
        ListProductJsonObject[] products = new ListProductJsonObject[list.getListproducts().size()];
        int i = 0;
        for (ListProduct listProduct : list.getListproducts()) {
            products[i] = new ListProductJsonObject(
                    listProduct.getId().getHouseId(),
                    listProduct.getId().getListId(),
                    listProduct.getId().getProductId(),
                    listProduct.getProduct().getProductName(),
                    listProduct.getListproductBrand(),
                    listProduct.getListproductQuantity());
        }
        HashMap<String, Object> listProductsProperties = new HashMap<>();
        listProductsProperties.put("elements", products);

        Entity listProductsEntity = new Entity(
                new String[]{"list-products", "collection"},
                new String[]{"collection"},
                listProductsProperties,
                null,
                null);

        return new Entity[]{listProductsEntity};
    }

    private Action[] initActions(List list) {
        if (list.getListType().equals("system"))
            return new Action[]{};

        Long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // Type
        String type = "application/json";

        // URIs
        String userListUri = UriBuilderUtils.buildListUri(houseId, listId);

        // PUT List
        Action updateList = new Action(
                "update-list",
                "Update List",
                Method.PUT,
                userListUri,
                type,
                new Field[]{
                        new Field("list-name", Field.Type.number, null, "Name"),
                        new Field("list-shareable", Field.Type.bool, null, "Shareable")
                }
        );

        // DELETE List
        Action deleteList = new Action(
                "delete-list",
                "Delete List",
                Method.DELETE,
                userListUri,
                null,
                null
        );

        return new Action[]{updateList, deleteList};
    }

    private Link[] initLinks(List list) {
        Long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // URIs
        String listUri = UriBuilderUtils.buildListUri(houseId, listId);
        String listsUri = UriBuilderUtils.buildListsUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS}, listUri);
        //Link-related-lists
        Link listsLink = new Link(new String[]{"related"}, new String[]{"lists", "collection"}, listsUri);

        return new Link[]{self, listsLink};
    }
}
