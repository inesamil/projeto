package pt.isel.ps.gis.hypermedia.jsonHome.components;

import pt.isel.ps.gis.hypermedia.jsonHome.components.members.Links;

public class Api {

    private final String title;
    private final Links links;

    public Api(String title, Links links) {
        this.title = title;
        this.links = links;
    }

    public String getTitle() {
        return title;
    }

    public Links getLinks() {
        return links;
    }
}
