package pt.isel.ps.gis.hypermedia.collectionPlusJson.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection {

    private final String version;
    private final String href;
    private final Link[] links;
    private final Item[] items;
    private final Template template;

    public Collection(String version, String href, Link[] links, Item[] items, Template template) {
        this.version = version;
        this.href = href;
        this.links = links;
        this.items = items;
        this.template = template;
    }

    public String getVersion() {
        return version;
    }

    public String getHref() {
        return href;
    }

    public Link[] getLinks() {
        return links;
    }

    public Item[] getItems() {
        return items;
    }

    public Template getTemplate() {
        return template;
    }
}
