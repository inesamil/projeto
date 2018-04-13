package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StockItemId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = 128, nullable = false)
    private String stockitemSku;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItemId that = (StockItemId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(stockitemSku, that.stockitemSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, stockitemSku);
    }
}
