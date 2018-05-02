package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class CategoryOutputModel {

    private final static String ENTITY_CLASS = "category";

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
    public CategoryOutputModel(Category category) {
        this.klass = initKlass();
        this.properties = initProperties(category);
        this.entities = initEntities(category);
        this.actions = initActions(category);
        this.links = initLinks(category);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(Category category) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("category-id", category.getCategoryId());
        properties.put("category-name", category.getCategoryName());
        return properties;
    }

    private Entity[] initEntities(Category category) {
        int categoryId = category.getCategoryId();

        // URIs
        String productsCategoryUri = UriBuilderUtils.buildProductsCategoryUri(categoryId);
        String categoriesUri = UriBuilderUtils.buildCategoriesUri();

        // Subentities
        Entity productsCategory = new Entity(new String[]{"products-category", "collection"},
                new String[]{"products-category"}, productsCategoryUri);
        Entity categories = new Entity(new String[]{"categories", "collection"}, new String[]{"categories"}, categoriesUri);

        return new Entity[]{productsCategory, categories};
    }

    private Action[] initActions(Category category) {
        return new Action[]{};
    }

    private Link[] initLinks(Category category) {
        int categoryId = category.getCategoryId();

        // URIs
        String categoryUri = UriBuilderUtils.buildCategoryUri(categoryId);

        // Link-self
        Link self = new Link(new String[]{"self"}, categoryUri);

        return new Link[]{self};
    }
}
