package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stockitem")
public class StockItem {

    @EmbeddedId
    private StockItemId id;

    @Basic
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Basic
    @Column(name = "stockitem_brand", length = 35, nullable = false)
    private String stockitemBrand;

    @Basic
    @Column(name = "stockitem_segment", length = 35, nullable = false)
    private String stockitemSegment;

    @Basic
    @Column(name = "stockitem_variety", length = 35, nullable = false)
    private String stockitemVariety;

    @Basic
    @Column(name = "stockitem_quantity", nullable = false)
    private Short stockitemQuantity;

    @Basic
    @Column(name = "stockitem_segmentunit", length = 5, nullable = false)
    private String stockitemSegmentunit;

    @Basic
    @Column(name = "stockitem_description")
    private String stockitemDescription;

    @Basic
    @Column(name = "stockitem_conservationstorage")
    private String stockitemConservationstorage;

    @OneToMany(mappedBy = "stockitem")
    private Collection<ExpirationDate> expirationdates;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false)
    private House houseByHouseId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false),
            @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    })
    private Product product;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemAllergy> stockitemallergies;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemMovement> stockitemmovements;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemStorage> stockitemstorages;

    public StockItemId getId() {
        return id;
    }

    public void setId(StockItemId id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getStockitemBrand() {
        return stockitemBrand;
    }

    public void setStockitemBrand(String stockitemBrand) {
        this.stockitemBrand = stockitemBrand;
    }

    public String getStockitemSegment() {
        return stockitemSegment;
    }

    public void setStockitemSegment(String stockitemSegment) {
        this.stockitemSegment = stockitemSegment;
    }

    public String getStockitemVariety() {
        return stockitemVariety;
    }

    public void setStockitemVariety(String stockitemVariety) {
        this.stockitemVariety = stockitemVariety;
    }

    public Short getStockitemQuantity() {
        return stockitemQuantity;
    }

    public void setStockitemQuantity(Short stockitemQuantity) {
        this.stockitemQuantity = stockitemQuantity;
    }

    public String getStockitemSegmentunit() {
        return stockitemSegmentunit;
    }

    public void setStockitemSegmentunit(String stockitemSegmentunit) {
        this.stockitemSegmentunit = stockitemSegmentunit;
    }

    public String getStockitemDescription() {
        return stockitemDescription;
    }

    public void setStockitemDescription(String stockitemDescription) {
        this.stockitemDescription = stockitemDescription;
    }

    public String getStockitemConservationstorage() {
        return stockitemConservationstorage;
    }

    public void setStockitemConservationstorage(String stockitemConservationstorage) {
        this.stockitemConservationstorage = stockitemConservationstorage;
    }

    public Collection<ExpirationDate> getExpirationdates() {
        return expirationdates;
    }

    public void setExpirationdates(Collection<ExpirationDate> expirationdates) {
        this.expirationdates = expirationdates;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Collection<StockItemAllergy> getStockitemallergies() {
        return stockitemallergies;
    }

    public void setStockitemallergies(Collection<StockItemAllergy> stockitemallergies) {
        this.stockitemallergies = stockitemallergies;
    }

    public Collection<StockItemMovement> getStockitemmovements() {
        return stockitemmovements;
    }

    public void setStockitemmovements(Collection<StockItemMovement> stockitemmovements) {
        this.stockitemmovements = stockitemmovements;
    }

    public Collection<StockItemStorage> getStockitemstorages() {
        return stockitemstorages;
    }

    public void setStockitemstorages(Collection<StockItemStorage> stockitemstorages) {
        this.stockitemstorages = stockitemstorages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItem that = (StockItem) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(stockitemBrand, that.stockitemBrand) &&
                Objects.equals(stockitemSegment, that.stockitemSegment) &&
                Objects.equals(stockitemVariety, that.stockitemVariety) &&
                Objects.equals(stockitemQuantity, that.stockitemQuantity) &&
                Objects.equals(stockitemSegmentunit, that.stockitemSegmentunit) &&
                Objects.equals(stockitemDescription, that.stockitemDescription) &&
                Objects.equals(stockitemConservationstorage, that.stockitemConservationstorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, productId, stockitemBrand, stockitemSegment, stockitemVariety, stockitemQuantity, stockitemSegmentunit, stockitemDescription, stockitemConservationstorage);
    }
}
