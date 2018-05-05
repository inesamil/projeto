package pt.isel.ps.gis.hypermedia.jsonHome.components.members;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum Method {
    GET, PUT, POST, DELETE, PATCH
}
