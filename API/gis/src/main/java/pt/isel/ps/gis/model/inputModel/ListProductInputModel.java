package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListProductInputModel {

    @JsonProperty("list-product-brand")
    private String brand;

    @JsonProperty("list-product-quantity")
    private Short quantity;

    public String getBrand() {
        return brand;
    }

    public Short getQuantity() {
        return quantity;
    }
}
