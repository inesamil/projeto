package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsListOutputModel {

    @JsonProperty
    private final Collection collection;

    public ProductsListOutputModel(long houseId, short listId, List<ListProduct> listProducts) {
        this.collection = initCollection(houseId, listId, listProducts);
    }

    private Collection initCollection(long houseId, short listId, List<ListProduct> listProducts) {
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
        Item[] items = mapItems(houseId, listId, listProducts);

        return new Collection(version, productsListUri, links, items);
    }

    // The items does not support HTTP GET method, just HTTP PUT and DELETE.
    private Item[] mapItems(long houseId, short listId, List<ListProduct> listProducts) {
        int listProductsSize = listProducts.size();
        Item[] items = new Item[listProductsSize];
        for (int i = 0; i < listProductsSize; i++) {
            ListProduct listProduct = listProducts.get(i);
            int categoryId = listProduct.getId().getCategoryId();
            int productId = listProduct.getId().getProductId();
            items[i] = new Item(
                    UriBuilderUtils.buildProductListUri(houseId, listId, productId),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("list-id", listId, "List ID"),
                            new Data("category-id", categoryId, "Category ID"),
                            new Data("product-id", productId, "Product ID"),
                            new Data("list-product-brand", listProduct.getListproductBrand(), "Brand"),
                            new Data("list-product-quantity", listProduct.getListproductQuantity(), "Quantity")
                    },
                    new Link[]{
                            new Link("listProducts-category", UriBuilderUtils.buildProductsCategoryUri(categoryId))
                    }
            );
        }
        return items;
    }
}
