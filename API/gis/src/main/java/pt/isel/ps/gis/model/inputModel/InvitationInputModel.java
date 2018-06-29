package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationInputModel {

    @JsonProperty("house-id")
    private Long houseId;

    @JsonProperty("user-username")
    private String username;

    public Long getHouseId() {
        return houseId;
    }

    public String getUsername() {
        return username;
    }
}
