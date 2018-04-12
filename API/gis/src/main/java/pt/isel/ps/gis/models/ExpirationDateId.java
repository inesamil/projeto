package pt.isel.ps.gis.models;

import pt.isel.ps.gis.utils.DateUtils;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class ExpirationDateId implements Serializable {

    /**
     * COLUNAS
     */
    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "stockitem_sku", length = 128, nullable = false)
    private String stockitemSku;

    @Id
    @Column(name = "date_date", nullable = false)
    private Timestamp dateDate;

    /**
     * CONSTRUTOR
     */
    protected ExpirationDateId() {}

    public ExpirationDateId(Long houseId, String stockitemSku, String expirationDate) throws IllegalArgumentException {
        setHouseId(houseId);
        setStockitemSku(stockitemSku);
        setDateDate(expirationDate);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws IllegalArgumentException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public String getStockitemSku() {
        return stockitemSku;
    }

    public void setStockitemSku(String stockitemSku) throws IllegalArgumentException {
        ValidationsUtils.validateStockItemSku(stockitemSku);
        this.stockitemSku = stockitemSku;
    }

    public String getDateDate() {
        return DateUtils.convertDateFormat(this.dateDate);
    }

    public void setDateDate(String date) throws IllegalArgumentException {
        ValidationsUtils.validateDate(date);
        date += " 00:00:00";    // JDBC timestamp escape format: yyyy-[m]m-[d]d hh:mm:ss[.f...].
        this.dateDate = Timestamp.valueOf(date);
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
