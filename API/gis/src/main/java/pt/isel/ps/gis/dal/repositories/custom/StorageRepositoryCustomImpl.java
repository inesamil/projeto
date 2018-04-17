package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.dal.repositories.StorageRepositoryCustom;
import pt.isel.ps.gis.model.Storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.Types;

public class StorageRepositoryCustomImpl implements StorageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertStorage(Storage storage) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_storage(?,?,?::numrange)}"
            )) {
                function.setLong(1, storage.getId().getHouseId());
                function.setString(2, storage.getStorageName());
                function.setObject(3, storage.getStorageTemperature(), Types.OTHER);
                function.execute();
            }
        });
    }
}
