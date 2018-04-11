package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class ExpirationDateId implements Serializable {

    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "stockitem_sku", length = 128, nullable = false)
    private String stockitemSku;

    @Id
    @Column(name = "date_date", nullable = false)
    private Timestamp dateDate;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getStockitemSku() {
        return stockitemSku;
    }

    public void setStockitemSku(String stockitemSku) {
        this.stockitemSku = stockitemSku;
    }

    public Timestamp getDateDate() {
        return dateDate;
    }

    public void setDateDate(Timestamp dateDate) {
        this.dateDate = dateDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ExpirationDateId that = (ExpirationDateId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(stockitemSku, that.stockitemSku) &&
                Objects.equals(dateDate, that.dateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, stockitemSku, dateDate);
    }
}
