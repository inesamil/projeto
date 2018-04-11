package pt.isel.ps.gis.dal.bdModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Column(name = "product_name", length = 35, nullable = false)
    private String name;

    @Column(name = "product_edible", nullable = false)
    private boolean edible;

    @Column(name = "product_shelfLife", nullable = false)
    private short shelfLife;

    @Column(name = "product_shelfLifeTimeUnit", length = 35, nullable = false)
    private String shelfLifeTimeUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Product() {}

    public Product(ProductId id, String name, boolean edible, short shelfLife, String shelfLifeTimeUnit) {
        this.id = id;
        this.name = name;
        this.edible = edible;
        this.shelfLife = shelfLife;
        this.shelfLifeTimeUnit = shelfLifeTimeUnit;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEdible() {
        return edible;
    }

    public short getShelfLife() {
        return shelfLife;
    }

    public String getShelfLifeTimeUnit() {
        return shelfLifeTimeUnit;
    }

    public Category getCategory() {
        return category;
    }
}

@Embeddable
class ProductId implements Serializable {

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int productId;

    protected ProductId() {
    }

    public ProductId(int categoryId, int productId) {
        this.categoryId = categoryId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ProductId))
            return false;
        ProductId other = (ProductId) obj;
        return Objects.equals(categoryId, other.categoryId) &&
                Objects.equals(productId, other.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, productId);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getProductId() {
        return productId;
    }
}
