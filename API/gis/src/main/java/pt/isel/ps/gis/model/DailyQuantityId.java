package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class DailyQuantityId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "stockitem_sku", length = RestrictionsUtils.STOCKITEM_SKU_MAX_LENGTH, nullable = false)
    private String stockitemSku;

    @Column(name = "dailyquantity_date", nullable = false)
    private Date dailyquantityDate;

    /**
     * CONSTRUTOR
     */
    protected DailyQuantityId() {
    }

    public DailyQuantityId(Long houseId, String stockitemSku, Date dailyquantityDate) throws EntityException {
        setHouseId(houseId);
        setStockitemSku(stockitemSku);
        setDailyquantityDate(dailyquantityDate);
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

    public Date getDailyquantityDate() {
        return dailyquantityDate;
    }

    public void setDailyquantityDate(Date dailyquantityDate) throws EntityException {
        ValidationsUtils.validateDailyQuantityDate(dailyquantityDate);
        this.dailyquantityDate = dailyquantityDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DailyQuantityId that = (DailyQuantityId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(stockitemSku, that.stockitemSku) &&
                Objects.equals(dailyquantityDate, that.dailyquantityDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, stockitemSku, dailyquantityDate);
    }
}
