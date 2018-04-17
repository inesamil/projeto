package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.SystemListRepositoryCustom;
import pt.isel.ps.gis.model.SystemList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class SystemListRepositoryCustomImpl implements SystemListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertSystemList(SystemList systemList) {
        Session session = entityManager.unwrap(Session.class);
        systemList.getId()
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_system_list(?,?)}"
            )) {
                function.setLong(1, systemList.getId().getHouseId());
                function.setString(2, systemList.getList().getListName());
                function.execute();
            }
        });
    }
}
