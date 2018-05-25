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
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class ProductsCategoryOutputModel {

    private final static String ENTITY_CLASS = "products-category";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    // Need categoryId field because if products is empty, cannot know which is the categoryId
    public ProductsCategoryOutputModel(int categoryId, List<Product> products) {
        this.klass = initKlass();
        this.properties = initProperties(products);
        this.entities = initEntities(products);
        this.links = initLinks(categoryId);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<Product> products) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", products.size());

        return properties;
    }

    private Entity[] initEntities(List<Product> products) {
        Entity[] entities = new Entity[products.size()];
        for (int i = 0; i < products.size(); ++i) {
            Product productsCategory = products.get(i);
            int productId = productsCategory.getId().getProductId();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("product-id", productId);
            properties.put("product-name", productsCategory.getProductName());
            properties.put("product-edible", productsCategory.getProductEdible());
            properties.put("product-shelflifetime", productsCategory.getProductShelflife() + " " + productsCategory.getProductShelflifetimeunit());

            String productUri = UriBuilderUtils.buildProductUri(productsCategory.getCategoryByCategoryId().getCategoryId(), productId);

            entities[i] = new Entity(
                    new String[]{"product"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"},  new String[]{"product"}, productUri)});
        }
        return entities;
    }

    private Link[] initLinks(int categoryId) {
        // URIs
        String categoryUri = UriBuilderUtils.buildCategoryUri(categoryId);
        String productsCategoryUri = UriBuilderUtils.buildProductsCategoryUri(categoryId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, productsCategoryUri);
        //Link-related-category
        Link categoryLink = new Link(new String[]{"related"}, new String[]{"category"}, categoryUri);

        return new Link[]{self, categoryLink};
    }
}
