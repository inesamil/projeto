package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.ListProduct;
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
    public ListOutputModel(String username, List list) {
        this.klass = initKlass();
        this.properties = initProperties(list);
        this.entities = initEntities(list);
        this.actions = initActions(list);
        this.links = initLinks(username, list);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(List list) {
        HashMap<String, Object> properties = new HashMap<>();
        String listType = list.getListType();
        properties.put("house-id", list.getId().getHouseId());
        properties.put("house-name", list.getHouseByHouseId().getHouseName());
        properties.put("list-id", list.getId().getListId());
        properties.put("list-name", list.getListName());
        properties.put("list-type", listType);
        if (listType.equals("system"))
            return properties;
        properties.put("user-username", list.getUserlist().getUsersByUsersId().getUsersUsername());
        properties.put("list-shareable", list.getUserlist().getListShareable());
        return properties;
    }

    private Entity[] initEntities(List list) {
        // Products
        Entity[] entities = new Entity[list.getListproducts().size()];
        int i = 0;
        for (ListProduct listProduct : list.getListproducts()) {
            HashMap<String, Object> properties = new HashMap<>();

            long houseId = listProduct.getId().getHouseId();
            short listId = listProduct.getId().getListId();
            int productId = listProduct.getId().getProductId();

            properties.put("house-id", houseId);
            properties.put("list-id", listId);
            properties.put("product-id", productId);
            properties.put("product-name", listProduct.getProduct().getProductName());
            properties.put("list-product-brand", listProduct.getListproductBrand());
            properties.put("list-product-quantity", listProduct.getListproductQuantity());

            String listProductUri = UriBuilderUtils.buildListProductUri(houseId, listId, productId);

            // PUT ListProduct
            Action updateListProduct = new Action(
                    "update-list-product",
                    "Update List Product",
                    Method.PUT,
                    listProductUri,
                    "application/json",
                    new Field[]{
                            new Field("product-id", Field.Type.number, null, "Product Id"),
                            new Field("brand", Field.Type.text, null, "Brand"),
                            new Field("quantity", Field.Type.number, null, "Quantity")
                    }
            );

            // DELETE ListProduct
            Action deleteListProduct = new Action(
                    "remove-list-product",
                    "Remove List Product",
                    Method.DELETE,
                    listProductUri,
                    null,
                    null
            );

            entities[i++] = new Entity(
                    new String[]{"list-products", "collection"},
                    new String[]{"collection"},
                    properties,
                    new Action[]{updateListProduct},
                    null);

        }
        return entities;
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
        String productsListUri = UriBuilderUtils.buildListProductstUri(houseId, listId);

        // POST ListProduct
        Action addListProduct = new Action(
                "add-list-product",
                "Add List Product",
                Method.POST,
                productsListUri,
                type,
                new Field[]{
                        new Field("product-id", Field.Type.number, null, "Product Id"),
                        new Field("brand", Field.Type.text, null, "Brand"),
                        new Field("quantity", Field.Type.number, null, "Quantity")
                }
        );

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

        return new Action[]{addListProduct, updateList, deleteList};
    }

    private Link[] initLinks(String username, List list) {
        Long houseId = list.getId().getHouseId();
        Short listId = list.getId().getListId();

        // Link-self
        String listUri = UriBuilderUtils.buildListUri(houseId, listId);
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS}, listUri);

        //Link-related-lists
        String listsUri = UriBuilderUtils.buildUserListsUri(username);
        Link listsLink = new Link(new String[]{"related"}, new String[]{"lists", "collection"}, listsUri);

        return new Link[]{self, listsLink};
    }
}
