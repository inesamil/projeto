package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StockItemStorageId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH, nullable = false)
    private String stockitemSku;

    @Column(name = "storage_id", nullable = false)
    private Short storageId;

    /**
     * CONSTRUTORES
     */
    protected StockItemStorageId() {}

    public StockItemStorageId(Long houseId, String stockitemSku, Short storageId) throws ModelException {
        setHouseId(houseId);
        setStockitemSku(stockitemSku);
        setStorageId(storageId);
    }

    /**
     * GETTERS E SETETRS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws ModelException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public String getStockitemSku() {
        return stockitemSku;
    }

    public void setStockitemSku(String stockitemSku) throws ModelException {
        ValidationsUtils.validateStockItemSku(stockitemSku);
        this.stockitemSku = stockitemSku;
    }

    public Short getStorageId() {
        return storageId;
    }

    public void setStorageId(Short storageId) throws ModelException {
        ValidationsUtils.validateStorageId(storageId);
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
