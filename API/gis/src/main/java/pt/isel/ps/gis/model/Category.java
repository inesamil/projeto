package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    /**
     * COLUNAS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Basic
    @Column(name = "category_name", length = RestrictionsUtils.CATEGORY_NAME_MAX_LENGTH, nullable = false, unique = true)
    private String categoryName;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryByCategoryId")
    private Collection<Product> productsByCategoryId;

    /**
     * CONSTRUTORES
     */
    protected Category() {
    }

    public Category(String categoryName) throws EntityException {
        setCategoryName(categoryName);
    }

    /**
     * GETTERS E SETTERS
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) throws EntityException {
        ValidationsUtils.validateCategoryName(categoryName);
        this.categoryName = categoryName;
    }

    public Collection<Product> getProductsByCategoryId() {
        return productsByCategoryId;
    }

    public void setProductsByCategoryId(Collection<Product> productsByCategoryId) {
        this.productsByCategoryId = productsByCategoryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category that = (Category) obj;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }
}
