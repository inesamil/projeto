package pt.isel.ps.gis.hypermedia.siren.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {

    private String name;
    private String title;
    private Method method;
    private String href;
    private String type;
    private Field[] fields;

    public Action(String name, String title, Method method, String href, String type, Field[] fields) {
        this.name = name;
        this.title = title;
        this.method = method;
        this.href = href;
        this.type = type;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Method getMethod() {
        return method;
    }

    public String getHref() {
        return href;
    }

    public String getType() {
        return type;
    }

    public Field[] getFields() {
        return fields;
    }
}
