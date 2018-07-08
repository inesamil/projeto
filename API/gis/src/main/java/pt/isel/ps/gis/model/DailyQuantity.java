package pt.isel.ps.gis.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "dailyquantity")
public class DailyQuantity {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private DailyQuantityId id;

    @Basic
    @Column(name = "dailyquantity_quantity", nullable = false)
    private Short dailyquantity_quantity;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false, insertable = false, updatable = false)
    })
    private StockItem stockitem;

    /**
     * CONSTRUTORES
     */
    protected DailyQuantity() {
    }

    public DailyQuantity(DailyQuantityId id, Short dailyquantity_quantity) {
        this.id = id;
        this.dailyquantity_quantity = dailyquantity_quantity;
    }

    public DailyQuantity(Long houseId, String stockitemSku, Date dailyquantityDate, Short dailyquantity_quantity) {
        this.dailyquantity_quantity = dailyquantity_quantity;
    }

    /**
     * GETTERS E SETTERS
     */
    public DailyQuantityId getId() {
        return id;
    }

    public void setId(DailyQuantityId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockitemSku, Date dailyquantityDate) {
        setId(new DailyQuantityId(houseId, stockitemSku, dailyquantityDate));
    }

    public Short getDailyquantity_quantity() {
        return dailyquantity_quantity;
    }

    public void setDailyquantity_quantity(Short dailyquantity_quantity) {
        this.dailyquantity_quantity = dailyquantity_quantity;
    }

    public StockItem getStockitem() {
        return stockitem;
    }

    public void setStockitem(StockItem stockitem) {
        this.stockitem = stockitem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DailyQuantity that = (DailyQuantity) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(dailyquantity_quantity, that.dailyquantity_quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dailyquantity_quantity);
    }
}
