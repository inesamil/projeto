package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockItemInputModel {

    @JsonProperty(value = "category-id", required = true)
    private Integer categoryId;

    @JsonProperty(value = "product-id", required = true)
    private Integer productId;

    @JsonProperty(value = "stock-item-brand", required = true)
    private String brand;

    @JsonProperty(value = "stock-item-conservation-storage", required = true)
    private String conservationStorage;

    @JsonProperty(value = "stock-item-description")
    private String description = null;

    @JsonProperty(value = "stock-item-quantity", required = true)
    private Short quantity;

    @JsonProperty(value = "stock-item-segment", required = true)
    private String segment;

    @JsonProperty(value = "stock-item-variety", required = true)
    private String variety;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getBrand() {
        return brand;
    }

    public String getConservationStorage() {
        return conservationStorage;
    }

    public String getDescription() {
        return description;
    }

    public Short getQuantity() {
        return quantity;
    }

    public String getSegment() {
        return segment;
    }

    public String getVariety() {
        return variety;
    }
}
