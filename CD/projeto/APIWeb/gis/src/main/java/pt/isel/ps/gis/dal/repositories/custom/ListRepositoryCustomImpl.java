package pt.isel.ps.gis.dal.repositories.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.dal.repositories.ListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ListRepositoryCustomImpl implements ListRepositoryCustom {

    private static final ObjectMapper mapper = new ObjectMapper();

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public java.util.List<List> findAvailableListsByUserUsername(
            final String username,
            final Long[] houses,
            final Boolean systemLists,
            final Boolean listsFromUser,
            final Boolean sharedLists
    ) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try {
                Array housesArray = connection.createArrayOf("bigint", houses);
                java.util.List<List> systemListsFiltered = findSystemListsFiltered(connection, housesArray, systemLists);
                java.util.List<List> userListsFiltered = findUserListsFiltered(connection, housesArray, listsFromUser,
                        username, sharedLists);
                java.util.List<List> lists = new ArrayList<>();
                Stream
                        .of(systemListsFiltered, userListsFiltered)
                        .distinct()
                        .forEach(lists::addAll);
                return lists;
            } catch (EntityException | IOException e) {
                throw new SQLException(e.getMessage());
            }
        });
    }

    private java.util.List<List> findSystemListsFiltered(
            final Connection connection,
            final Array housesIds,
            final Boolean system
    ) throws SQLException, EntityException, IOException {
        String sql = "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, " +
                "public.\"list\".list_type, public.\"house\".house_name, public.\"house\".house_characteristics " +
                "FROM public.\"list\" JOIN public.\"house\" ON (public.\"house\".house_id = public.\"list\".house_id) " +
                "WHERE public.\"list\".house_id = ANY (?) AND public.\"list\".list_type = (CASE WHEN ? = true THEN 'system' ELSE null END);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (isNotNull(ps, 1, housesIds))
                ps.setArray(1, housesIds);
            if (isNotNull(ps, 2, system))
                ps.setBoolean(2, system);
            try (ResultSet resultSet = ps.executeQuery()) {
                java.util.List<List> systemLists = new ArrayList<>();
                while (resultSet.next()) {
                    long house_id = resultSet.getLong(1);
                    short list_id = resultSet.getShort(2);
                    String list_name = resultSet.getString(3);
                    String list_type = resultSet.getString(4);
                    String house_name = resultSet.getString(5);
                    Characteristics characteristics = mapper.readValue(resultSet.getString(6), Characteristics.class);
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setSystemlist(new SystemList(house_id, list_id, list_name));
                    list.setHouseByHouseId(new House(house_id, house_name, characteristics));
                    systemLists.add(list);
                }
                return systemLists;
            }
        }
    }

    private java.util.List<List> findUserListsFiltered(
            final Connection connection,
            final Array housesIds,
            final Boolean listsFromUser,
            final String username,
            final Boolean shared
    ) throws SQLException, EntityException, IOException {
        String sql = "SELECT public.\"list\".house_id, public.\"house\".house_name, public.\"house\".house_characteristics, " +
                "public.\"list\".list_id, public.\"list\".list_name, public.\"list\".list_type, public.\"userlist\".list_shareable, " +
                "public.\"userlist\".users_id, public.\"users\".users_username, public.\"users\".users_email, public.\"users\".users_age, public.\"users\".users_name, public.\"users\".users_password " +
                "FROM public.\"list\" JOIN public.\"userlist\" ON (public.\"list\".house_id = public.\"userlist\".house_id " +
                "AND public.\"list\".list_id = public.\"userlist\".list_id) " +
                "JOIN public.\"house\" ON (public.\"house\".house_id = public.\"list\".house_id) " +
                "JOIN public.\"users\" ON (public.\"userlist\".users_id = public.\"users\".users_id) " +
                "WHERE public.\"list\".house_id = ANY (?) AND public.\"list\".list_type = 'user' AND ((? = true AND public.\"users\".users_username = ?) OR " +
                "(public.\"users\".users_username != ? AND public.\"userlist\".list_shareable = CASE WHEN ? = false THEN null ELSE true END))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (isNotNull(ps, 1, housesIds))
                ps.setArray(1, housesIds);
            if (isNotNull(ps, 2, listsFromUser))
                ps.setBoolean(2, listsFromUser);
            if (isNotNull(ps, 3, username))
                ps.setString(3, username);
            if (isNotNull(ps, 4, username))
                ps.setString(4, username);
            if (isNotNull(ps, 5, shared))
                ps.setBoolean(5, shared);
            try (ResultSet resultSet = ps.executeQuery()) {
                java.util.List<List> lists = new ArrayList<>();
                while (resultSet.next()) {
                    // House
                    long house_id = resultSet.getLong(1);
                    String house_name = resultSet.getString(2);
                    Characteristics characteristics = mapper.readValue(resultSet.getString(3), Characteristics.class);
                    // List
                    short list_id = resultSet.getShort(4);
                    String list_name = resultSet.getString(5);
                    String list_type = resultSet.getString(6);
                    boolean list_shareable = resultSet.getBoolean(7);
                    // User
                    long userId = resultSet.getLong(8);
                    String userUsername = resultSet.getString(9);
                    String userEmail = resultSet.getString(10);
                    short userAge = resultSet.getShort(11);
                    String userName = resultSet.getString(12);
                    String userPassword = resultSet.getString(13);
                    // UserList
                    UserList userList = new UserList(house_id, list_id, list_name, userId, list_shareable);
                    userList.setUsersByUsersId(new Users(userId, userUsername, userEmail, userAge, userName, userPassword));
                    // List
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setUserlist(userList);
                    list.setHouseByHouseId(new House(house_id, house_name, characteristics));

                    lists.add(list);
                }
                return lists;
            }
        }
    }

    /**
     * Verify if t is null and it is true call ps.setNull in position idx and return false. Otherwise return true.
     *
     * @param ps  instance of PreparedStatement
     * @param idx index to use in ps
     * @param t   object to check if it is null
     * @return true if t is null and set null in ps. Otherwise return true
     * @throws SQLException throw by ps.setNull(..)
     */
    private boolean isNotNull(PreparedStatement ps, int idx, Object t) throws SQLException {
        if (t == null) {
            ps.setNull(idx, Types.OTHER);
            return false;
        }
        return true;
    }
}
