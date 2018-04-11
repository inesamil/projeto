package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHouseId implements Serializable {

    @Id
    @Column(name = "house_id")
    private Long houseId;

    @Id
    @Column(name = "users_username")
    private String usersUsername;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
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
