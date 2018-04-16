package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "date")
public class Date {

    /**
     * COLUNAS
     */
    @Id
    @Column(name = "date_date", nullable = false)
    private Timestamp dateDate;

    /**
     * COLEÇÕES
     */
    @OneToMany(mappedBy = "dateByDateDate")
    private Collection<ExpirationDate> expirationdatesByDateDate;

    /**
     * CONSTRUTORES
     */
    protected Date() {
    }

    public Date(String date) throws EntityException {
        setDateDate(date);
    }

    /**
     * GETTERS E SETTERS
     */
    public String getDateDate() {
        return DateUtils.convertDateFormat(this.dateDate);
    }

    public void setDateDate(String date) throws EntityException {
        ValidationsUtils.validateDate(date);
        date += " 00:00:00";    // JDBC timestamp escape format: yyyy-[m]m-[d]d hh:mm:ss[.f...].
        this.dateDate = Timestamp.valueOf(date);
    }

    public Collection<ExpirationDate> getExpirationdatesByDateDate() {
        return expirationdatesByDateDate;
    }

    public void setExpirationdatesByDateDate(Collection<ExpirationDate> expirationdatesByDateDate) {
        this.expirationdatesByDateDate = expirationdatesByDateDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date that = (Date) obj;
        return Objects.equals(dateDate, that.dateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDate);
    }
}
