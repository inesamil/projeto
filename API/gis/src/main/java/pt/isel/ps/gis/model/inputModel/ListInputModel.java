package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListInputModel {

    @JsonProperty("list-name")
    private String name;

    @JsonProperty("list-shareable")
    private Boolean shareable;

    public String getName() {
        return name;
    }

    public Boolean getShareable() {
        return shareable;
    }
}
