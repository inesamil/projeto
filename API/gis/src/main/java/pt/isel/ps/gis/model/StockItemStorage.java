package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stockitemstorage")
public class StockItemStorage {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private StockItemStorageId id;

    @Basic
    @Column(name = "stockitemstorage_quantity", nullable = false)
    private Short stockitemstorageQuantity;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false, insertable = false, updatable = false)
    })
    private StockItem stockitem;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "storage_id", referencedColumnName = "storage_id", nullable = false, insertable = false, updatable = false)
    })
    private Storage storage;

    /**
     * CONSTRUTORES
     */
    protected StockItemStorage() {}

    public StockItemStorage(Long houseId, String stockitemSku, Short storageId, Short stockitemstorageQuantity) throws ModelException {
        setId(houseId, stockitemSku, storageId);
        setStockitemstorageQuantity(stockitemstorageQuantity);
    }

    /**
     * GETTERS E SETTERS
     */
    public StockItemStorageId getId() {
        return id;
    }

    private void setId(StockItemStorageId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockitemSku, Short storageId) throws ModelException {
        setId(new StockItemStorageId(houseId, stockitemSku, storageId));
    }

    public Short getStockitemstorageQuantity() {
        return stockitemstorageQuantity;
    }

    public void setStockitemstorageQuantity(Short stockitemstorageQuantity) throws ModelException {
        ValidationsUtils.validateStockItemStorageQuantity(stockitemstorageQuantity);
        this.stockitemstorageQuantity = stockitemstorageQuantity;
    }

    public StockItem getStockitem() {
        return stockitem;
    }

    public void setStockitem(StockItem stockitem) {
        this.stockitem = stockitem;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItemStorage that = (StockItemStorage) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(stockitemstorageQuantity, that.stockitemstorageQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockitemstorageQuantity);
    }
}
