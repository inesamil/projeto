package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "list")
public class List {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private ListId id;

    @Basic
    @Column(name = "list_name", length = 35, nullable = false)
    private String listName;

    @Basic
    @Column(name = "list_type", length = RestrictionsUtils.LIST_TYPE_MAX_LENGTH, nullable = false)
    private String listType;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "list"
    )
    private Collection<ListProduct> listproducts = new ArrayList<>();

    /**
     * CONSTRUTORES
     */
    protected List() {
    }

    public List(ListId id, String listName, String listType) throws EntityException {
        this.id = id;
        setListName(listName);
        setListType(listType);
    }

    public List(Long houseId, String listName, String listType) throws EntityException {
        setId(houseId);
        setListName(listName);
        setListType(listType);
    }

    public List(Long houseId, Short listId, String listName, String listType) throws EntityException {
        setId(houseId, listId);
        setListName(listName);
        setListType(listType);
    }

    /**
     * GETTERS E SETTERS
     */
    public ListId getId() {
        return id;
    }

    public void setId(ListId id) {
        this.id = id;
    }

    public void setId(Long houseId, Short listId) throws EntityException {
        setId(new ListId(houseId, listId));
    }

    public void setId(Long houseId) throws EntityException {
        setId(new ListId(houseId));
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) throws EntityException {
        ValidationsUtils.validateListName(listName);
        this.listName = listName;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) throws EntityException {
        ValidationsUtils.validateListType(listType);
        this.listType = listType;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    public Collection<ListProduct> getListproducts() {
        return listproducts;
    }

    public void setListproducts(Collection<ListProduct> listproducts) {
        this.listproducts = listproducts;
    }

    public void addListProduct(ListProduct listProduct) {
        listproducts.add(listProduct);
        listProduct.setList(this);
    }

    public void removeListProduct(ListProduct listProduct) {
        listproducts.remove(listProduct);
        listProduct.setList(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        List that = (List) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(listName, that.listName) &&
                Objects.equals(listType, that.listType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listName, listType);
    }
}
