package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.StockItemRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockItemRepositoryCustomImpl implements StockItemRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

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
                ResultSet resultSet = function.executeQuery();
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
                resultSet.close();
                try {
                    return new StockItem(house_id, stockitem_sku, category_id, product_id, stockitem_brand, stockitem_segment,
                            stockitem_variety, stockitem_quantity, stockitem_segmentunit, stockitem_description, stockitem_conservationstorage);
                } catch (EntityException e) {
                    throw new SQLException(e.getMessage());
                }
            }
        });
    }
}
