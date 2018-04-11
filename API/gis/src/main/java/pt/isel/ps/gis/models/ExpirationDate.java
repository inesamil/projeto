package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "expirationdate")
public class ExpirationDate {

    @EmbeddedId
    private ExpirationDateId id;

    @Basic
    @Column(name = "date_quantity", nullable = false)
    private Short dateQuantity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false)
    })
    private StockItem stockitem;

    @ManyToOne
    @JoinColumn(name = "date_date", referencedColumnName = "date_date", nullable = false)
    private Date dateByDateDate;

    public ExpirationDateId getId() {
        return id;
    }

    public void setId(ExpirationDateId id) {
        this.id = id;
    }

    public Short getDateQuantity() {
        return dateQuantity;
    }

    public void setDateQuantity(Short dateQuantity) {
        this.dateQuantity = dateQuantity;
    }

    public StockItem getStockitem() {
        return stockitem;
    }

    public void setStockitem(StockItem stockitem) {
        this.stockitem = stockitem;
    }

    public Date getDateByDateDate() {
        return dateByDateDate;
    }

    public void setDateByDateDate(Date dateByDateDate) {
        this.dateByDateDate = dateByDateDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ExpirationDate that = (ExpirationDate) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateQuantity, that.dateQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateQuantity);
    }
}
