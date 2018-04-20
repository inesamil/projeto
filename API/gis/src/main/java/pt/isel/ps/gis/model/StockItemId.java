package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StockItemId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH, nullable = false)
    private String stockitemSku;

    /**
     * CONSTRUTORES
     */
    protected StockItemId() {
    }

    public StockItemId(Long houseId) throws EntityException {
        setHouseId(houseId);
    }

    public StockItemId(Long houseId, String stockitemSku) throws EntityException {
        this(houseId);
        setStockitemSku(stockitemSku);
    }

    /**
     * GETTERS E SETTERS
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
