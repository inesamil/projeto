package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.*;

@Entity
@Table(name = "House")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;
    @Column(name = "house_name")
    private String name;
    @Column(name = "house_babiesNumber")
    private short babiesNumber;
    @Column(name = "house_childrenNumber")
    private short childrenNumber;
    @Column(name = "house_adultsNumber")
    private short adultsNumber;
    @Column(name = "house_seniorsNumber")
    private short seniorsNumber;

    protected House() { }

    public House(String name, short babiesNumber, short childrenNumber, short adultsNumber, short seniorsNumber) {
        this.name = name;
        this.babiesNumber = babiesNumber;
        this.childrenNumber = childrenNumber;
        this.adultsNumber = adultsNumber;
        this.seniorsNumber = seniorsNumber;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"name\":%s}", id, name);
    }
}
