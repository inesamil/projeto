package pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

    private final String name;
    private final Object value;
    private final String prompt;

    public Data(String name, Object value, String prompt) {
        this.name = name;
        this.value = value;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getPrompt() {
        return prompt;
    }
}


