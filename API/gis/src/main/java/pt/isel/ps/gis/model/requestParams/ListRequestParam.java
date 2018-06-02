package pt.isel.ps.gis.model.requestParams;

public class ListRequestParam {

    private Long[] housesIds;
    private Boolean system;
    private Boolean user;
    private Boolean shareable;

    public ListRequestParam(Long[] housesIds, Boolean system, Boolean user, Boolean shareable) {
        this.housesIds = housesIds;
        this.system = system;
        this.user = user;
        this.shareable = shareable;
    }

    public boolean isNull() {
        return housesIds == null && system == null && user == null && shareable == null;
    }

    public Long[] getHousesIds() {
        return housesIds;
    }

    public Boolean getSystem() {
        return system;
    }

    public Boolean getUser() {
        return user;
    }

    public Boolean getShareable() {
        return shareable;
    }
}
