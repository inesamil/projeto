package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation")
public class Invitation {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private InvitationId id;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "users_id", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    /**
     * CONSTRUTORES
     */
    protected Invitation() {
    }

    public Invitation(InvitationId id) {
        this.id = id;
    }

    public Invitation(Long usersId, Long houseId) throws EntityException {
        this(new InvitationId(usersId, houseId));
    }

    /**
     * GETTERS E SETTERS
     */
    public InvitationId getId() {
        return id;
    }

    public void setId(InvitationId id) {
        this.id = id;
    }

    public void setId(Long usersId, Long houseId) throws EntityException {
        setId(new InvitationId(usersId, houseId));
    }

    public Users getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(Users usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invitation that = (Invitation) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
