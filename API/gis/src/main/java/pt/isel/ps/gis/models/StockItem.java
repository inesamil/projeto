package pt.isel.ps.gis.models;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stockitem")
public class StockItem {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private StockItemId id;

    @Basic
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Basic
    @Column(name = "stockitem_brand", length = RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH, nullable = false)
    private String stockitemBrand;

    @Basic
    @Column(name = "stockitem_segment", nullable = false)
    private Float stockitemSegment;

    @Basic
    @Column(name = "stockitem_variety", length = RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH, nullable = false)
    private String stockitemVariety;

    @Basic
    @Column(name = "stockitem_quantity", nullable = false)
    private Short stockitemQuantity;

    @Basic
    @Column(name = "stockitem_segmentunit", length = RestrictionsUtils.STOCKITEM_SEGMENTUNIT_MAX_LENGTH, nullable = false)
    private String stockitemSegmentunit;

    // The length = 10485760 is the maximum permited in postgreSql jdbc
    @Basic
    @Column(name = "stockitem_description", length = RestrictionsUtils.STOCKITEM_DESCRIPTION_MAX_LENGTH)
    private String stockitemDescription;

    @Basic
    @Column(name = "stockitem_conservationstorage", length = 128, nullable = false)
    private String stockitemConservationstorage;

    /**
     * ASSOCIAÇÕES
      */
    @OneToMany(mappedBy = "stockitem")
    private Collection<ExpirationDate> expirationdates;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    })
    private Product product;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemAllergy> stockitemallergies;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemMovement> stockitemmovements;

    @OneToMany(mappedBy = "stockitem")
    private Collection<StockItemStorage> stockitemstorages;

    /**
     * CONSTRUTORES
     */
    protected StockItem() {}

    public StockItem(Integer categoryId, Integer productId, String stockitemBrand, Float stockitemSegment,
                     String stockitemSegmentUnit, String stockitemVariety, Short stockitemQuantity,
                     String stockitemDescription, String stockitemConservationStorage) throws ModelException {
        setCategoryId(categoryId);
        setProductId(productId);
        setStockitemBrand(stockitemBrand);
        setStockitemSegment(stockitemSegment);
        setStockitemSegmentunit(stockitemSegmentUnit);
        setStockitemVariety(stockitemVariety);
        setStockitemQuantity(stockitemQuantity);
        setStockitemDescription(stockitemDescription);
        setStockitemConservationstorage(stockitemConservationStorage);
    }

    public StockItem(Long houseId, String stockitemSku, Integer categoryId, Integer productId, String stockitemBrand, Float stockitemSegment,
                     String stockitemSegmentUnit, String stockitemVariety, Short stockitemQuantity, String stockitemDescription,
                     String stockitemConservationStorage) throws ModelException {
        this(categoryId, productId, stockitemBrand, stockitemSegment, stockitemSegmentUnit, stockitemVariety,
                stockitemQuantity, stockitemDescription, stockitemConservationStorage);
        setId(houseId, stockitemSku);
    }

    /**
     * GETTERS E SETTERS
     */
    public StockItemId getId() {
        return id;
    }

    private void setId(StockItemId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockitemSku) throws ModelException {
        setId(new StockItemId(houseId, stockitemSku));
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) throws ModelException {
        ValidationsUtils.validateCategoryId(categoryId);
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) throws ModelException{
        ValidationsUtils.validateProductId(productId);
        this.productId = productId;
    }

    public String getStockitemBrand() {
        return stockitemBrand;
    }

    public void setStockitemBrand(String stockitemBrand) throws ModelException {
        ValidationsUtils.validateStockItemBrand(stockitemBrand);
        this.stockitemBrand = stockitemBrand;
    }

    public Float getStockitemSegment() {
        return stockitemSegment;
    }

    public void setStockitemSegment(Float stockitemSegment) throws ModelException {
        ValidationsUtils.validateStockItemSegment(stockitemSegment);
        this.stockitemSegment = stockitemSegment;
    }

    public String getStockitemVariety() {
        return stockitemVariety;
    }

    public void setStockitemVariety(String stockitemVariety) throws ModelException {
        ValidationsUtils.validateStockItemVariety(stockitemVariety);
        this.stockitemVariety = stockitemVariety;
    }

    public Short getStockitemQuantity() {
        return stockitemQuantity;
    }

    public void setStockitemQuantity(Short stockitemQuantity) throws ModelException {
        ValidationsUtils.validateStockItemQuantity(stockitemQuantity);
        this.stockitemQuantity = stockitemQuantity;
    }

    public String getStockitemSegmentunit() {
        return stockitemSegmentunit;
    }

    public void setStockitemSegmentunit(String stockitemSegmentunit) throws ModelException{
        ValidationsUtils.validateStockItemSegmentUnit(stockitemSegmentunit);
        this.stockitemSegmentunit = stockitemSegmentunit;
    }

    public String getStockitemDescription() {
        return stockitemDescription;
    }

    public void setStockitemDescription(String stockitemDescription) throws ModelException{
        ValidationsUtils.validateStockItemDescription(stockitemDescription);
        this.stockitemDescription = stockitemDescription;
    }

    public String getStockitemConservationstorage() {
        return stockitemConservationstorage;
    }

    public void setStockitemConservationstorage(String stockitemConservationstorage) throws ModelException {
        ValidationsUtils.validateStockItemConservationStorage(stockitemConservationstorage);
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
