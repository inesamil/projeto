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
    public UserList insertUserList(Long houseId, String listName, String username, Boolean shareable) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_user_list(?,?,?,?)}"
            )) {
                function.setLong(1, houseId);
                function.setString(2, listName);
                function.setString(3, username);
                function.setBoolean(4, shareable);
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    long house_id = resultSet.getLong(1);
                    String house_name = resultSet.getString(2);
                    Characteristics characteristics = mapper.readValue(resultSet.getString(3), Characteristics.class);
                    short list_id = resultSet.getShort(4);
                    String list_name = resultSet.getString(5);
                    String list_type = resultSet.getString(6);
                    long users_id = resultSet.getLong(7);
                    String users_username = resultSet.getString(8);
                    String users_email = resultSet.getString(9);
                    short users_age = resultSet.getShort(10);
                    String users_name = resultSet.getString(11);
                    String users_password = resultSet.getString(12);
                    boolean list_shareable = resultSet.getBoolean(13);
                    UserList userListInserted = new UserList(house_id, list_id, list_name, users_id, list_shareable);
                    // Set List
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setHouseByHouseId(new House(house_id, house_name, characteristics));
                    list.setUserlist(userListInserted);
                    userListInserted.setList(list);
                    userListInserted.setUsersByUsersId(new Users(users_id, users_username, users_email, users_age, users_name, users_password));
                    return userListInserted;
                } catch (EntityException | IOException e) {
                    throw new SQLException(e.getMessage());
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
