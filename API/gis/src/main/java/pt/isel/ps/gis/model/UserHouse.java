package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userhouse")
public class UserHouse {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private UserHouseId id;

    @Basic
    @Column(name = "userhouse_administrator")
    private Boolean userhouseAdministrator;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne
    @JoinColumn(name = "users_username", referencedColumnName = "users_username", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersUsername;

    /**
     * CONSTRUTORES
     */
    protected UserHouse() {
    }

    public UserHouse(Long houseId, String username, Boolean userhouseAdministrator) throws EntityException {
        setId(houseId, username);
        setUserhouseAdministrator(userhouseAdministrator);
    }

    /**
     * GETTERS E SETTERS
     */
    public UserHouseId getId() {
        return id;
    }

    public void setId(UserHouseId id) {
        this.id = id;
    }

    public void setId(Long houseId, String username) throws EntityException {
        setId(new UserHouseId(houseId, username));
    }

    public Boolean getUserhouseAdministrator() {
        return userhouseAdministrator;
    }

    public void setUserhouseAdministrator(Boolean userhouseAdministrator) throws EntityException {
        ValidationsUtils.validateUserHouseAdministrator(userhouseAdministrator);
        this.userhouseAdministrator = userhouseAdministrator;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    public Users getUsersByUsersUsername() {
        return usersByUsersUsername;
    }

    public void setUsersByUsersUsername(Users usersByUsersUsername) {
        this.usersByUsersUsername = usersByUsersUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserHouse that = (UserHouse) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(userhouseAdministrator, that.userhouseAdministrator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userhouseAdministrator);
    }
}
