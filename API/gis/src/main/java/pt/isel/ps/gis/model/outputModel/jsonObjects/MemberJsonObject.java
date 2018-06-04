package pt.isel.ps.gis.model.outputModel.jsonObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberJsonObject {
    @JsonProperty(value = "house-id")
    private final Long houseId;
    @JsonProperty(value = "user-username")
    private final String username;
    @JsonProperty(value = "administrator")
    private final Boolean administrator;

    public MemberJsonObject(Long houseId, String username, Boolean administrator) {
        this.houseId = houseId;
        this.username = username;
        this.administrator = administrator;
    }
}
