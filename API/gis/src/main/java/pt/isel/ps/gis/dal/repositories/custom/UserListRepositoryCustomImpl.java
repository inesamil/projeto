package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.UserListRepositoryCustom;
import pt.isel.ps.gis.model.UserList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class UserListRepositoryCustomImpl implements UserListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertUserList(UserList userList) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_user_list(?,?,?,?)}"
            )) {
                function.setLong(1, userList.getId().getHouseId());
                function.setString(2, userList.getList().getListName());
                function.setString(3, userList.getUsersUsername());
                function.setBoolean(4, userList.getListShareable());
                function.execute();
            }
        });
    }
}
