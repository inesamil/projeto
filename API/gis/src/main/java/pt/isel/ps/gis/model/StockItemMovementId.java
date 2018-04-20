package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class StockItemMovementId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH, nullable = false)
    private String stockitemSku;

    @Column(name = "storage_id", nullable = false)
    private Short storageId;

    @Column(name = "stockitemmovement_type", nullable = false)
    private Boolean stockitemmovementType;

    @Column(name = "stockitemmovement_datetime", nullable = false)
    private Timestamp stockitemmovementDatetime;

    @Column(name = "stockitemmovement_quantity", nullable = false)
    private Short stockitemmovementQuantity;

    /**
     * CONSTRUTORES
     */
    protected StockItemMovementId() {
    }

    public StockItemMovementId(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType,
                               String stockitemmovementDatetime, Short stockitemmovementQuantity) throws EntityException {
        setHouseId(houseId);
        setStockitemSku(stockitemSku);
        setStorageId(storageId);
        setStockitemmovementType(stockitemmovementType);
        setStockitemmovementDatetime(stockitemmovementDatetime);
        setStockitemmovementQuantity(stockitemmovementQuantity);
    }

    /**
     * GETTERS E SETETRS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public String getStockitemSku() {
        return stockitemSku;
    }

    public void setStockitemSku(String stockitemSku) throws EntityException {
        ValidationsUtils.validateStockItemSku(stockitemSku);
        this.stockitemSku = stockitemSku;
    }

    public Short getStorageId() {
        return storageId;
    }

    public void setStorageId(Short storageId) throws EntityException {
        ValidationsUtils.validateStorageId(storageId);
        this.storageId = storageId;
    }

    public Boolean getStockitemmovementType() {
        return stockitemmovementType;
    }

    public void setStockitemmovementType(Boolean stockitemmovementType) throws EntityException {
        ValidationsUtils.validateStockItemMovementType(stockitemmovementType);
        this.stockitemmovementType = stockitemmovementType;
    }

    public Timestamp getStockitemmovementDatetime() {
        // TODO: tem de retornar string, não pode retornar Timestamp
        return stockitemmovementDatetime;
    }

    public void setStockitemmovementDatetime(String stockitemmovementDatetime) throws EntityException {
        ValidationsUtils.validateStockItemMovementDateTime(stockitemmovementDatetime);
        this.stockitemmovementDatetime = Timestamp.valueOf(stockitemmovementDatetime);
    }

    public Short getStockitemmovementQuantity() {
        return stockitemmovementQuantity;
    }

    public void setStockitemmovementQuantity(Short stockitemmovementQuantity) throws EntityException {
        ValidationsUtils.validateStockItemMovementQuantity(stockitemmovementQuantity);
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
