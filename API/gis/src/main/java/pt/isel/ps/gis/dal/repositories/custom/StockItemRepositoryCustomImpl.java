package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.StockItemRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockItemRepositoryCustomImpl implements StockItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StockItem> findStockItemsFiltered(Long houseId, String productName, String brand, String variety, String segment, Short storageId) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" " +
                    "WHERE public.\"stockitem\".house_id = ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" JOIN public.\"product\" ON (public.\"stockitem\".category_id = public.\"product\".category_id AND public.\"stockitem\".product_id = public.\"product\".product_id) " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"product\".product_name != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitem\".stockitem_brand != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitem\".stockitem_variety != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitem\".stockitem_segment != ? " +
                    "EXCEPT " +
                    "SELECT public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage " +
                    "FROM public.\"stockitem\" JOIN public.\"stockitemstorage\" ON (public.\"stockitem\".house_id = public.\"stockitemstorage\".house_id AND public.\"stockitem\".stockitem_sku = public.\"stockitemstorage\".stockitem_sku) " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitemstorage\".storage_id != ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isNotNull(ps, 1, houseId))
                    ps.setLong(1, houseId);
                if (isNotNull(ps, 2, houseId))
                    ps.setLong(2, houseId);
                if (isNotNull(ps, 3, productName))
                    ps.setString(3, productName);
                if (isNotNull(ps, 4, houseId))
                    ps.setLong(4, houseId);
                if (isNotNull(ps, 5, brand))
                    ps.setString(5, brand);
                if (isNotNull(ps, 6, houseId))
                    ps.setLong(6, houseId);
                if (isNotNull(ps, 7, variety))
                    ps.setString(7, variety);
                if (isNotNull(ps, 8, houseId))
                    ps.setLong(8, houseId);
                if (isNotNull(ps, 9, segment))
                    ps.setString(9, segment);
                if (isNotNull(ps, 10, houseId))
                    ps.setLong(10, houseId);
                if (isNotNull(ps, 11, storageId))
                    ps.setShort(11, storageId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<StockItem> stockItems = new ArrayList<>();
                    while (resultSet.next()) {
                        try {
                            StockItem stockItem = extractStockItemFromResultSet(resultSet);
                            stockItems.add(stockItem);
                        } catch (EntityException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return stockItems;
                }
            }
        });
    }

    @Override
    public StockItem insertStockItem(final StockItem stockItem) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_stock_item(?,?,?,?,?,?,?,?,?,?)}"
            )) {
                function.setLong(1, stockItem.getId().getHouseId());
                function.setInt(2, stockItem.getCategoryId());
                function.setInt(3, stockItem.getProductId());
                function.setString(4, stockItem.getStockitemBrand());
                function.setString(5, stockItem.getStockitemVariety());
                function.setFloat(6, stockItem.getStockitemSegment());
                function.setString(7, stockItem.getStockitemSegmentunit());
                function.setShort(8, stockItem.getStockitemQuantity());
                function.setString(9, stockItem.getStockitemDescription());
                function.setString(10, stockItem.getStockitemConservationstorage());
                return executeUpdate(function);
            }
        });
    }

    @Override
    public StockItem decrementStockitemQuantity(StockItemId stockItemId, Short quantityToDecrement) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "UPDATE public.\"stockitem\" SET stockitem_quantity = public.\"stockitem\".stockitem_quantity - ? " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitem\".stockitem_sku = ? RETURNING " +
                    "public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, " +
                    "public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, " +
                    "public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                setParametersForIncrementAndDecrement(ps, quantityToDecrement, stockItemId);
                return executeUpdate(ps);
            }
        });
    }

    @Override
    public StockItem incrementStockitemQuantity(StockItemId stockItemId, Short quantityToIncrement) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            String sql = "UPDATE public.\"stockitem\" SET stockitem_quantity = public.\"stockitem\".stockitem_quantity + ? " +
                    "WHERE public.\"stockitem\".house_id = ? AND public.\"stockitem\".stockitem_sku = ? RETURNING " +
                    "public.\"stockitem\".house_id, public.\"stockitem\".stockitem_sku, " +
                    "public.\"stockitem\".category_id, public.\"stockitem\".product_id, public.\"stockitem\".stockitem_brand, " +
                    "public.\"stockitem\".stockitem_segment, public.\"stockitem\".stockitem_variety, " +
                    "public.\"stockitem\".stockitem_quantity, public.\"stockitem\".stockitem_segmentunit, " +
                    "public.\"stockitem\".stockitem_description, public.\"stockitem\".stockitem_conservationstorage;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                setParametersForIncrementAndDecrement(ps, quantityToIncrement, stockItemId);
                return executeUpdate(ps);
            }
        });
    }

    @Override
    public void deleteStockItemById(StockItemId id) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call delete_stock_item(?,?)}"
            )) {
                function.setLong(1, id.getHouseId());
                function.setString(2, id.getStockitemSku());
                function.execute();
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

    /**
     * set parameters in ps for increment and decrement.
     *
     * @param ps          instance of PreparedStatement
     * @param toUpdate    quantity to increment or decrement
     * @param stockItemId instance of StockItemId to set parameters in ps
     * @throws SQLException throw by ps
     */
    private void setParametersForIncrementAndDecrement(PreparedStatement ps, Short toUpdate, StockItemId stockItemId) throws SQLException {
        if (isNotNull(ps, 1, toUpdate))
            ps.setShort(1, toUpdate);
        if (isNotNull(ps, 2, stockItemId.getHouseId()))
            ps.setLong(2, stockItemId.getHouseId());
        if (isNotNull(ps, 3, stockItemId.getStockitemSku()))
            ps.setString(3, stockItemId.getStockitemSku());
    }

    /**
     * Execute the update and return StockItem instance.
     *
     * @param ps instance of PreparedStatement
     * @return StockItem instance
     * @throws SQLException throw by PreparedStatement or if ResultSet is empty or trying to extract StockItem from
     *                      ResultSet.
     */
    private StockItem executeUpdate(PreparedStatement ps) throws SQLException {
        try (ResultSet resultSet = ps.executeQuery()) {
            if (!resultSet.next()) throw new SQLException("Result set is empty.");
            try {
                return extractStockItemFromResultSet(resultSet);
            } catch (EntityException e) {
                throw new SQLException(e.getMessage());
            }
        }
    }

    /**
     * Extract StockItem from ResultSet
     *
     * @param resultSet instance of ResultSet
     * @return instance of StockItem
     * @throws SQLException    throw by ResultSet
     * @throws EntityException throw if cannot create instance of StockItem
     */
    private StockItem extractStockItemFromResultSet(ResultSet resultSet) throws SQLException, EntityException {
        long house_id = resultSet.getLong(1);
        String stockitem_sku = resultSet.getString(2);
        int category_id = resultSet.getInt(3);
        int product_id = resultSet.getInt(4);
        String stockitem_brand = resultSet.getString(5);
        float stockitem_segment = resultSet.getFloat(6);
        String stockitem_variety = resultSet.getString(7);
        short stockitem_quantity = resultSet.getShort(8);
        String stockitem_segmentunit = resultSet.getString(9);
        String stockitem_description = resultSet.getString(10);
        String stockitem_conservationstorage = resultSet.getString(11);
        return new StockItem(house_id, stockitem_sku, category_id, product_id, stockitem_brand, stockitem_segment,
                stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage);
    }
}
