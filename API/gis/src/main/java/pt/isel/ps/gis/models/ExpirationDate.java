package pt.isel.ps.gis.models;

import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "expirationdate")
public class ExpirationDate {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private ExpirationDateId id;

    @Basic
    @Column(name = "date_quantity", nullable = false)
    private Short dateQuantity;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false)
    })
    private StockItem stockitem;

    @ManyToOne
    @JoinColumn(name = "date_date", referencedColumnName = "date_date", nullable = false)
    private Date dateByDateDate;

    /**
     * CONSTRUTORES
     */
    protected ExpirationDate() {}

    public ExpirationDate(Long houseId, String stockItemSku, String expirationDate) throws IllegalArgumentException {
        setId(houseId, stockItemSku, expirationDate);
    }

    public ExpirationDate(Long houseId, String stockItemSku, String expirationDate, Short quantity) throws IllegalArgumentException {
        setId(houseId, stockItemSku, expirationDate);
        setDateQuantity(quantity);
    }

    /**
     * GETTERS E SETTERS
     */
    public ExpirationDateId getId() {
        return id;
    }

    public void setId(ExpirationDateId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockItemSku, String expirationDate) {
        setId(new ExpirationDateId(houseId, stockItemSku, expirationDate));
    }

    public Short getDateQuantity() {
        return dateQuantity;
    }

    public void setDateQuantity(Short quantity) throws IllegalArgumentException {
        ValidationsUtils.validateExpirationDateQuantity(quantity);
        this.dateQuantity = quantity;
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
