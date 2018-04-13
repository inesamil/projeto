package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class StockItemMovementId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = 128, nullable = false)
    private String stockitemSku;

    @Column(name = "storage_id", nullable = false)
    private Short storageId;

    @Column(name = "stockitemmovement_type", nullable = false)
    private Boolean stockitemmovementType;

    @Column(name = "stockitemmovement_datetime", nullable = false)
    private Timestamp stockitemmovementDatetime;

    @Column(name = "stockitemmovement_quantity", nullable = false)
    private Short stockitemmovementQuantity;

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

    public Boolean getStockitemmovementType() {
        return stockitemmovementType;
    }

    public void setStockitemmovementType(Boolean stockitemmovementType) {
        this.stockitemmovementType = stockitemmovementType;
    }

    public Timestamp getStockitemmovementDatetime() {
        return stockitemmovementDatetime;
    }

    public void setStockitemmovementDatetime(Timestamp stockitemmovementDatetime) {
        this.stockitemmovementDatetime = stockitemmovementDatetime;
    }

    public Short getStockitemmovementQuantity() {
        return stockitemmovementQuantity;
    }

    public void setStockitemmovementQuantity(Short stockitemmovementQuantity) {
        this.stockitemmovementQuantity = stockitemmovementQuantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItemMovementId that = (StockItemMovementId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(stockitemSku, that.stockitemSku) &&
                Objects.equals(storageId, that.storageId) &&
                Objects.equals(stockitemmovementType, that.stockitemmovementType) &&
                Objects.equals(stockitemmovementDatetime, that.stockitemmovementDatetime) &&
                Objects.equals(stockitemmovementQuantity, that.stockitemmovementQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime, stockitemmovementQuantity);
    }
}
