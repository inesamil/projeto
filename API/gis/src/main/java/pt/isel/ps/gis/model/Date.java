package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
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
    private java.sql.Date dateDate;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "dateByDateDate"
    )
    private Collection<ExpirationDate> expirationdatesByDateDate = new ArrayList<>();

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
        return DateUtils.convertDateFormat(dateDate);
    }

    public void setDateDate(String date) throws EntityException {
        ValidationsUtils.validateDate(date);
        this.dateDate = java.sql.Date.valueOf(date);
    }

    public Collection<ExpirationDate> getExpirationdatesByDateDate() {
        return expirationdatesByDateDate;
    }

    public void setExpirationdatesByDateDate(Collection<ExpirationDate> expirationdatesByDateDate) {
        this.expirationdatesByDateDate = expirationdatesByDateDate;
    }

    public void addExpirationDate(ExpirationDate expirationDate) {
        expirationdatesByDateDate.add(expirationDate);
        expirationDate.setDateByDateDate(this);
    }

    public void removeExpirationDate(ExpirationDate expirationDate) {
        expirationdatesByDateDate.remove(expirationDate);
        expirationDate.setDateByDateDate(null);
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
