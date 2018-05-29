package pt.isel.ps.gis.model.outputModel.jsonObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpirationDateJsonObject {
    @JsonProperty(value = "expiration-date")
    private final String expirationDate;
    @JsonProperty(value = "quantity")
    private final short quantity;

    // Ctor
    public ExpirationDateJsonObject(String expirationDate, short quantity) {
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }
}

