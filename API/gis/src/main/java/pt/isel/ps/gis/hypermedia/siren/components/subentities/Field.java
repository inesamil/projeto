package pt.isel.ps.gis.hypermedia.siren.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {

    public enum Type {
        hidden, text, search, tel, url, email, password, datetime,
        date, month, week, time, datetimeLocal, number, range, color,
        checkbox, radio, file
    }

    private String name;
    private Type type;
    private Object value;

    public Field(String name, Type type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
