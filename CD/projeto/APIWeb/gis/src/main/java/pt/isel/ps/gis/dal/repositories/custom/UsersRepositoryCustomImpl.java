package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.UsersRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class UsersRepositoryCustomImpl implements UsersRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteCascadeUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call delete_user(?)}"
            )) {
                function.setString(1, username);
                function.execute();
            }
        });
    }
}
