package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation")
public class Invitation {
    //TODO: remover o boolean invitationAccepted
    /**
     * COLUNAS
     */
    @EmbeddedId
    private InvitationId id;

    @Basic
    @Column(name = "invitation_accepted", nullable = false, columnDefinition = "bool default false")
    private Boolean invitationAccepted;

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

    public Invitation(InvitationId id, Boolean invitationAccepted) throws EntityException {
        this.id = id;
        setInvitationAccepted(invitationAccepted);
    }

    public Invitation(InvitationId id) throws EntityException {
        this(id, false);
    }

    public Invitation(Long usersId, Long houseId, Boolean invitationAccepted) throws EntityException {
        this(new InvitationId(usersId, houseId), invitationAccepted);
    }

    public Invitation(Long usersId, Long houseId) throws EntityException {
        this(new InvitationId(usersId, houseId), false);
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

    public Boolean getInvitationAccepted() {
        return invitationAccepted;
    }

    public void setInvitationAccepted(Boolean invitationAccepted) throws EntityException {
        ValidationsUtils.validateInvitationAccepted(invitationAccepted);
        this.invitationAccepted = invitationAccepted;
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
        return Objects.equals(id, that.id) &&
                Objects.equals(invitationAccepted, that.invitationAccepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invitationAccepted);
    }
}
