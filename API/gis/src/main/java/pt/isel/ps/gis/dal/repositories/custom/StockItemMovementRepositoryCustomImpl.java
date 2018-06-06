package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockItemMovementRepositoryCustomImpl implements StockItemMovementRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StockItemMovement> findMovementsFiltered(Long houseId, String sku, Boolean type, Timestamp date, Short storageId) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" " +
                    "WHERE public.\"stockitemmovement\".house_id = ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" " +
                    "WHERE public.\"stockitemmovement\".house_id = ? AND public.\"stockitemmovement\".stockitem_sku != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" " +
                    "WHERE public.\"stockitemmovement\".house_id = ? AND public.\"stockitemmovement\".stockitemmovement_type != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" " +
                    "WHERE public.\"stockitemmovement\".house_id = ? AND DATE(public.\"stockitemmovement\".stockitemmovement_datetime) != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" " +
                    "WHERE public.\"stockitemmovement\".house_id = ? AND public.\"stockitemmovement\".storage_id != ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isNotNull(ps, 1, houseId))
                    ps.setLong(1, houseId);
                if (isNotNull(ps, 2, houseId))
                    ps.setLong(2, houseId);
                if (isNotNull(ps, 3, sku))
                    ps.setString(3, sku);
                if (isNotNull(ps, 4, houseId))
                    ps.setLong(4, houseId);
                if (isNotNull(ps, 5, type))
                    ps.setBoolean(5, type);
                if (isNotNull(ps, 6, houseId))
                    ps.setLong(6, houseId);
                if (isNotNull(ps, 7, date))
                    ps.setTimestamp(7, date);
                if (isNotNull(ps, 8, houseId))
                    ps.setLong(8, houseId);
                if (isNotNull(ps, 9, storageId))
                    ps.setShort(9, storageId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<StockItemMovement> stockItemMovements = new ArrayList<>();
                    while (resultSet.next()) {
                        try {
                            stockItemMovements.add(extractStockItemMovementFromResultSet(resultSet));
                        } catch (EntityException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return stockItemMovements;
                }
            }
        });
    }

    @Transactional
    @Override
    public StockItemMovement insertStockItemMovement(final long houseId, final short storageId, final boolean movementType,
                                                     final short quantity, final String productName, final String brand,
                                                     final String variety, final float segment, final String segmentUnit,
                                                     final String conservationConditions, final String description,
                                                     final Date date
    ) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_movement(?,?,?,?,?,?,?,?,?,?,?,?::date)}"
            )) {
                function.setLong(1, houseId);
                function.setShort(2, storageId);
                function.setBoolean(3, movementType);
                function.setShort(4, quantity);
                function.setString(5, productName);
                function.setString(6, brand);
                function.setString(7, variety);
                function.setFloat(8, segment);
                function.setString(9, segmentUnit);
                function.setString(10, description);
                function.setString(11, conservationConditions);
                function.setDate(12, date);
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    try {
                        return extractStockItemMovementFromResultSet(resultSet);
                    } catch (EntityException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Extract StockItemMovement from ResultSet
     *
     * @param resultSet instance of ResultSet
     * @return instance of StockItemMovement
     * @throws SQLException    throw by ResultSet
     * @throws EntityException throw if cannot create instance of StockItem
     */
    private StockItemMovement extractStockItemMovementFromResultSet(ResultSet resultSet) throws SQLException, EntityException {
        long house_id = resultSet.getLong(1);
        String stockitem_sku = resultSet.getString(2);
        short storage_id = resultSet.getShort(3);
        boolean stockitemmovement_type = resultSet.getBoolean(4);
        String stockitemmovement_datetime = DateUtils.convertTimestampFormat(resultSet.getTimestamp(5));
        short stockitemmovement_quantity = resultSet.getShort(6);
        return new StockItemMovement(house_id, stockitem_sku, storage_id, stockitemmovement_type, stockitemmovement_datetime,
                stockitemmovement_quantity);
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
