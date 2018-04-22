package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.HouseRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class HouseRepositoryCustomImpl implements HouseRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteCascadeHouseById(Long houseId) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call delete_house(?)}"
            )) {
                function.setLong(1, houseId);
                function.execute();
            }
        });
    }
}
