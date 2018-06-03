package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListInputModel {
    //TODO: isto fica aqui :/
    @JsonProperty("house-id")
    private Long houseId;

    @JsonProperty("list-name")
    private String name;

    @JsonProperty("list-shareable")
    private Boolean shareable;

    public Long getHouseId() {
        return houseId;
    }

    public String getName() {
        return name;
    }

    public Boolean getShareable() {
        return shareable;
    }
}
