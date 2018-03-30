package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.*;

@Entity
@Table(name = "List")
@Inheritance(strategy = InheritanceType.JOINED)
public class List {

    @EmbeddedId
    private ListId id;

    @Column(name = "list_name", length = 35, nullable = false)
    private String name;

    @Column(name = "list_type", length = 7, nullable = false)
    private String type;

    protected List() { }

    public List(ListId id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}

@Embeddable
class ListId {

    @Column(name = "house_id", nullable = false)
    private long houseId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", nullable = false)
    private long listId;
}
