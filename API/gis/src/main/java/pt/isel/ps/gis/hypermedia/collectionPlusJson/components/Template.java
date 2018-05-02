package pt.isel.ps.gis.hypermedia.collectionPlusJson.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Template {

    private final Data[] data;

    public Template(Data[] data) {
        this.data = data;
    }

    public Data[] getData() {
        return data;
    }
}
