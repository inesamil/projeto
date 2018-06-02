package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.dal.repositories.ListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.model.SystemList;
import pt.isel.ps.gis.model.UserList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ListRepositoryCustomImpl implements ListRepositoryCustom {

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
                java.util.List<List> systemListsFiltered = findSystemListsFiltered(connection, username, housesArray,
                        systemLists);
                java.util.List<List> userListsFiltered = findUserListsFiltered(connection, housesArray, listsFromUser,
                        username, sharedLists);
                java.util.List<List> lists = new ArrayList<>();
                Stream
                        .of(systemListsFiltered, userListsFiltered)
                        .forEach(lists::addAll);
                return lists;
            } catch (EntityException e) {
                throw new SQLException(e.getMessage());
            }
        });
    }

    private java.util.List<List> findSystemListsFiltered(
            final Connection connection,
            final String username,
            final Array housesIds,
            final Boolean system
    ) throws SQLException, EntityException {
        String sql = "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, " +
                "public.\"list\".list_type FROM public.\"list\" JOIN (SELECT public.\"userhouse\".house_id FROM " +
                "public.\"userhouse\" WHERE public.\"userhouse\".users_username = ? AND " +
                "public.\"userhouse\".house_id = ANY (?)) AS UH ON public.\"list\".house_id = UH.house_id WHERE " +
                "public.\"list\".list_type = CASE WHEN ? = true THEN 'system' ELSE null END;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (isNotNull(ps, 1, username))
                ps.setString(1, username);
            if (isNotNull(ps, 2, housesIds))
                ps.setArray(2, housesIds);
            if (isNotNull(ps, 3, system))
                ps.setBoolean(3, system);
            try (ResultSet resultSet = ps.executeQuery()) {
                java.util.List<List> systemLists = new ArrayList<>();
                while (resultSet.next()) {
                    long house_id = resultSet.getLong(1);
                    short list_id = resultSet.getShort(2);
                    String list_name = resultSet.getString(3);
                    String list_type = resultSet.getString(4);
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setSystemlist(new SystemList(house_id, list_id, list_name));
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
    ) throws SQLException, EntityException {
        String sql = "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, " +
                "public.\"list\".list_type, public.\"userlist\".users_username, public.\"userlist\".list_shareable " +
                "FROM public.\"list\" JOIN public.\"userlist\" ON (public.\"list\".house_id = public.\"userlist\".house_id " +
                "AND public.\"list\".list_id = public.\"userlist\".list_id) " +
                "WHERE public.\"list\".house_id = ANY (?) AND list_type = 'user' AND users_username = " +
                "(CASE WHEN ? = true THEN ? ELSE null END) " +
                "UNION " +
                "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, " +
                "public.\"list\".list_type, public.\"userlist\".users_username, public.\"userlist\".list_shareable " +
                "FROM public.\"list\" JOIN public.\"userlist\" ON (public.\"list\".house_id = public.\"userlist\".house_id " +
                "AND public.\"list\".list_id = public.\"userlist\".list_id) " +
                "WHERE public.\"list\".house_id = ANY (?) AND list_type = 'user' AND " +
                "list_shareable = (CASE WHEN ? = true THEN true ELSE null END);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (isNotNull(ps, 1, housesIds))
                ps.setArray(1, housesIds);
            if (isNotNull(ps, 2, listsFromUser))
                ps.setBoolean(2, listsFromUser);
            if (isNotNull(ps, 3, username))
                ps.setString(3, username);
            if (isNotNull(ps, 4, housesIds))
                ps.setArray(4, housesIds);
            if (isNotNull(ps, 5, shared))
                ps.setBoolean(5, shared);
            try (ResultSet resultSet = ps.executeQuery()) {
                java.util.List<List> userLists = new ArrayList<>();
                while (resultSet.next()) {
                    long house_id = resultSet.getLong(1);
                    short list_id = resultSet.getShort(2);
                    String list_name = resultSet.getString(3);
                    String list_type = resultSet.getString(4);
                    String list_username = resultSet.getString(5);
                    boolean list_shareable = resultSet.getBoolean(6);
                    List list = new List(house_id, list_id, list_name, list_type);
                    list.setUserlist(new UserList(house_id, list_id, list_name, list_username, list_shareable));
                    userLists.add(list);
                }
                return userLists;
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
