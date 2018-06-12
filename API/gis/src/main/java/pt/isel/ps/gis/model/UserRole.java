package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usersrole")
public class UserRole {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private UserRoleId id;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "users_id", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false, insertable = false, updatable = false)
    private Role roleByRoleId;

    /**
     * CONSTRUTORES
     */
    protected UserRole() {
    }

    public UserRole(Long usersId, Short roleId) throws EntityException {
        setId(usersId, roleId);
    }

    /**
     * GETTERS E SETTERS
     */
    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public void setId(Long usersId, Short roleId) throws EntityException {
        setId(new UserRoleId(usersId, roleId));
    }

    public Users getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(Users usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRole that = (UserRole) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
