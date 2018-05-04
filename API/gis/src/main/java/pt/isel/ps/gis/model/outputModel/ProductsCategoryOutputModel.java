package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Query;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsCategoryOutputModel {

    @JsonProperty
    private final Collection collection;

    // Need categoryId field because if products is empty, cannot know which is the categoryId
    public ProductsCategoryOutputModel(int categoryId, List<Product> products) {
        this.collection = initCollection(categoryId, products);
    }

    private Collection initCollection(int categoryId, List<Product> products) {
        //URIs
        String categoryUri = UriBuilderUtils.buildCategoryUri(categoryId);
        String productsCategoryUri = UriBuilderUtils.buildProductsCategoryUri(categoryId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("category", categoryUri)
        };

        // Items
        Item[] items = mapItems(categoryId, products);

        Query[] queries = new Query[]{
                new Query(productsCategoryUri, "search", "Search by name", new Data[]{
                        new Data("name", null, "Name")
                })
        };

        return new Collection(version, productsCategoryUri, links, items, queries);
    }

    private Item[] mapItems(int categoryId, List<Product> products) {
        int productsSize = products.size();
        Item[] items = new Item[productsSize];
        for (int i = 0; i < productsSize; i++) {
            Product product = products.get(i);
            int productId = product.getId().getProductId();
            items[i] = new Item(
                    UriBuilderUtils.buildProductUri(categoryId, productId),
                    new Data[]{
                            new Data("product-id", productId, "ID"),
                            new Data("product-name", product.getProductName(), "Name"),
                            new Data("product-edible", product.getProductEdible(), "Edible"),
                            new Data("product-shelflifetime", product.getProductShelflife() + " " +
                                    product.getProductShelflifetimeunit(), "Shelf Life Time")
                    },
                    null
            );
        }
        return items;
    }
}
