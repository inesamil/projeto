package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvitationId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    /**
     * CONSTRUTORES
     */
    protected InvitationId() {
    }

    public InvitationId(Long usersId, Long houseId) throws EntityException {
        setUsersId(usersId);
        setHouseId(houseId);
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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InvitationId that = (InvitationId) obj;
        return Objects.equals(usersId, that.usersId) &&
                Objects.equals(houseId, that.houseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, houseId);
    }
}
