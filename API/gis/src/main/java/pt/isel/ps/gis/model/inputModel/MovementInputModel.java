package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovementInputModel {

    @JsonProperty(required = true)
    private String tag;
    @JsonProperty(value = "storage-id", required = true)
    private Short storageId;
    @JsonProperty(value = "movement-type", required = true)
    private Boolean type;

    public String getTag() {
        return tag;
    }

    public Short getStorageId() {
        return storageId;
    }

    public Boolean getType() {
        return type;
    }
}
