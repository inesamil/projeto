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
    public List<StockItem> findStockItemsFiltered(Long houseId, String productName, String brand, String variety, Float segment, Short storageId) {
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
                    ps.setFloat(9, segment);
                if (isNotNull(ps, 10, houseId))
                    ps.setLong(10, houseId);
                if (isNotNull(ps, 11, storageId))
                    ps.setShort(11, storageId);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<StockItem> stockItems = new ArrayList<>();
                    while (resultSet.next()) {
                        long house_id = resultSet.getLong(1);
                        String sku = resultSet.getString(2);
                        int category_id = resultSet.getInt(3);
                        int product_id = resultSet.getInt(4);
                        String stockitem_brand = resultSet.getString(5);
                        float stockitem_segment = resultSet.getFloat(6);
                        String stockitem_variety = resultSet.getString(7);
                        short stockitem_quantity = resultSet.getShort(8);
                        String stockitem_segmentunit = resultSet.getString(9);
                        String stockitem_description = resultSet.getString(10);
                        String stockitem_conservationstorage = resultSet.getString(11);
                        try {
                            StockItem stockItem = new StockItem(house_id, sku, category_id, product_id, stockitem_brand, stockitem_segment,
                                    stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description,
                                    stockitem_conservationstorage);
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

    private <T> boolean isNotNull(PreparedStatement ps, int idx, T t) throws SQLException {
        if (t == null) {
            ps.setNull(idx, Types.OTHER);
            return false;
        }
        return true;
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
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
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
                    try {
                        return new StockItem(house_id, stockitem_sku, category_id, product_id, stockitem_brand, stockitem_segment,
                                stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage);
                    } catch (EntityException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
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
}
