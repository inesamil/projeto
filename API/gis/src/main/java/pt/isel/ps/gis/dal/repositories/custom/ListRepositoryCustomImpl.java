package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.ListRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class ListRepositoryCustomImpl implements ListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public java.util.List<List> findListsFiltered(final Long houseId, final Boolean system, final String username, final Boolean shared) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, public.\"list\".list_type " +
                    "FROM public.\"list\" " +
                    "WHERE public.\"list\".house_id = ? AND list_type = CASE WHEN ? = true THEN 'system' ELSE null END " +
                    "UNION " +
                    "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, public.\"list\".list_type " +
                    "FROM public.\"list\" JOIN public.\"userlist\" ON (public.\"list\".house_id = public.\"userlist\".house_id AND public.\"list\".list_id = public.\"userlist\".list_id) " +
                    "WHERE public.\"list\".house_id = ? AND list_type = 'user' AND users_username = ? " +
                    "UNION " +
                    "SELECT public.\"list\".house_id, public.\"list\".list_id, public.\"list\".list_name, public.\"list\".list_type " +
                    "FROM public.\"list\" JOIN public.\"userlist\" ON (public.\"list\".house_id = public.\"userlist\".house_id AND public.\"list\".list_id = public.\"userlist\".list_id) " +
                    "WHERE public.\"list\".house_id = ? AND list_type = 'user' AND list_shareable = (CASE WHEN ? = true THEN true ELSE null END);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isNotNull(ps, 1, houseId))
                    ps.setLong(1, houseId);
                if (isNotNull(ps, 2, system))
                    ps.setBoolean(2, system);
                if (isNotNull(ps, 3, houseId))
                    ps.setLong(3, houseId);
                if (isNotNull(ps, 4, username))
                    ps.setString(4, username);
                if (isNotNull(ps, 5, houseId))
                    ps.setLong(5, houseId);
                if (isNotNull(ps, 6, shared))
                    ps.setBoolean(6, shared);
                try (ResultSet resultSet = ps.executeQuery()) {
                    java.util.List<List> lists = new ArrayList<>();
                    while (resultSet.next()) {
                        long house_id = resultSet.getLong(1);
                        short list_id = resultSet.getShort(2);
                        String list_name = resultSet.getString(3);
                        String list_type = resultSet.getString(4);
                        try {
                            List list = new List(house_id, list_id, list_name, list_type);
                            lists.add(list);
                        } catch (EntityException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return lists;
                }
            }
        });
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
