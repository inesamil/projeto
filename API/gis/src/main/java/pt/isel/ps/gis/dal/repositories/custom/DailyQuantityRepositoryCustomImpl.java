package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.DailyQuantityRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.DailyQuantity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DailyQuantityRepositoryCustomImpl implements DailyQuantityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailyQuantity> findAllByStartDateAndEndDate(Long houseId, String sku, Date startDate, Date endDate) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "SELECT public.\"dailyquantity\".house_id, public.\"dailyquantity\".stockitem_sku, " +
                    "public.\"dailyquantity\".dailyquantity_date, public.\"dailyquantity\".dailyquantity_quantity " +
                    "FROM public.\"dailyquantity\" " +
                    "WHERE public.\"dailyquantity\".house_id = ? " +
                    "AND public.\"dailyquantity\".stockitem_sku = ? " +
                    "AND public.\"dailyquantity\".dailyquantity_date >= ? " +
                    "AND public.\"dailyquantity\".dailyquantity_date <= ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isNotNull(ps, 1, houseId))
                    ps.setLong(1, houseId);
                if (isNotNull(ps, 2, sku))
                    ps.setString(2, sku);
                if (isNotNull(ps, 3, startDate))
                    ps.setDate(3, startDate);
                if (isNotNull(ps, 4, endDate))
                    ps.setDate(4, endDate);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<DailyQuantity> dailyQuantities = new ArrayList<>();
                    while (resultSet.next()) {
                        try {
                            long house_id = resultSet.getLong(1);
                            String stockitem_sku = resultSet.getString(2);
                            Date dailyquantity_date = resultSet.getDate(3);
                            short dailyquantity_quantity = resultSet.getShort(4);
                            dailyQuantities.add(new DailyQuantity(house_id, stockitem_sku, dailyquantity_date, dailyquantity_quantity));
                        } catch (EntityException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return dailyQuantities;
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
