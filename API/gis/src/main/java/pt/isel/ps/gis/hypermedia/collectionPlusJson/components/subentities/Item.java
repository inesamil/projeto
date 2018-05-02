package pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    private final String href; // SHOULD
    private final Data[] data; // MAY
    private final Link[] links;   // MAY

    public Item(String href, Data[] data, Link[] links) {
        this.href = href;
        this.data = data;
        this.links = links;
    }

    public String getHref() {
        return href;
    }

    public Data[] getData() {
        return data;
    }

    public Link[] getLinks() {
        return links;
    }
}
