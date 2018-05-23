package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "links"})
public class ProductOutputModel {

    private final static String ENTITY_CLASS = "product";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Link[] links;

    // Ctor
    public ProductOutputModel(Product product) {
        this.klass = initKlass();
        this.properties = initProperties(product);
        this.links = initLinks(product);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(Product product) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("category-id", product.getId().getCategoryId());
        properties.put("product-id", product.getId().getProductId());
        properties.put("product-name", product.getProductName());
        properties.put("product-edible", product.getProductEdible());
        properties.put("product-shelflifetime", product.getProductShelflife() + " " + product.getProductShelflifetimeunit());
        return properties;
    }

    private Link[] initLinks(Product product) {
        int categoryId = product.getId().getCategoryId();
        int productId = product.getId().getProductId();

        // URIs
        String productUri = UriBuilderUtils.buildProductUri(categoryId, productId);
        String productsCategoryUri = UriBuilderUtils.buildProductsCategoryUri(categoryId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS}, productUri);
        //Link-related-productsCategory
        Link productsCategoryLink = new Link(new String[]{"related"}, new String[]{"products-category", "collection"}, productsCategoryUri);
        return new Link[]{self, productsCategoryLink};
    }
}
