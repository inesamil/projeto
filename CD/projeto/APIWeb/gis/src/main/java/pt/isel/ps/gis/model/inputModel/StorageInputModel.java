package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StorageInputModel {

    @JsonProperty(value = "storage-name")
    private String name;

    @JsonProperty(value = "storage-minimum-temperature")
    private Float minimumTemperature;

    @JsonProperty(value = "storage-maximum-temperature")
    private Float maximumTemperature;

    public String getName() {
        return name;
    }

    public Float getMinimumTemperature() {
        return minimumTemperature;
    }

    public Float getMaximumTemperature() {
        return maximumTemperature;
    }
}
