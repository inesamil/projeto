package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsListOutputModel {

    @JsonProperty
    private final Collection collection;

    public ProductsListOutputModel(long houseId, short listId, List<Product> products) {
        this.collection = initCollection(houseId, listId, products);
    }

    private Collection initCollection(long houseId, short listId, List<Product> products) {
        //URIs
        String listUri = UriBuilderUtils.buildListUri(houseId, listId);
        String productsListUri = UriBuilderUtils.buildProductsListUri(houseId, listId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("list", listUri)
        };

        // Items
        Item[] items = mapItems(houseId, listId, products);

        return new Collection(version, productsListUri, links, items, null);
    }

    // The items does not support HTTP GET method, just HTTP PUT and DELETE.
    private Item[] mapItems(long houseId, short listId, List<Product> products) {
        int productsSize = products.size();
        Item[] items = new Item[productsSize];
        for (int i = 0; i < productsSize; i++) {
            Product product = products.get(i);
            int categoryId = product.getId().getCategoryId();
            int productId = product.getId().getProductId();
            items[i] = new Item(
                    UriBuilderUtils.buildProductListUri(houseId, listId, productId),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("list-id", listId, "List ID"),
                            new Data("category-id", categoryId, "Category ID"),
                            new Data("product-id", productId, "Product ID"),
                            new Data("product-name", product.getProductName(), "Name"),
                            new Data("product-edible", product.getProductEdible(), "Edible"),
                            new Data("product-shelflifetime", product.getProductShelflife() + " " +
                                    product.getProductShelflifetimeunit(), "Shelf Life Time")
                    },
                    new Link[]{
                            new Link("products-category", UriBuilderUtils.buildProductsCategoryUri(categoryId))
                    }
            );
        }
        return items;
    }
}
