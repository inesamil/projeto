package pt.isel.ps.gis.model.outputModel.jsonObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.model.ListProduct;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListProductJsonObject {
    @JsonProperty(value = "house-id")
    private final Long houseId;
    @JsonProperty(value = "list-id")
    private final Short listId;
    @JsonProperty(value = "product-id")
    private final Integer productId;
    @JsonProperty(value = "product-name")
    private final String productName;
    @JsonProperty(value = "list-product-brand")
    private final String productBrand;
    @JsonProperty(value = "list-product-quantity")
    private final Short productQuantity;

    // Ctor

    public ListProductJsonObject(Long houseId, Short listId, Integer productId, String productName, String productBrand, Short productQuantity) {
        this.houseId = houseId;
        this.listId = listId;
        this.productId = productId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productQuantity = productQuantity;
    }
}
