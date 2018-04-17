package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.SystemListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.SystemList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemListRepositoryCustomImpl implements SystemListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SystemList insertSystemList(SystemList systemList) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_system_list(?,?)}"
            )) {
                function.setLong(1, systemList.getId().getHouseId());
                function.setString(2, systemList.getList().getListName());
                ResultSet resultSet = function.executeQuery();
                if (!resultSet.next()) throw new SQLException("Result set is empty.");
                long house_id = resultSet.getLong(1);
                short list_id = resultSet.getShort(2);
                String list_name = resultSet.getString(3);
                resultSet.close();
                try {
                    return new SystemList(house_id, list_id, list_name);
                } catch (EntityException e) {
                    throw new SQLException(e.getMessage());
                }
            }
        });
    }
}
