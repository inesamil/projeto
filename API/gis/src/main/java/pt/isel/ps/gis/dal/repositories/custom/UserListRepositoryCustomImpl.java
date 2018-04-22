package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.UserListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.UserList;
import pt.isel.ps.gis.model.UserListId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserListRepositoryCustomImpl implements UserListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserList insertUserList(UserList userList) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_user_list(?,?,?,?)}"
            )) {
                function.setLong(1, userList.getId().getHouseId());
                function.setString(2, userList.getList().getListName());
                function.setString(3, userList.getUsersUsername());
                function.setBoolean(4, userList.getListShareable());
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    long house_id = resultSet.getLong(1);
                    short list_id = resultSet.getShort(2);
                    String list_name = resultSet.getString(3);
                    String users_username = resultSet.getString(4);
                    boolean list_shareable = resultSet.getBoolean(5);
                    try {
                        return new UserList(house_id, list_id, list_name, users_username, list_shareable);
                    } catch (EntityException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void deleteCascadeUserListById(UserListId id) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call delete_user_list(?,?)}"
            )) {
                function.setLong(1, id.getHouseId());
                function.setShort(2, id.getListId());
                function.execute();
            }
        });
    }
}
