package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.model.House;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Characteristics {
    @JsonProperty(value = "babies-number")
    private final short babiesNumber;
    @JsonProperty(value = "babies-number")
    private final short babiesNumber;
    @JsonProperty(value = "babies-number")
    private final short babiesNumber;
    @JsonProperty(value = "babies-number")
    private final short babiesNumber;

    // Ctor
    public Characteristics(pt.isel.ps.gis.model.Characteristics characteristics) {
        this.babiesNumber = characteristics.getHouse_babiesNumber();
    }
}
