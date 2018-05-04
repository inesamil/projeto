package pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

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
