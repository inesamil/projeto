package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stockitemstorage")
public class StockItemStorage {

    @EmbeddedId
    private StockItemStorageId id;

    @Basic
    @Column(name = "stockitemstorage_quantity", nullable = false)
    private Short stockitemstorageQuantity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false)
    })
    private StockItem stockitem;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "storage_id", referencedColumnName = "storage_id", nullable = false)
    })
    private Storage storage;

    public StockItemStorageId getId() {
        return id;
    }

    public void setId(StockItemStorageId id) {
        this.id = id;
    }

    public Short getStockitemstorageQuantity() {
        return stockitemstorageQuantity;
    }

    public void setStockitemstorageQuantity(Short stockitemstorageQuantity) {
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
