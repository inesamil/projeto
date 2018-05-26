package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class ProductsListOutputModel {

    private final static String ENTITY_CLASS = "products-list";

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

    public ProductsListOutputModel(long houseId, short listId, List<ListProduct> listProducts) {
        this.klass = initKlass();
        this.properties = initProperties(listProducts);
        this.entities = initEntities(houseId, listId, listProducts);
        this.actions = initActions(houseId, listId);
        this.links = initLinks(houseId, listId);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<ListProduct> listProducts) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", listProducts.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, short listId, List<ListProduct> listProducts) {
        Entity[] entities = new Entity[listProducts.size()];
        for (int i = 0; i < listProducts.size(); ++i) {
            ListProduct listProduct = listProducts.get(i);
            Product product = listProduct.getProduct();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("list-id", listId);
            properties.put("category-id", product.getCategoryByCategoryId().getCategoryId());
            properties.put("product-id", product.getId().getProductId());
            properties.put("list-product-brand", listProduct.getListproductBrand());
            properties.put("list-product-quantity", listProduct.getListproductQuantity());

            String productUri = UriBuilderUtils.buildProductUri((int)houseId, listId);

            entities[i] = new Entity(
                    new String[]{"list-product"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"related"}, new String[]{"product"}, productUri)});
        }
        return entities;
    }

    private Action[] initActions(long houseId, short listId) {
        // Type
        String type = "application/json";

        // URIs
        String productsListUri = UriBuilderUtils.buildProductsListUri(houseId, listId);

        // PUT List
        Action putListProduct = new Action(
                "update-product",
                "Update product",
                Method.PUT,
                productsListUri,
                type,
                new Field[]{
                        new Field("list-product-brand", Field.Type.text, null, "Brand"),
                        new Field("list-product-quantity", Field.Type.number, null, "Quantity")
                }
        );

        // DELETE List Products
        Action deleteListProducts = new Action(
                "delete-list-products",
                "Delete List Products",
                Method.PUT,
                productsListUri,
                null,
                null
        );

        return new Action[]{putListProduct, deleteListProducts};
    }

    private Link[] initLinks(long houseId, short listId) {
        //URIs
        String listUri = UriBuilderUtils.buildListUri(houseId, listId);
        String productsListUri = UriBuilderUtils.buildProductsListUri(houseId, listId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, productsListUri);
        //Link-related-list
        Link listLink = new Link(new String[]{"related"}, new String[]{"list"}, listUri);

        return new Link[]{self, listLink};
    }
}
