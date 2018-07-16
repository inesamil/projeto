package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {

    /**
     * COLUNAS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Short roleId;

    @Basic
    @Column(name = "role_name", length = RestrictionsUtils.ROLE_NAME_MAX_LENGTH, nullable = false)
    private String roleName;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleByRoleId")
    private Collection<UserRole> usersroleByRoleId;

    /**
     * CONSTRUTORES
     */
    protected Role() {
    }

    public Role(Short roleId, String roleName) throws EntityException {
        setRoleId(roleId);
        setRoleName(roleName);
    }

    public Role(String roleName) throws EntityException {
        setRoleName(roleName);
    }

    /**
     * GETTERS E SETTERS
     */
    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) throws EntityException {
        ValidationsUtils.validateRoleId(roleId);
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) throws EntityException {
        ValidationsUtils.validateRoleName(roleName);
        this.roleName = roleName;
    }

    public Collection<UserRole> getUsersroleByRoleId() {
        return usersroleByRoleId;
    }

    public void setUsersroleByRoleId(Collection<UserRole> usersroleByRoleId) {
        this.usersroleByRoleId = usersroleByRoleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role that = (Role) obj;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }
}
