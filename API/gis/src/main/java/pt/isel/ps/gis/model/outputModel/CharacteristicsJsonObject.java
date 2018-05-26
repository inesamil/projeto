package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.model.Characteristics;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacteristicsJsonObject {
    @JsonProperty(value = "babies-number")
    private final short babiesNumber;
    @JsonProperty(value = "children-number")
    private final short childrenNumber;
    @JsonProperty(value = "adults-number")
    private final short adultsNumber;
    @JsonProperty(value = "seniors-number")
    private final short seniorsNumber;

    // Ctor
    public CharacteristicsJsonObject(Characteristics characteristics) {
        this.babiesNumber = characteristics.getHouse_babiesNumber();
        this.childrenNumber = characteristics.getHouse_childrenNumber();
        this.adultsNumber = characteristics.getHouse_adultsNumber();
        this.seniorsNumber = characteristics.getHouse_seniorsNumber();
    }
}
