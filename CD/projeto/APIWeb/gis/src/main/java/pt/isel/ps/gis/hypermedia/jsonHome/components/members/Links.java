package pt.isel.ps.gis.hypermedia.jsonHome.components.members;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Links {

    private final String author;
    private final String describedBy;
    private final String license;

    public Links(String author, String describedBy, String license) {
        this.author = author;
        this.describedBy = describedBy;
        this.license = license;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescribedBy() {
        return describedBy;
    }

    public String getLicense() {
        return license;
    }
}
