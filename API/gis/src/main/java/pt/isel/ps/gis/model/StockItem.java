package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Column(name = "product_id", nullable = false, unique = true)
    private Integer productId;

    @Basic
    @Column(name = "stockitem_brand", length = RestrictionsUtils.STOCKITEM_BRAND_MAX_LENGTH, nullable = false, unique = true)
    private String stockitemBrand;

    @Basic
    @Column(name = "stockitem_segment", nullable = false, unique = true)
    private Float stockitemSegment;

    @Basic
    @Column(name = "stockitem_variety", length = RestrictionsUtils.STOCKITEM_VARIETY_MAX_LENGTH, nullable = false, unique = true)
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
    @Column(name = "stockitem_conservationstorage", length = 128)
    private String stockitemConservationstorage;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "stockitem"
    )
    private Collection<ExpirationDate> expirationdates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "stockitem"
    )
    private Collection<StockItemAllergy> stockitemallergies = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "stockitem"
    )
    private Collection<StockItemMovement> stockitemmovements = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "stockitem"
    )
    private Collection<StockItemStorage> stockitemstorages = new ArrayList<>();

    /**
     * CONSTRUTORES
     */
    protected StockItem() {
    }

    private StockItem(Integer productId, String stockitemBrand, Float stockitemSegment,
                      String stockitemVariety, Short stockitemQuantity, String stockitemSegmentUnit, String stockitemDescription,
                      String stockitemConservationStorage) throws EntityException {
        setProductId(productId);
        setStockitemBrand(stockitemBrand);
        setStockitemSegment(stockitemSegment);
        setStockitemVariety(stockitemVariety);
        setStockitemQuantity(stockitemQuantity);
        setStockitemSegmentunit(stockitemSegmentUnit);
        setStockitemDescription(stockitemDescription);
        setStockitemConservationstorage(stockitemConservationStorage);
    }

    public StockItem(StockItemId id, Integer productId, String stockitemBrand,
                     Float stockitemSegment, String stockitemVariety, Short stockitemQuantity, String stockitemSegmentUnit,
                     String stockitemDescription, String stockitemConservationStorage) throws EntityException {
        this(productId, stockitemBrand, stockitemSegment, stockitemVariety,
                stockitemQuantity, stockitemSegmentUnit, stockitemDescription, stockitemConservationStorage);
        this.id = id;
    }

    public StockItem(Long houseId, Integer productId, String stockitemBrand, Float stockitemSegment,
                     String stockitemVariety, Short stockitemQuantity, String stockitemSegmentUnit, String stockitemDescription,
                     String stockitemConservationStorage) throws EntityException {
        this(productId, stockitemBrand, stockitemSegment, stockitemVariety,
                stockitemQuantity, stockitemSegmentUnit, stockitemDescription, stockitemConservationStorage);
        setId(houseId);
    }

    public StockItem(Long houseId, String stockitemSku, Integer productId, String stockitemBrand, Float stockitemSegment,
                     String stockitemVariety, Short stockitemQuantity, String stockitemSegmentUnit, String stockitemDescription,
                     String stockitemConservationStorage) throws EntityException {
        this(productId, stockitemBrand, stockitemSegment, stockitemVariety, stockitemQuantity,
                stockitemSegmentUnit, stockitemDescription, stockitemConservationStorage);
        setId(houseId, stockitemSku);
    }

    /**
     * GETTERS E SETTERS
     */
    public StockItemId getId() {
        return id;
    }

    public void setId(StockItemId id) {
        this.id = id;
    }

    public void setId(Long houseId) throws EntityException {
        setId(new StockItemId(houseId));
    }

    public void setId(Long houseId, String stockitemSku) throws EntityException {
        setId(new StockItemId(houseId, stockitemSku));
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        this.productId = productId;
    }

    public String getStockitemBrand() {
        return stockitemBrand;
    }

    public void setStockitemBrand(String stockitemBrand) throws EntityException {
        ValidationsUtils.validateStockItemBrand(stockitemBrand);
        this.stockitemBrand = stockitemBrand;
    }

    public Float getStockitemSegment() {
        return stockitemSegment;
    }

    public void setStockitemSegment(Float stockitemSegment) throws EntityException {
        ValidationsUtils.validateStockItemSegment(stockitemSegment);
        this.stockitemSegment = stockitemSegment;
    }

    public String getStockitemVariety() {
        return stockitemVariety;
    }

    public void setStockitemVariety(String stockitemVariety) throws EntityException {
        ValidationsUtils.validateStockItemVariety(stockitemVariety);
        this.stockitemVariety = stockitemVariety;
    }

    public Short getStockitemQuantity() {
        return stockitemQuantity;
    }

    public void setStockitemQuantity(Short stockitemQuantity) throws EntityException {
        ValidationsUtils.validateStockItemQuantity(stockitemQuantity);
        this.stockitemQuantity = stockitemQuantity;
    }

    public String getStockitemSegmentunit() {
        return stockitemSegmentunit;
    }

    public void setStockitemSegmentunit(String stockitemSegmentunit) throws EntityException {
        ValidationsUtils.validateStockItemSegmentUnit(stockitemSegmentunit);
        this.stockitemSegmentunit = stockitemSegmentunit;
    }

    public String getStockitemDescription() {
        return stockitemDescription;
    }

    public void setStockitemDescription(String stockitemDescription) throws EntityException {
        ValidationsUtils.validateStockItemDescription(stockitemDescription);
        this.stockitemDescription = stockitemDescription;
    }

    public String getStockitemConservationstorage() {
        return stockitemConservationstorage;
    }

    public void setStockitemConservationstorage(String stockitemConservationstorage) throws EntityException {
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

    public void addExpirationDate(ExpirationDate expirationDate) {
        expirationdates.add(expirationDate);
        expirationDate.setStockitem(this);
    }

    public void removeExpirationDate(ExpirationDate expirationDate) {
        expirationdates.remove(expirationDate);
        expirationDate.setStockitem(null);
    }

    public void addStockItemAllergy(StockItemAllergy stockItemAllergy) {
        stockitemallergies.add(stockItemAllergy);
        stockItemAllergy.setStockitem(this);
    }

    public void removeStockItemAllergy(StockItemAllergy stockItemAllergy) {
        stockitemallergies.remove(stockItemAllergy);
        stockItemAllergy.setStockitem(null);
    }

    public void addStockItemMovement(StockItemMovement stockItemMovement) {
        stockitemmovements.add(stockItemMovement);
        stockItemMovement.setStockitem(this);
    }

    public void removeStockItemMovement(StockItemMovement stockItemMovement) {
        stockitemmovements.remove(stockItemMovement);
        stockItemMovement.setStockitem(null);
    }

    public void addStockItemStorage(StockItemStorage stockItemStorage) {
        stockitemstorages.add(stockItemStorage);
        stockItemStorage.setStockitem(this);
    }

    public void removeStockItemStorage(StockItemStorage stockItemStorage) {
        stockitemstorages.remove(stockItemStorage);
        stockItemStorage.setStockitem(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItem that = (StockItem) obj;
        return Objects.equals(id, that.id) &&
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
        return Objects.hash(id, productId, stockitemBrand, stockitemSegment, stockitemVariety, stockitemQuantity, stockitemSegmentunit, stockitemDescription, stockitemConservationstorage);
    }
}
