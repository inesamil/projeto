package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockItemMovementRepositoryCustomImpl implements StockItemMovementRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StockItemMovement> getMovementsFiltered(Long houseId, String sku, Boolean type, Timestamp date, Short storageId) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "SELECT public.\"stockitemmovement\".house_id, public.\"stockitemmovement\".stockitem_sku, " +
                    "public.\"stockitemmovement\".storage_id, public.\"stockitemmovement\".stockitemmovement_type, " +
                    "public.\"stockitemmovement\".stockitemmovement_datetime, public.\"stockitemmovement\".stockitemmovement_quantity " +
                    "FROM public.\"stockitemmovement\" WHERE public.\"stockitemmovement\".house_id = ? AND " +
                    "(public.\"stockitemmovement\".stockitem_sku = ? OR (? IS NOT NULL AND " +
                    "public.\"stockitemmovement\".stockitemmovement_type = ?) OR " +
                    "DATE(public.\"stockitemmovement\".stockitemmovement_datetime) = ? " +
                    "OR public.\"stockitemmovement\".storage_id = ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isNotNull(ps, 1, houseId))
                    ps.setLong(1, houseId);
                if (isNotNull(ps, 2, sku))
                    ps.setString(2, sku);
                if (isNotNull(ps, 3, type))
                    ps.setBoolean(3, type);
                if (isNotNull(ps, 4, type))
                    ps.setBoolean(4, type);
                if (isNotNull(ps, 5, date))
                    ps.setTimestamp(5, date);
                if (isNotNull(ps, 6, storageId))
                    ps.setShort(6, storageId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<StockItemMovement> stockItemMovements = new ArrayList<>();
                    while (resultSet.next()) {
                        long house_id = resultSet.getLong(1);
                        String stockitem_sku = resultSet.getString(2);
                        short storage_id = resultSet.getShort(3);
                        boolean stockitemmovement_type = resultSet.getBoolean(4);
                        Timestamp stockitemmovement_datetime = resultSet.getTimestamp(5);
                        short stockitemmovement_quantity = resultSet.getShort(6);
                        try {
                            StockItemMovement stockItemMovement = new StockItemMovement(house_id, stockitem_sku, storage_id, stockitemmovement_type,
                                    stockitemmovement_datetime, stockitemmovement_quantity);
                            stockItemMovements.add(stockItemMovement);
                        } catch (EntityException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return stockItemMovements;
                }
            }
        });
    }

    private <T> boolean isNotNull(PreparedStatement ps, int idx, T t) throws SQLException {
        if (t == null) {
            ps.setNull(idx, Types.OTHER);
            return false;
        }
        return true;
    }
}
