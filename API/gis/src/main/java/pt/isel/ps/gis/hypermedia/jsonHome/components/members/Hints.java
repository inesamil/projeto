package pt.isel.ps.gis.hypermedia.jsonHome.components.members;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hints {

    private final Method[] allow;
    private final HashMap<String, Object> formats;
    //public String[] acceptPatch;
    private final String[] acceptPost;
    private final String[] acceptPut;
    //public String[] acceptRanges;
    //public String[] acceptPrefer;
    //public String docs;
    //public String[] preconditionRequired;
    private final HashMap<String, Object>[] authSchemes;
    //public String status;

    public Hints(Method[] allow, HashMap<String, Object> formats, String[] acceptPost, String[] acceptPut, HashMap<String, Object>[] authSchemes) {
        this.allow = allow;
        this.formats = formats;
        this.acceptPost = acceptPost;
        this.acceptPut = acceptPut;
        this.authSchemes = authSchemes;
    }

    public Method[] getAllow() {
        return allow;
    }

    public HashMap<String, Object> getFormats() {
        return formats;
    }

    public String[] getAcceptPost() {
        return acceptPost;
    }

    public String[] getAcceptPut() {
        return acceptPut;
    }

    public HashMap<String, Object>[] getAuthSchemes() {
        return authSchemes;
    }
}
