package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Query;
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
        String indexUri = UriBuilderUtils.buildIndexUri();
        String categoriesUri = UriBuilderUtils.buildCategoriesUri();

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("index", indexUri)
        };

        // Items
        Item[] items = mapItems(categories);

        Query[] queries = new Query[]{
                new Query(categoriesUri, "search", "Search by name", new Data[]{
                        new Data("name", null, "Name")
                })
        };

        return new Collection(version, categoriesUri, links, items, queries);
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
