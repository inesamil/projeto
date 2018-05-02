package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.Category;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesOutputModel {

    @JsonProperty
    private final Collection collection;

    public CategoriesOutputModel(List<Category> categories) {
        this.collection = initCollection(categories);
    }

    private Collection initCollection(List<Category> categories) {
        //URIs
        String categoriesUri = UriBuilderUtils.buildCategoriesUri();

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{new Link("self", categoriesUri)};

        // Items
        Item[] items = mapItems(categories);

        return new Collection(version, categoriesUri, links, items, null);
    }

    private Item[] mapItems(List<Category> categories) {
        int categoriesSize = categories.size();
        Item[] items = new Item[categoriesSize];
        for (int i = 0; i < categoriesSize; i++) {
            Category category = categories.get(i);
            int categoryId = category.getCategoryId();
            items[i] = new Item(
                    UriBuilderUtils.buildCategoryUri(categoryId),
                    new Data[]{
                            new Data("category-id", category.getCategoryId(), "ID"),
                            new Data("category-name", category.getCategoryName(), "Name")
                    },
                    new Link[]{
                            new Link("products-category", UriBuilderUtils.buildProductsCategoryUri(categoryId))
                    }
            );
        }
        return items;
    }
}
