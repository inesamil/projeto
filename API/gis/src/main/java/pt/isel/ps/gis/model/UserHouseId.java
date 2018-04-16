package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
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

    @Column(name = "users_username", length = RestrictionsUtils.USER_USERNAME_MAX_LENGTH, nullable = false)
    private String usersUsername;

    /**
     * CONSTRUTORES
     */
    protected UserHouseId() {
    }

    public UserHouseId(Long houseId, String username) throws EntityException {
        setHouseId(houseId);
        setUsersUsername(username);
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

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.usersUsername = usersUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserHouseId that = (UserHouseId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(usersUsername, that.usersUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, usersUsername);
    }
}
