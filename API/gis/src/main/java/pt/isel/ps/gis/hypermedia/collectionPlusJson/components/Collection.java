package pt.isel.ps.gis.hypermedia.collectionPlusJson.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Query;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Template;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection {

    private final String version;
    private final String href;
    private final Link[] links;
    private final Item[] items;
    private final Query[] queries;
    private final Template template;

    public Collection(String version, String href, Link[] links, Item[] items, Query[] queries, Template template) {
        this.version = version;
        this.href = href;
        this.links = links;
        this.items = items;
        this.queries = queries;
        this.template = template;
    }

    public Collection(String version, String href, Link[] links, Item[] items, Template template) {
        this(version, href, links, items, null, template);
    }

    public Collection(String version, String href, Link[] links, Item[] items) {
        this(version, href, links, items, null, null);
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

    public Query[] getQueries() {
        return queries;
    }

    public Template getTemplate() {
        return template;
    }
}
