package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "date")
public class Date {

    @Id
    @Column(name = "date_date")
    private Timestamp dateDate;

    @OneToMany(mappedBy = "dateByDateDate")
    private Collection<ExpirationDate> expirationdatesByDateDate;

    public Timestamp getDateDate() {
        return dateDate;
    }

    public void setDateDate(Timestamp dateDate) {
        this.dateDate = dateDate;
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
