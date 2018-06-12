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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "users_id", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersId;

    /**
     * CONSTRUTORES
     */
    protected UserHouse() {
    }

    public UserHouse(Long houseId, Long userId, Boolean userhouseAdministrator) throws EntityException {
        setId(houseId, userId);
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

    public void setId(Long houseId, Long userId) throws EntityException {
        setId(new UserHouseId(houseId, userId));
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

    public Users getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(Users usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
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
