package pt.isel.ps.gis.dal.bdModel;

import org.hibernate.annotations.Type;
import pt.isel.ps.gis.dal.bdModel.JsonType.Json;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "House")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    @Column(name = "house_name")
    private String name;

    @Column(name = "characteristics")
    @Type(type = "JsonType")
    private Json characteristics;

    protected House() {
    }

    public House(String name, short babiesNumber, short childrenNumber, short adultsNumber, short seniorsNumber) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof House)) {
            return false;
        }
        House other = (House) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"name\":%s}", id, name);
    }
}