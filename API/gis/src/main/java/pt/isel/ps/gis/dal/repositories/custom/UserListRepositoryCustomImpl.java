package pt.isel.ps.gis.dal.repositories.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.UserListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserListRepositoryCustomImpl implements UserListRepositoryCustom {

    private static final ObjectMapper mapper = new ObjectMapper();

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
                function.setString(3, userList.getUsersId());
                function.setBoolean(4, userList.getListShareable());
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    long house_id = resultSet.getLong(1);
                    String house_name = resultSet.getString(2);
                    Characteristics characteristics = mapper.readValue(resultSet.getString(3), Characteristics.class);
                    short list_id = resultSet.getShort(4);
                    String list_name = resultSet.getString(5);
                    String list_type = resultSet.getString(6);
                    String users_username = resultSet.getString(7);
                    boolean list_shareable = resultSet.getBoolean(8);

                    UserList userListInserted =  new UserList(house_id, list_id, list_name, users_username, list_shareable);
                    // Set List
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setHouseByHouseId(new House(house_id, house_name, characteristics));
                    list.setUserlist(userListInserted);
                    userListInserted.setList(list);

                    return userListInserted;
                } catch (EntityException | IOException e) {
                    throw new SQLException(e.getMessage());
                }
        }});
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
