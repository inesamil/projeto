package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import org.postgresql.util.PGobject;
import pt.isel.ps.gis.dal.repositories.StorageRepositoryCustom;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Numrange;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.model.StorageId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StorageRepositoryCustomImpl implements StorageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Storage insertStorage(final Storage storage) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_storage(?,?,?::numrange)}"
            )) {
                function.setLong(1, storage.getId().getHouseId());
                function.setString(2, storage.getStorageName());
                function.setObject(3, storage.getStorageTemperature(), Types.OTHER);
                try (ResultSet resultSet = function.executeQuery()) {
                    if (!resultSet.next()) throw new SQLException("Result set is empty.");
                    long house_id = resultSet.getLong(1);
                    short storage_id = resultSet.getShort(2);
                    String storage_name = resultSet.getString(3);
                    PGobject temperaturePGobj = (PGobject) resultSet.getObject(4);
                    if (!temperaturePGobj.getType().equals("numrange"))
                        throw new SQLException(String.format("Not allow conversation of this type: %s", temperaturePGobj.getType()));
                    try {
                        Numrange storage_temperature = Numrange.parseNumrange(temperaturePGobj.getValue());
                        return new Storage(house_id, storage_id, storage_name, storage_temperature);
                    } catch (EntityException e) {
                        throw new SQLException(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void deleteCascadeStorageById(StorageId id) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call delete_storage(?,?)}"
            )) {
                function.setLong(1, id.getHouseId());
                function.setShort(2, id.getStorageId());
                function.execute();
            }
        });
    }
}
