package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userhouse")
public class UserHouse {

    @EmbeddedId
    private UserHouseId id;

    @Basic
    @Column(name = "userhouse_administrator")
    private Boolean userhouseAdministrator;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne
    @JoinColumn(name = "users_username", referencedColumnName = "users_username", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersUsername;

    public UserHouseId getId() {
        return id;
    }

    public void setId(UserHouseId id) {
        this.id = id;
    }

    public Boolean getUserhouseAdministrator() {
        return userhouseAdministrator;
    }

    public void setUserhouseAdministrator(Boolean userhouseAdministrator) {
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
