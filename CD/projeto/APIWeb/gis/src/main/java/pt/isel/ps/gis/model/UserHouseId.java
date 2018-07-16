package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHouseId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "users_id", nullable = false)
    private Long usersId;

    /**
     * CONSTRUTORES
     */
    protected UserHouseId() {
    }

    public UserHouseId(Long houseId, Long userId) throws EntityException {
        setHouseId(houseId);
        setUsersId(userId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) throws EntityException {
        ValidationsUtils.validateUserId(usersId);
        this.usersId = usersId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserHouseId that = (UserHouseId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(usersId, that.usersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, usersId);
    }
}
