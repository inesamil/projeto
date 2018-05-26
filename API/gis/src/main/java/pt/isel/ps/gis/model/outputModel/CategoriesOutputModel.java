package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "links"})
public class CategoriesOutputModel {

    private final static String ENTITY_CLASS = "categories";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Link[] links;

    public CategoriesOutputModel(List<Category> categories) {
        this.klass = initKlass();
        this.properties = initProperties(categories);
        this.entities = initEntities(categories);
        this.links = initLinks();
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<Category> categories) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", categories.size());

        return properties;
    }

    private Entity[] initEntities(List<Category> categories) {
        Entity[] entities = new Entity[categories.size()];
        for (int i = 0; i < categories.size(); ++i) {
            Category category = categories.get(i);
            String categoryUri = UriBuilderUtils.buildCategoryUri(category.getCategoryId());

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("category-id", category.getCategoryId());
            properties.put("category-name", category.getCategoryName());

            entities[i] = new Entity(
                    new String[]{"category"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"category", "item"}, categoryUri)});
        }
        return entities;
    }

    private Link[] initLinks() {
        //URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String categoriesUri = UriBuilderUtils.buildCategoriesUri();

        //Link-index
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);
        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, categoriesUri);


        return new Link[]{indexLink, self};
    }
}
