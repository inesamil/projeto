package pt.isel.ps.gis.model.requestParams;

public class ListRequestParam {

    private Boolean system;
    private String user;
    private Boolean shareable;

    public boolean isNull() {
        return system == null && user == null && shareable == null;
    }

    public boolean getSystem() {
        if (system == null)
            return false;
        return system;
    }

    public String getUser() {
        return user;
    }

    public boolean getShareable() {
        if (shareable == null)
            return false;
        return shareable;
    }
}
