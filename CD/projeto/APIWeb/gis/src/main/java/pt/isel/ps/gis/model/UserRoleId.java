package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRoleId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @Column(name = "role_id", nullable = false)
    private Short roleId;

    /**
     * CONSTRUTORES
     */
    protected UserRoleId() {
    }

    public UserRoleId(Long usersId, Short roleId) throws EntityException {
        setUsersId(usersId);
        setRoleId(roleId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) throws EntityException {
        ValidationsUtils.validateUserId(usersId);
        this.usersId = usersId;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) throws EntityException {
        ValidationsUtils.validateRoleId(roleId);
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRoleId that = (UserRoleId) obj;
        return Objects.equals(usersId, that.usersId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, roleId);
    }
}
