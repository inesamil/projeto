package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllergyInputModel {

    @JsonProperty(value = "house-allergies-num")
    private Short allergicsNum;

    public Short getAllergicsNum() {
        return allergicsNum;
    }
}
