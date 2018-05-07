package pt.isel.ps.gis.model.requestParams;

public class ListRequestParam {

    private Boolean system;
    private String user;
    private Boolean shareable;

    public ListRequestParam(Boolean system, String user, Boolean shareable) {
        this.system = system;
        this.user = user;
        this.shareable = shareable;
    }

    public boolean isNull() {
        return system == null && user == null && shareable == null;
    }

    public Boolean getSystem() {
        return system;
    }

    public String getUser() {
        return user;
    }

    public Boolean getShareable() {
        return shareable;
    }
}
