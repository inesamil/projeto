package pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Query {

    private final String href;
    private final String rel;
    private final String prompt;
    private final Data[] data;

    public Query(String href, String rel, String prompt, Data[] data) {
        this.href = href;
        this.rel = rel;
        this.prompt = prompt;
        this.data = data;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public String getPrompt() {
        return prompt;
    }

    public Data[] getData() {
        return data;
    }
}
