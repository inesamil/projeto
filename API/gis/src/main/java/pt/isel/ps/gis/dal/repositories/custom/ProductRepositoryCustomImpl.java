package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.ProductRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product insertProduct(Product product) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_product(?,?,?,?,?)}"
            )) {
                function.setInt(1, product.getId().getCategoryId());
                function.setString(2, product.getProductName());
                function.setBoolean(3, product.getProductEdible());
                function.setShort(4, product.getProductShelflife());
                function.setString(5, product.getProductShelflifetimeunit());
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    int category_id = resultSet.getInt(1);
                    int product_id = resultSet.getInt(2);
                    String product_name = resultSet.getString(3);
                    boolean product_edible = resultSet.getBoolean(4);
                    short product_shelflife = resultSet.getShort(5);
                    String product_shelflifetimeunit = resultSet.getString(6);
                    try {
                        return new Product(category_id, product_id, product_name, product_edible, product_shelflife, product_shelflifetimeunit);
                    } catch (EntityException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
            }
        });
    }
}
