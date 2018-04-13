package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StockItemStorageId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = 128, nullable = false)
    private String stockitemSku;

    @Column(name = "storage_id", nullable = false)
    private Short storageId;

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

    public Short getStorageId() {
        return storageId;
    }

    public void setStorageId(Short storageId) {
        this.storageId = storageId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItemStorageId that = (StockItemStorageId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(stockitemSku, that.stockitemSku) &&
                Objects.equals(storageId, that.storageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, stockitemSku, storageId);
    }
}
