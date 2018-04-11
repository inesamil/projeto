package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    @Basic
    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "categoryByCategoryId")
    private Collection<Product> productsByCategoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
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
